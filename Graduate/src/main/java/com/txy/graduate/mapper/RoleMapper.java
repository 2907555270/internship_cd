package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    //根据用户user_id查询用户对应的权限 user_role-->role：多表查询
    Role findRoleByUser_id(String user_id);

    //向user_role表插入用户和权限 user_role 对应的关联项
    int saveUserAndRole(Map<String,Object> map);

    //根据user_id删除用户和权限 user_role 对应的关联项
    int removeUserAndRoleByUId(String user_id);

    //根据role_id删除用户和权限 user_role 对应的关联项
    int removeUserAndRoleByRId(Integer role_id);
}
