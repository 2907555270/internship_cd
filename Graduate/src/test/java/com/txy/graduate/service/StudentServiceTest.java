package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.txy.graduate.config.StudentPage;
import com.txy.graduate.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void test_findAllByPage() {
        System.out.println(studentService.findAllByPage(1, 2).getRecords());
    }

    @Test
    public void test_findByConditionsAndPage() {
        Student student = studentService.findAllByPage(1, 1).getRecords().get(0);
        StudentPage studentPage = new StudentPage(1, 4, student);
        IPage<Student> pageInfo = studentService.findByConditionsAndPage(studentPage);
        System.out.println(pageInfo.getRecords());
    }

    @Test
    public void test_findGlobalStatus() {
        System.out.println(studentService.findGlobalStatus());
    }

    @Test
    public void test_findById(){
        System.out.println(studentService.findById("1632501"));
    }

    @Test
    public void test_findByIdOrName(){
        System.out.println(studentService.findByIdOrName("张"));
    }
}
