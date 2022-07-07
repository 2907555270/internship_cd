package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.Role;
import com.txy.graduate.domain.User;


public interface RoleService extends IService<Role> {

    //分页查询所有的Role
    IPage<Role> findAllByPage(int currentPage,int pageSize);

    //按user_id查询用户的权限
    Role getRole(String user_id);

}
