package com.txy.graduate.security.handler;

import cn.hutool.json.JSONUtil;
import com.txy.graduate.config.Result;
import com.txy.graduate.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //如何上下文中的用户信息没有被清除，则手动调用退出
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        //响应成功退出的请求
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader(jwtUtil.getHeader(), "");
        Result resp = Result.result(200,true, "退出成功", null);
        outputStream.write(JSONUtil.toJsonStr(resp).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
