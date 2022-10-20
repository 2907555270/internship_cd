package com.txy.graduate.security.handler;

import cn.hutool.json.JSONUtil;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.po.SysRole;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.config.ConstConfig;
import com.txy.graduate.service.ISysRoleService;
import com.txy.graduate.service.ISysUserService;
import com.txy.graduate.util.JwtUtil;
import com.txy.graduate.util.RedisUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录成功过滤器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    JwtUtil jwtUtil;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysUserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        // 生成jwt，并放置到请求头中
        String jwt = jwtUtil.createToken(authentication.getName());

        // 获取当前用户角色并返回，前端根据角色跳对应页面
        String username = authentication.getName();
        SysUser sysUser = userService.queryUserByUserName(username);

        // 将用户的信息存放在redis中
        redisUtil.set(ConstConfig.USER_KEY+":"+username,sysUser,3600 * 72);

        // 获取用户的权限
        List<SysRole> roles = roleService.queryRoleByUid(sysUser.getId());

        //封装用户的信息
        Map<String, Object> map = new HashMap<>();
        map.put("role","ROLE_"+roles.get(0).getCode());
        map.put("id",sysUser.getId());
        map.put("username",sysUser.getUsername());

        // 响应登录成功请求
        httpServletResponse.setHeader(jwtUtil.getHeader(),jwt);
        Result resp = Result.result(200,true, "登录成功", map);
        outputStream.write(JSONUtil.toJsonStr(resp).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
