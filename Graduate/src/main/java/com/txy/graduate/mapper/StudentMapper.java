package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.domain.Statistic;
import com.txy.graduate.domain.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    //
    ////分组查询每个状态的完成人数
    //List<Statistic> countStatus();
}
