package com.txy.graduate.mapper;

import com.txy.graduate.domain.Statistic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test_findAll(){
        studentMapper.selectList(null);
    }

    @Test
    public void test_selectMaps(){
        List<Map<String, Object>> maps = studentMapper.selectMaps(null);
        System.out.println(maps);
    }

    @Test
    public void test_countStatus(){
        List<Statistic> list = studentMapper.countStatus();
        System.out.println(list);
    }

}
