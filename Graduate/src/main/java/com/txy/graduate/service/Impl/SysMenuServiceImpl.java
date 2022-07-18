package com.txy.graduate.service.Impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.po.SysMenu;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.mapper.SysMenuMapper;
import com.txy.graduate.mapper.SysUserMapper;
import com.txy.graduate.config.ConstConfig;
import com.txy.graduate.domain.dto.MenuDTO;
import com.txy.graduate.service.ISysMenuService;
import com.txy.graduate.service.ISysRoleService;

import com.txy.graduate.util.QueryUtil;
import com.txy.graduate.util.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper menuMapper;

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ISysRoleService roleService;


    //查询的基本字段
    private final String[] baseColumns = {"id", "parent_id", "name", "path", "perms", "component", "type", "status"};

    /**
     * menu表
     */
    //查询所有的数据，用于导出数据
    //可分页查询，也可以查询所有，但是不会返回分页信息
    @DS("slave")
    @Override
    public List<SysMenu> queryAll(Integer... args) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.select(baseColumns);
        //查询全部
        if (args == null || args.length < 2)
            return menuMapper.selectList(wrapper);
        //分页查询
        return menuMapper.selectPage(new Page<>(args[0], args[1]), wrapper).getRecords();
    }

    @DS("slave")
    @SneakyThrows
    @Override
    public IPage<SysMenu> querySysMenu(Map<String, Object> map) {
        //封装查询条件的对象
        SysMenu sysMenu = QueryUtil.map2obj(map, SysMenu.class);

        QueryWrapper<SysMenu> wrapper = QueryUtil.queryWrapper_LikeMany(sysMenu);

        return menuMapper.selectPage(QueryUtil.getPageFromMap(map), wrapper);
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
    @DS("slave")
    @Override
    public List<MenuDTO> queryCurrentUserNav(String username) {
        //从redis中获取用户信息
        SysUser user = (SysUser) redisUtil.get(ConstConfig.USER_KEY + ":" + username);

        //查询 user_id --> menu_id --> menu_list
        List<Long> menuIds = userMapper.getMenuIdByUserId(user.getId());

        List<SysMenu> menuList = menuMapper.selectList(new QueryWrapper<SysMenu>().in("id", menuIds));

        // 处理菜单层级关系(转树状结构)
        List<SysMenu> menuTree = buildTreeMenu(menuList);

        // 转dto
        return convert(menuTree);
    }


    /**
     * 构建树
     * @param menuList ：菜单列表
     * @return List<SysMenu> ： 构建后的菜单树
     */
    private List<SysMenu> buildTreeMenu(List<SysMenu> menuList) {
        List<SysMenu> finalMenus = new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            for (SysMenu e : menuList) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                if (sysMenu.getId().equals(e.getParentId())) {
                    sysMenu.getChildren().add(e);
                }
            }
            if (sysMenu.getParentId() == 0L) {
                finalMenus.add(sysMenu);
            }
        }
        return finalMenus;
    }

    /**
     * 展开树并转dto
     * @param menuTree ： 菜单树-只含有直接孩子
     * @return : 含有所有子菜单
     */
    private List<MenuDTO> convert(List<SysMenu> menuTree) {
        List<MenuDTO> menuDTOS = new ArrayList<>();
        menuTree.forEach(t -> {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(t.getId());
            menuDTO.setName(t.getPerms());
            menuDTO.setTitle(t.getName());
            menuDTO.setComponent(t.getComponent());
            menuDTO.setPath(t.getPath());
            if (t.getChildren().size() > 0) {
                menuDTO.setChildren(convert(t.getChildren()));
            }
            menuDTOS.add(menuDTO);
        });
        return menuDTOS;
    }
}
