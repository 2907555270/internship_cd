package com.txy.graduate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;

@SpringBootTest
class GraduateApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(hashpw);
    }

}
