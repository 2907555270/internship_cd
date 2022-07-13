package com.txy.graduate.security.service;

import com.txy.graduate.security.entity.SecurityUser;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    //自定义配置用户信息存取
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserByUserName(s);
        if (sysUser == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new SecurityUser(sysUser.getId(),sysUser.getUsername(),sysUser.getPassword(),getAuthority(sysUser.getId()));
    }

    /**根据userID获取权限（角色，菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getAuthority(Long userId){
        //角色（ROLE_admin）菜单操作权限（sys:user:list）
        String authority = sysUserService.getUserAuthorityInfo(userId);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
