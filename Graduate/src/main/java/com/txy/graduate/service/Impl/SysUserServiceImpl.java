package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.sys.SysRole;
import com.txy.graduate.domain.sys.SysUser;
import com.txy.graduate.domain.sys.SysUserRole;
import com.txy.graduate.mapper.SysUserMapper;
import com.txy.graduate.service.ISysRoleService;
import com.txy.graduate.service.ISysUserService;
import com.txy.graduate.util.QueryWrapperUtil;
import com.txy.graduate.util.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysRoleService roleService;


    /**
     * user表
     */

    @SneakyThrows
    @Override
    public IPage<SysUser> querySysUser(Map<String, Object> map) {
        //获取user查询条件
        SysUser sysUser = QueryWrapperUtil.map2obj(map, SysUser.class);
        QueryWrapper<SysUser> wrapper = QueryWrapperUtil.queryWrapper_LikeMany(sysUser);

        //查询条件无参数时就是普通的分页查询，否则就是多条件模糊查询+分页查询的组合
        return userMapper.selectPage(QueryWrapperUtil.getPageFromMap(map), wrapper);
    }

    @Override
    public SysUser getUserByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    /**
     * user user_role表
     */

    @Override
    public String getUserAuthorityInfo(Long userId) {
        String authority = "";
        if (redisUtil.hasKey("GrantedAuthority:" + userId)) {
            authority = (String) redisUtil.get("GrantedAuthority:" + userId);
        } else {
            //1.获取角色信息
            // select * form sys_role where id in (select role_id from sys_user_role where user_id = 1)
            List<SysRole> roles = roleService.list(new QueryWrapper<SysRole>().inSql("id", "select role_id from sys_user_role where user_id = " + userId));
            if (roles.size() > 0) {
                String rolesCode = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = rolesCode.concat(",");
            }
            //2.获取权限信息
            List<String> menus = userMapper.getMenuByUserId(userId);
            if (menus.size() > 0) {
                String menuCode = String.join(",", menus);
                authority = authority.concat(menuCode);
            }

            redisUtil.set("GrantedAuthority:" + userId, authority, 60 * 60);
        }
        return authority;
    }

    @Transactional
    @Override
    public boolean deleteUserAndUserRoleByUid(Long user_id) {
        //解绑用户的角色信息
        boolean flag1 = roleService.removeUserAndRoleByUId(user_id);
        //删除用户信息
        boolean flag2 = userMapper.deleteById(user_id) > 0;
        return flag1 && flag2;
    }

    @SneakyThrows
    @Transactional
    @Override
    public boolean saveUserAndUserRole(Map<String, Object> map) {
        //map中获取user信息
        SysUserRole sysUserRole = QueryWrapperUtil.map2obj((Map<String, Object>) map.get("role"), SysUserRole.class);

        //map中获取user_role信息
        SysUser sysUser = QueryWrapperUtil.map2obj((Map<String, Object>) map.get("user"), SysUser.class);

        //添加user信息
        boolean flag1 = userMapper.insert(sysUser) > 0;

        System.out.println(sysUser);

        sysUserRole.setUserId(sysUser.getId());

        //为user绑定role信息
        boolean flag2 = roleService.saveUserAndRole(sysUserRole);

        return flag1 && flag2;
    }
}
