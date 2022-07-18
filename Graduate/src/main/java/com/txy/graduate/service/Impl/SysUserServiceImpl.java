package com.txy.graduate.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.po.SysRole;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.domain.po.SysUserRole;
import com.txy.graduate.mapper.SysUserMapper;
import com.txy.graduate.config.ConstConfig;
import com.txy.graduate.service.ISysRoleService;
import com.txy.graduate.service.ISysUserService;
import com.txy.graduate.util.QueryUtil;
import com.txy.graduate.util.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ISysRoleService roleService;

    @DS("slave")
    @Override
    public List<SysUser> queryAll(Integer... args) {
        //暂时不用筛选字段
        //QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        //wrapper.select()
        if(args==null||args.length<2){
            return userMapper.selectList(null);
        }
        //分页查询
        return userMapper.selectPage(new Page<>(args[0],args[1]),null).getRecords();
    }

    /**
     * user表
     */

    @DS("slave")
    @SneakyThrows
    @Override
    public IPage<SysUser> querySysUser(Map<String, Object> map) {
        //获取user查询条件
        SysUser sysUser = QueryUtil.map2obj(map, SysUser.class);
        QueryWrapper<SysUser> wrapper = QueryUtil.queryWrapper_LikeMany(sysUser);

        //查询条件无参数时就是普通的分页查询，否则就是多条件模糊查询+分页查询的组合
        return userMapper.selectPage(QueryUtil.getPageFromMap(map), wrapper);
    }

    @DS("slave")
    @Override
    public SysUser queryUserByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    /**
     * user user_role表
     */
    @DS("slave")
    @Override
    public String queryUserAuthorityInfo(Long userId) {
        String authority = "";
        //如果redis中有用户的身份信息，则直接从redis中获取
        if (redisUtil.hasKey("GrantedAuthority:" + userId)) {
            authority = (String) redisUtil.get("GrantedAuthority:" + userId);
        }

        //redis中没有用户的身份信息，从数据库获取，拼接角色信息和权限信息，并写入redis
        else {
            //1.获取角色信息
            List<SysRole> roles = roleService.queryRoleByUid(userId);
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

            //向redis中存放用户的身份信息，有效期为一个小时
            redisUtil.set(ConstConfig.GRANTED_KEY +":" + userId, authority, 60 * 60);
        }
        return authority;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteUserAndUserRoleByUid(Long user_id) {
        //解绑用户的角色信息
        boolean flag1 = roleService.removeUserAndRoleByUId(user_id);
        //删除用户信息
        boolean flag2 = userMapper.deleteById(user_id) > 0;
        return flag1&&flag2;
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean saveUserAndUserRole(Map<String, Object> map) {
        //map中获取user信息
        SysUserRole sysUserRole = QueryUtil.map2obj((Map<String, Object>) map.get("role"), SysUserRole.class);

        //map中获取user_role信息
        SysUser sysUser = QueryUtil.map2obj((Map<String, Object>) map.get("user"), SysUser.class);

        //添加user信息
        boolean flag1 = userMapper.insert(sysUser) > 0;

        sysUserRole.setUserId(sysUser.getId());

        //为user绑定role信息
        boolean flag2 = roleService.saveUserAndRole(sysUserRole);

        return flag1 && flag2;
    }
}
