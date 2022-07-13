package com.txy.graduate.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.txy.graduate.config.Result;
import com.txy.graduate.security.config.ConstConfig;
import com.txy.graduate.util.RedisUtil;
import com.txy.graduate.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j //开启日志记录
@RestController
public class LoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/captcha")
    public Result captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 生成验证码
        String code = producer.createText();
        // 从Redis获取验证码的key
        String key = ToolUtil.getUUID();

        //验证码的图片
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        //// Base64Encoder encoder = new Base64Encoder();
        //String str = "data:image/jpeg;base64,";
        //String base64Img = str + Base64Encoder.encode(byteArrayOutputStream.toByteArray());

        // 将验证码存放到redis中
        redisUtil.hset(ConstConfig.CAPTCHA_KEY,key,code,120);

        return Result.resp(200,"ok",
                MapUtil.builder()
                        .put("key",key)
                        .put("base64Img",code)
                        .build());
    }
}
