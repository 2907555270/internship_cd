package com.txy.graduate.mapper;

import com.txy.graduate.config.Page;
import com.txy.graduate.domain.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;


@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testSelectAll(){
        List<StudentDTO> studentDTOS = studentMapper.selectAll();
        for (StudentDTO studentDTO:studentDTOS ) {
            System.out.println(studentDTO);
        }
    }

    @Test
    public void testSelectAllByPage(){
        Page<StudentDTO> page = new Page<>();
        page.setStart(0);
        page.setPageSize(3);
        List<StudentDTO> studentDTOS = studentMapper.selectAllByPage(page);
        for (StudentDTO studentDTO:studentDTOS ) {
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
        StudentDTO dto = new StudentDTO();
        Page<StudentDTO> page = new Page<>(1,3);
        dto.setPage(page);
        dto.setStudentName("张三四");
        List<StudentDTO> studentDTO = studentMapper.selectByConditionsAndPage(dto);
        System.out.println(studentDTO);
    }
}
