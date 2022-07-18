package com.txy.graduate.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.txy.graduate.config.Result;
import com.txy.graduate.config.ConstConfig;
import com.txy.graduate.util.RedisUtil;
import com.txy.graduate.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j //开启日志记录
@RestController
public class LoginController {

    @Resource
    private Producer producer;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        // 生成验证码
        String code = producer.createText();
        // 从Redis获取验证码的key
        String key = ToolUtil.getUUID();

        //验证码的图片
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        String str = "data:image/jpeg;base64,";
        String base64Img = str + Base64Encoder.encode(byteArrayOutputStream.toByteArray());

        // 将验证码存放到redis中
        redisUtil.hset(ConstConfig.CAPTCHA_KEY,key,code,120);

        return Result.result(200,true,"成功获取到验证码 ^_^",
                MapUtil.builder()
                        .put("key",key)
                        .put("base64Img",base64Img)
                        .build());
    }
}
