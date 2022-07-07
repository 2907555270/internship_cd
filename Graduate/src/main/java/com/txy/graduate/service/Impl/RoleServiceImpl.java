package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Role;
import com.txy.graduate.mapper.RoleMapper;
import com.txy.graduate.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public IPage<Role> findAllByPage(int currentPage, int pageSize) {
        Page<Role> rolePage = new Page<>(currentPage, pageSize);
        return roleMapper.selectPage(rolePage, null);
    }

    @Override
    public Role getRole(String user_id) {
        return roleMapper.findRoleByUser_id(user_id);
    }
}
