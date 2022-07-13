package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.po.SysUser;

import java.util.Map;

public interface ISysUserService extends IService<SysUser> {


    /**
     * user表
     */

    /**
     * 组合查询所有数据(多条件模糊查询+分页查询)
     *  1. 无查询条件时就是查询所有，按照分页
     *  2. 有查询条件时就是多条件+分页组合查询
     */
    IPage<SysUser> querySysUser(Map<String,Object> map);

    //根据username查询用户信息
    SysUser getUserByUserName(String username);


    /**
     * user user_role表
     */
    //根据userId查询权限信息
    String getUserAuthorityInfo(Long userId);

    //按user_id删除用户信息：同时解绑用户的角色信息
    boolean deleteUserAndUserRoleByUid(Long user_id);

    //新增user：同时绑定用户的角色信息
    boolean saveUserAndUserRole(Map<String,Object> map);
}
