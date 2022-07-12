package com.txy.graduate;

import com.txy.graduate.config.Result;
import com.txy.graduate.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootTest
class GraduateApplicationTests {

    @Autowired
    private FileUtil fileUploadUtil;

    @Test
    void contextLoads() throws IOException {
        MultipartFile[] multipartFiles = new MultipartFile[2];
        Result result = fileUploadUtil.uploadPics(multipartFiles, Process.class);
        System.out.println(result);
    }

}
