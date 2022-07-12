package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    //分组查询每个状态的完成人数
    Map<String, Object> selectStatusCount();

    //统计每个状态中备注中的人数
    Map<String, Object> selectNoteCount();

    //根据studentId删除student信息
    int deleteByStudentId(Long student_id);
}
