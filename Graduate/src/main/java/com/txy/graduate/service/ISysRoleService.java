package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.po.SysRole;
import com.txy.graduate.domain.po.SysRoleMenu;
import com.txy.graduate.domain.po.SysUserRole;

import java.util.List;
import java.util.Map;


public interface ISysRoleService extends IService<SysRole> {

    /**
     * Role表
     */
    //分页+多条件模糊查询
    IPage<SysRole> querySysRole(Map<String,Object> map);


    /**
     * User_Role表
     */
    //按user_id查询用户对应的权限信息
    List<SysRole> queryRoleByUid(Long user_id);

    //为user绑定role
    boolean saveUserAndRole(SysUserRole sysUserRole);

    //按role_id 解绑role对应的所有user_role信息
    boolean removeUserAndRoleByRId(Long role_id);

    //按user_id 解绑user对应的所有user_role信息
    boolean removeUserAndRoleByUId(Long user_id);

    //改绑 某个 用户———角色 绑定信息
    boolean updateUByUserRoleId(SysUserRole sysUserRole);

    /**
     * Menu_Role表
     */
    //按照menu_id查询权限信息
    List<SysRole> queryRoleByMid(Long menu_id);

    //为menu绑定role
    boolean saveRoleAndMenu(SysRoleMenu sysRoleMenu);

    //按role_id 解绑 role和menu
    boolean removeRoleAndMenuByRid(Long role_id);

    //按menu_id 解绑 role和menu
    boolean removeRoleAndMenuByMid(Long menu_id);

    //改绑 某个 菜单————角色 绑定信息
    boolean updateMByUserRoleId(SysRoleMenu sysRoleMenu);


    /**
     * User_Role Menu_Role
     */
    //删除role,同时解绑该role对应的user和menu信息
    boolean removeRoleByRid(Long role_id);


}
