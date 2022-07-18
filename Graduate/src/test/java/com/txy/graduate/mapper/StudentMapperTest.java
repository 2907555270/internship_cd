package com.txy.graduate.mapper;

import com.txy.graduate.config.Page;
import com.txy.graduate.domain.dto.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testSelectAll(){
        List<StudentDto> studentDTOS = studentMapper.selectAll();
        for (StudentDto studentDTO:studentDTOS ) {
            System.out.println(studentDTO);
        }
    }

    @Test
    public void testSelectAllByPage(){
        Page<StudentDto> page = new Page<>();
        page.setStart(0);
        page.setPageSize(3);
        List<StudentDto> studentDTOS = studentMapper.selectAllByPage(page);
        for (StudentDto studentDTO:studentDTOS ) {
            System.out.println(studentDTO);
        }
    }

    @Test
    public void testSelectAmount(){
        System.out.println(studentMapper.selectAmount());
        System.out.println(studentMapper.selectAmountQuick());
    }

    @Test
    public void testSelectConditionsAndPage(){
        StudentDto dto = new StudentDto();
        Page<StudentDto> page = new Page<>(1,3);
        dto.setPage(page);
        dto.setStudentName("张三四");
        List<StudentDto> studentDTO = studentMapper.selectByConditionsAndPage(dto);
        System.out.println(studentDTO);
    }
}
