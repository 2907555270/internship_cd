package com.txy.graduate.service;

import com.txy.graduate.config.Page;
import com.txy.graduate.domain.dto.StudentDTO;
import com.txy.graduate.domain.po.Student;
import com.txy.graduate.domain.vo.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void testQueryAll(){
        List<StudentDTO> studentDTOS = studentService.queryAll();
        System.out.println(studentDTOS);
    }

    @Test
    public void testQueryAllByPage(){
        Page<StudentDTO> studentDTOPage = new Page<>(2,5);
        Page<StudentDTO> dtoPage = studentService.queryByPage(studentDTOPage);
        System.out.println(dtoPage);
    }

    @Test
    public void testQueryByConditionsAndPage(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage",1);
        map.put("pageSize",3);
        map.put("studentDep","计算机");
        Page<StudentDTO> studentPage = studentService.queryByConditionsAndPage(map);
        System.out.println(studentPage);
    }

    @Test
    public void testQueryByIdOrName(){
        String content = "张三四";
        List<Student> students = studentService.queryByIdOrName(content);
        System.out.println(students);
    }

    @Test
    public void testStatistic(){
        List<Status> statuses = studentService.queryGlobalStatus();
        System.out.println(statuses);
    }
}
