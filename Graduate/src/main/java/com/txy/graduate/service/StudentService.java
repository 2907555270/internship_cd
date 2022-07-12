package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.Status;
import com.txy.graduate.domain.Student;

import java.util.List;
import java.util.Map;


public interface StudentService extends IService<Student>{
    /**
     * student表查询
     */
    //分页加多条件模糊查询
    IPage<Student> queryStudent(Map<String,Object> map);

    //将输入内容作为 学号或姓名进行匹配
    List<Student> queryByIdOrName(String content);

    //根据student_id查询学生信息
    Student queryStudentById(String studentId);

    //统计所有状态的全局情况
    List<Status> queryGlobalStatus();
}
