package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Role;
import com.txy.graduate.domain.User;
import com.txy.graduate.mapper.RoleMapper;
import com.txy.graduate.mapper.UserMapper;
import com.txy.graduate.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Role findByName(String role_name) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        return roleMapper.selectOne(wrapper.eq("role_name",role_name));
    }

    @Override
    public boolean saveUserAndRole(String user_id, Integer role_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id",user_id);
        map.put("role_id",role_id);
        return roleMapper.saveUserAndRole(map)>0;
    }

    @Override
    public boolean removeUserAndRoleByUId(String user_id) {
        return roleMapper.removeUserAndRoleByUId(user_id)>0;
    }

    @Override
    public boolean removeUserAndRoleByRId(Integer role_id) {
        return roleMapper.removeUserAndRoleByRId(role_id)>0;
    }
}
