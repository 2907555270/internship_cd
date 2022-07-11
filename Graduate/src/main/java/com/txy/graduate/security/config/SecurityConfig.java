package com.txy.graduate.security.config;

import com.txy.graduate.security.filter.*;
import com.txy.graduate.security.handler.*;
import com.txy.graduate.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailHandler loginFailHandler;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private CaptcheFilter captcheFilter;
    @Autowired
    private JWTAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtLogoutSuccecsHandler logoutSuccessHandler;

    @Bean
    JWTFilter jwtFilter() throws Exception {
        return new JWTFilter(authenticationManager());
    }

    /**
     * 注入密码加密方式
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha", //验证码
            "/favicon.ico",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                //登录配置
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandler)
                .and()

                //退出配置
                .logout()
                //配置退出成功处理器
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()

                //禁用session:无状态会话
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                //访问认证配置
                // 白名单中的url请求不进行拦截
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                // 其余全部拦截
                .anyRequest().authenticated()

                // 异常处理
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) //未认证异常处理器
                .accessDeniedHandler(accessDeniedHandler) //权限不足异常处理器

                //配置自定义过滤器
                .and()
                .addFilter(jwtFilter())
                .addFilterBefore(captcheFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }
}
