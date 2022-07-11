package com.txy.graduate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraduateApplicationTests {

    @Value("${secretKey}")
    private String key;

    @Test
    void contextLoads() {
        System.out.println(key);
    }

}
