package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.po.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    //分组查询每个状态的完成人数
    Map<String, Object> selectStatusCount();

    //统计每个状态中备注中的人数
    Map<String, Object> selectNoteCount();

    //根据studentId删除student信息
    int deleteByStudentId(String student_id);

    //对输入内容按id或name进行模糊查询
    List<Student> selectByIdOrName(String content);

    //是否存在该学号对应的学生信息
    Student isExistedByStudentId(String studentId);

    //根据id查询学生的studentId
    String selectStudentIdById(Long id);
}
