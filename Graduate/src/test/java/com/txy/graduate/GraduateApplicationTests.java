package com.txy.graduate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class GraduateApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(hashpw);
    }

    @Test
    void PageTest(){
        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        list.add("ok");
    }

}
