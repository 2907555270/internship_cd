package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    //根据用户user_id查询用户对应的权限：多表查询
    Role findRoleByUser_id(String user_id);
}
