package com.txy.graduate;

import com.txy.graduate.config.Result;
import com.txy.graduate.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootTest
class GraduateApplicationTests {



    @Test
    void contextLoads() throws IOException {
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(hashpw);
    }

}
