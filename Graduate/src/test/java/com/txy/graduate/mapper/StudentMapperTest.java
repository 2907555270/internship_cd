package com.txy.graduate.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test1(){
        System.out.println(studentMapper.selectStatusCount());
        System.out.println(studentMapper.selectNoteCount());
    }

}
