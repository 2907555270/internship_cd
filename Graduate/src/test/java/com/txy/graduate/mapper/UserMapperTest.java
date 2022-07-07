package com.txy.graduate.mapper;

import com.txy.graduate.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper mapper;

    @Test
    public void test_findAll(){
        mapper.selectList(null);
    }

    @Test
    public void test_insert(){
        User user = new User("abcd","zhangsan","","",null);
        mapper.insert(user);
    }
}
