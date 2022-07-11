package com.txy.graduate.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.txy.graduate.config.Result;
import com.txy.graduate.security.config.ConstConfig;
import com.txy.graduate.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class LoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/test1")
    public Result test1(){
        System.out.println("------1111-----");
        return Result.resp(200, "ok", null);
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/test2")
    public Result test2(){
        System.out.println("-----2222------");
        return Result.resp(200, "ok", null);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/test/login")
    public Result login(){
        String encodePwd = passwordEncoder.encode("111111");
        boolean matches = passwordEncoder.matches("111111", encodePwd);
        System.out.println("匹配结果："+matches+" - "+encodePwd);
        return Result.resp(200, "ok", matches);
    }

    @GetMapping("/captcha")
    public Result captche(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //加密密码匹配演示代码
        String encodePwd = passwordEncoder.encode("111111");
        boolean matches = passwordEncoder.matches("111111", encodePwd);
        System.out.println("匹配结果："+matches+" - "+encodePwd);

        //// 生成验证码
        //String code = producer.createText();
        //// 从Redis获取验证码的key
        //String key = ToolUtil.getUUid();

        //手动模拟
        String code = "88888";
        String key = "aaaaaa";

        //验证码的图片
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        // Base64Encoder encoder = new Base64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + Base64Encoder.encode(byteArrayOutputStream.toByteArray());

        // 存放redis
        redisUtil.hset(ConstConfig.CAPTCHA_KEY,key,code,120);

        return Result.resp(200,"ok",
                MapUtil.builder()
                        .put("key",key)
                        .put("base64Img",code)
                        .build());
    }
}
