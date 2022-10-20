package com.txy.graduate.service;

import com.txy.graduate.domain.dto.StudentDto;
import com.txy.graduate.domain.po.StudentDetail;
import com.txy.graduate.domain.po.StudentInfo;
import com.txy.graduate.mapper.StudentDetailMapper;
import com.txy.graduate.util.QueryUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void testQueryAll(){
        List<StudentDto> studentDTOS = studentService.queryAll("sc1010");
        for (StudentDto dto:studentDTOS ) {
            System.out.println(dto);
        }
    }

    @Test
    public void testQueryAllByPage(){

    }

    @Test
    public void testQueryByConditionsAndPage(){

    }

    @Test
    public void testQueryByIdOrName(){
    }

    @Test
    public void testStatistic(){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setSchoolCode("sc1010");
        Map<String,Map<String,Object>> statuses = studentService.queryStatus(studentInfo);
        System.out.println(statuses);
    }

    @Test
    public void testUpdateStatus() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",2);
        map.put("reportStatus",1);
        boolean flag = studentService.updateStatus(map);
        System.out.println(flag);
    }
}
