package com.txy.graduate.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.sys.SysMenu;
import com.txy.graduate.domain.sys.SysUser;
import com.txy.graduate.mapper.SysMenuMapper;
import com.txy.graduate.mapper.SysUserMapper;
import com.txy.graduate.security.config.ConstConfig;
import com.txy.graduate.domain.dto.MenuDTO;
import com.txy.graduate.service.ISysMenuService;
import com.txy.graduate.service.ISysRoleService;

import com.txy.graduate.util.QueryWrapperUtil;
import com.txy.graduate.util.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysRoleService roleService;

    /**
     * menu表
     */
    @SneakyThrows
    @Override
    public IPage<SysMenu> findSysMenu(Map<String, Object> map) {
        //封装查询条件的对象
        SysMenu sysMenu = QueryWrapperUtil.map2obj(map, SysMenu.class);

        QueryWrapper<SysMenu> wrapper = QueryWrapperUtil.queryWrapper_LikeMany(sysMenu);

        return menuMapper.selectPage(QueryWrapperUtil.getPageFromMap(map),wrapper);
    }

    /**
     * menu role_menu表
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteSysMenuById(Long menu_id) {
        //解绑menu_role
        roleService.removeRoleAndMenuByMid(menu_id);
        //删除menu
        menuMapper.deleteById(menu_id);
        return false;
    }


    /**
     * user_role role_menu表
     */
    @Override
    public List<MenuDTO> getCurrentUserNav(String username) {
        //从redis中获取用户信息
        SysUser user = (SysUser) redisUtil.get(ConstConfig.USER_KEY+":"+username);

        //查询 user_id --> menu_id --> menu_list
        List<Long> menuIds = userMapper.getMenuIdByUserId(user.getId());

        List<SysMenu> menuList = menuMapper.selectList(new QueryWrapper<SysMenu>().in("id", menuIds));

        // 处理菜单层级关系(转树状结构)
        List<SysMenu> menuTree = buildTreeMenu(menuList);

        // 转dto
        return convert(menuTree);
    }


    /**构建树
     * @param menuList
     * @return
     */
    private List<SysMenu> buildTreeMenu(List<SysMenu> menuList) {
        List<SysMenu> finalMenus = new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            for (SysMenu e : menuList) {
                if(sysMenu.getChildren()==null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                if (sysMenu.getId().equals(e.getParentId())){
                    sysMenu.getChildren().add(e);
                }
            }
            if (sysMenu.getParentId() == 0L){
                finalMenus.add(sysMenu);
            }
        }
        return finalMenus;
    }

    /**展开树并转dto
     * @param menuTree
     * @return
     */
    private List<MenuDTO> convert(List<SysMenu> menuTree) {
        List<MenuDTO> menuDTOS = new ArrayList<>();
        menuTree.forEach(t->{
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(t.getId());
            menuDTO.setName(t.getPerms());
            menuDTO.setTitle(t.getName());
            menuDTO.setComponent(t.getComponent());
            menuDTO.setPath(t.getPath());
            if (t.getChildren().size() >0) {
                menuDTO.setChildren(convert(t.getChildren()));
            }
            menuDTOS.add(menuDTO);
        });
        return menuDTOS;
    }
}
