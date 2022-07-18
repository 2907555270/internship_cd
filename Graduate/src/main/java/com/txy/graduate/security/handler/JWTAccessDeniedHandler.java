package com.txy.graduate.security.handler;

import cn.hutool.json.JSONUtil;
import com.txy.graduate.config.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 *权限不足异常处理器
 */
@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        //返回权限不足的响应
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletOutputStream outputStream = response.getOutputStream();
        Result resp = Result.resp(500,"权限不足", null);
        outputStream.write(JSONUtil.toJsonStr(resp).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
