package com.txy.graduate.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.StudentPage;
import com.txy.graduate.domain.Student;
import com.txy.graduate.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Map;

@SpringBootTest
public class QueryWrapperUtilTest {

    @Autowired
    private StudentService studentService;

    //创建一个数据对象用于测试
    private final Student student = new Student();

    @Test
    public void test_obj2map() {
        //映射为map
        student.setStudentId("1632501");
        student.setStudentName("胡佳绩");
        Map<String, Object> map = QueryWrapperUtil.obj2map(student);
        System.out.println(map);
    }

    @Test
    public void test_ManyLike() {
        student.setStudentDep("计算机系");
        System.out.println(student);
        StudentPage studentPage = new StudentPage(1, 2, student);
        IPage<Student> pageInfo = studentService.findByConditionsAndPage(studentPage);
        System.out.println(pageInfo.getRecords());
    }
}
