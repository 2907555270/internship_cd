package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.Role;
import com.txy.graduate.domain.User;


public interface RoleService extends IService<Role> {

    //分页查询所有的Role
    IPage<Role> findAllByPage(int currentPage,int pageSize);

    //按user_id查询用户对应的权限信息
    Role getRole(String user_id);

    //按role_name查询权限信息
    Role findByName(String role_name);

    //为user设置role权限信息
    boolean saveUserAndRole(String user_id,Integer role_id);

    //根据user_id删除 user_role 对应的权限信息
    boolean removeUserAndRoleByUId(String user_id);

    //根据role_id删除 user_role 对应的权限信息
    boolean removeUserAndRoleByRId(Integer role_id);
}
