package com.txy.graduate.mapper;

import com.txy.graduate.domain.dto.StudentDto;
import com.txy.graduate.domain.po.StudentInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentInfoMapper studentMapper;

    @Test
    public void testSelectAll(){
        List<StudentDto> studentDTOS = studentMapper.selectAll("sc1010");
        for (StudentDto studentDTO:studentDTOS ) {
            System.out.println(studentDTO);
        }
    }

    @Test
    public void testSelectAllByPage(){

    }

    @Test
    public void testSelectConditionsAndPage(){

    }

    @Test
    public void testSelectNav() {
        Map<String ,Object> map = new HashMap<>();
        HashSet<String> setDep = new HashSet<>();
        HashSet<String> setPre = new HashSet<>();
        HashSet<String> setClass = new HashSet<>();

        List<StudentInfo> studentInfos = studentMapper.selectNav("sc1010");
        studentInfos.forEach(s->{
            setDep.add(s.getStudentDep());
            setPre.add(s.getStudentPre());
            setClass.add(s.getStudentClass());
        });

        map.put("studentDep",setDep);
        map.put("studentPre",setPre);
        map.put("studentClass",setClass);

        System.out.println(map);
    }
}
