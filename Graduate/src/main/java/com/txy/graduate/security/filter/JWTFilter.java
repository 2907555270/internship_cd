package com.txy.graduate.security.filter;
import cn.hutool.core.util.StrUtil;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.security.config.ConstConfig;
import com.txy.graduate.security.service.UserDetailServiceImpl;
import com.txy.graduate.service.ISysUserService;
import com.txy.graduate.util.JwtUtil;
import com.txy.graduate.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicAuthenticationFilter {
    @Autowired
    private JwtUtil jwtUtil;

    public JWTFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //从Header请求中获取jwt令牌
        String jwt = request.getHeader(jwtUtil.getHeader());

        //jwt令牌检验
        if (StrUtil.isBlankOrUndefined(jwt)){
            // token为空继续往下走，因为可能是白名单的匿名访问
            chain.doFilter(request,response);
            return;
        }
        Claims claim = jwtUtil.getClaimByToken(jwt);
        if (null == claim){
            throw new JwtException("token 异常");
        }
        if (jwtUtil.isTokenExpired(claim)){
            throw new JwtException("token 过期");
        }

        //token正常，拿到用户账号
        String userName = claim.getSubject();

        // 可以获取用户信息，权限等交由security,然后继续往下走
        SysUser sysUser = sysUserService.getUserByUserName(userName);

        //将用户的身份信息保存在redis中
        redisUtil.set(ConstConfig.USER_KEY+":"+userName,sysUser);

        // 参数：用户名，密码，权限信息 ---> 生成jwt token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, null, userDetailsService.getAuthority(sysUser.getId()));

        //将用户的身份信息保存在上下文中
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request,response);
    }
}
