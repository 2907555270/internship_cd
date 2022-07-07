package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.Role;
import com.txy.graduate.domain.User;

public interface UserService extends IService<User> {

    /**
     * 按照分页条件查询所有数据
     * @param currentPage
     * @param pageSize
     * @return
     */
    IPage<User> findAllByPage(int currentPage,int pageSize);

    /**
     * 按user_id和user_pwd匹配进行登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 根据user_Id查看用户权限
     */
    Role findByUserId(User user);
}
