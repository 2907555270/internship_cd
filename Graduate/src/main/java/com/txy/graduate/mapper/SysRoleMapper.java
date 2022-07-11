package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.SysRole;
import com.txy.graduate.domain.SysRoleMenu;
import com.txy.graduate.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * user_role表
     */
    //SELECT : user_id --> role_id --> role
    List<SysRole> selectRoleByUid(Integer user_id);

    //SAVE : user_role
    int insertUserAndRole(SysUserRole sysUserRole);

    //DELETE : role_id --> delete mysql{user_role} by role_id --> delete role
    int deleteUserAndRoleByRId(Integer role_id);

    //DELETE : user_id --> delete mysql{user_role} by user_id
    int deleteUserAndRoleByUId(Integer user_id);

    //UPDATE : user_role_id --> update mysql{user_role}
    int updateUByUserAndRoleId(SysUserRole sysUserRole);


    /**
     * role_menu表
     */
    //SELECT : menu_id --> role_id --> role
    List<SysRole> selectRoleByMenuId(Integer menu_id);

    //SAVE : role_menu
    int insertRoleAndMenu(SysRoleMenu sysRoleMenu);

    //DELETE : role_id --> delete mysql{role_menu} by role_id --> delete role
    int deleteRoleAndMenuByRId(Integer role_id);

    //DELETE : user_id --> delete mysql{user_role} by user_id
    int deleteRoleAndMenuByMId(Integer menu_id);

    //UPDATE : menu_role_id --> update mysql{menu_role}
    int updateMByUserAndRoleId(SysRoleMenu sysRoleMenu);
}
