package com.txy.graduate.security.filter;

import com.txy.graduate.config.ConstConfig;
import com.txy.graduate.security.exception.CaptchaException;
import com.txy.graduate.security.handler.LoginFailHandler;
import com.txy.graduate.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptcheFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private LoginFailHandler failHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.contains("/login") && httpServletRequest.getMethod().equals("POST")){
            // 校验验证码
            try {
                validate(httpServletRequest);
            } catch (CaptchaException e) {//认证失败后，交由认证失败处理器返回失败信息
                failHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validate(HttpServletRequest request) throws CaptchaException {
        String code = request.getParameter("code");
        String key = request.getParameter("key");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)){
            throw new CaptchaException("验证不能为空");
        }

        if (!code.equals(redisUtil.hget(ConstConfig.CAPTCHA_KEY, key))){
            throw new CaptchaException("验证码错误");
        }
        //删掉redis的验证码(一次性使用)
        redisUtil.hdel(ConstConfig.CAPTCHA_KEY, key);
    }
}
