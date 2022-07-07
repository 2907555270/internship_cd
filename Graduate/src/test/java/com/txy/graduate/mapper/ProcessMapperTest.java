package com.txy.graduate.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProcessMapperTest {
    @Autowired
    private ProcessMapper mapper;

    @Test
    public void test_findAll(){
        mapper.selectList(null);
    }
}
