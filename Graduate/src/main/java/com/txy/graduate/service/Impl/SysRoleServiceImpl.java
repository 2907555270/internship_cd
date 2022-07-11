package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.sys.SysRole;
import com.txy.graduate.domain.sys.SysRoleMenu;
import com.txy.graduate.domain.sys.SysUserRole;
import com.txy.graduate.mapper.SysRoleMapper;
import com.txy.graduate.service.ISysRoleService;
import com.txy.graduate.util.QueryWrapperUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * Role表
     */
    @SneakyThrows
    @Override
    public IPage<SysRole> querySysRole(Map<String, Object> map) {
        //获取查询条件
        SysRole sysRole = QueryWrapperUtil.map2obj(map, SysRole.class);
        QueryWrapper<SysRole> wrapper = QueryWrapperUtil.queryWrapper_LikeMany(sysRole);

        return roleMapper.selectPage(QueryWrapperUtil.getPageFromMap(map), wrapper);
    }


    /**
     * user_role表
     */
    @Override
    public List<SysRole> queryRoleByUid(Integer user_id) {
        return roleMapper.selectRoleByUid(user_id);
    }

    @Override
    public boolean saveUserAndRole(SysUserRole sysUserRole) {
        return roleMapper.insertUserAndRole(sysUserRole) > 0;
    }

    @Override
    public boolean removeUserAndRoleByRId(Integer role_id) {
        return roleMapper.deleteUserAndRoleByRId(role_id) > 0;
    }

    @Override
    public boolean removeUserAndRoleByUId(Integer user_id) {
        return roleMapper.deleteUserAndRoleByUId(user_id) > 0;
    }

    @Override
    public boolean updateUByUserRoleId(SysUserRole sysUserRole) {
        return roleMapper.updateUByUserAndRoleId(sysUserRole) > 0;
    }


    /**
     * role_menu表
     */
    @Override
    public List<SysRole> queryRoleByMid(Integer menu_id) {
        return roleMapper.selectRoleByMenuId(menu_id);
    }

    @Override
    public boolean saveRoleAndMenu(SysRoleMenu sysRoleMenu) {
        return roleMapper.insertRoleAndMenu(sysRoleMenu) > 0;
    }

    @Override
    public boolean removeRoleAndMenuByMid(Integer menu_id) {
        return roleMapper.deleteRoleAndMenuByMId(menu_id) > 0;
    }

    @Override
    public boolean removeRoleAndMenuByRid(Integer role_id) {
        return roleMapper.deleteRoleAndMenuByRId(role_id) > 0;
    }

    @Override
    public boolean updateMByUserRoleId(SysRoleMenu sysRoleMenu) {
        return roleMapper.updateMByUserAndRoleId(sysRoleMenu) > 0;
    }

    /**
     * role user_role menu_role表
     */
    @Transactional
    @Override
    public boolean removeRoleByRid(Integer role_id) {
        //解绑user_role
        removeUserAndRoleByRId(role_id);
        //解绑role_menu
        removeRoleAndMenuByRid(role_id);

        //删除role --- 开启了事务，若前面执行出错，则会自动回滚：及第三步成功-->整个成功
        return removeById(role_id);
    }


}