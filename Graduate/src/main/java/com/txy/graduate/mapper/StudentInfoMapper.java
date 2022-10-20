package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.dto.StudentDto;
import com.txy.graduate.domain.po.BookInfo;
import com.txy.graduate.domain.po.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper()
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {
    //查询数据中有价值的字段数据，并替换显示内容
    List<StudentDto> selectAll(String schoolCode);

    //根据studentId删除student信息
    int deleteByStudentId(String student_id);

    //是否存在该学号对应的学生信息
    StudentInfo isExistedByStudentId(String studentId);

    //根据id查询学生的studentId
    String selectStudentIdById(Long id);

    //根据id修改学生的状态信息
    int updateStatus(@Param("map") Map<String,Object> map);

    //根据id查询学生的图书欠款未还等信息
    BookInfo selectBookInfoById(Long id);

    //查询导航筛选条件
    List<StudentInfo> selectNav(String schoolCode);
}
