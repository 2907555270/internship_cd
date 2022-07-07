package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Role;
import com.txy.graduate.domain.User;
import com.txy.graduate.mapper.RoleMapper;
import com.txy.graduate.mapper.UserMapper;
import com.txy.graduate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public IPage<User> findAllByPage(int currentPage,int pageSize) {
        IPage<User> userPage = new Page<>(currentPage, pageSize);
        return userMapper.selectPage(userPage,null);
    }

    @Override
    public User login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId());
        wrapper.eq("user_pwd",user.getUserPwd());
        return userMapper.selectOne(wrapper);
    }

    @Override
    public Role findByUserId(User user) {
        return null;
    }
}
