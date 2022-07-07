package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.config.StudentPage;
import com.txy.graduate.domain.Student;

import java.util.List;
import java.util.Map;


public interface StudentService extends IService<Student>{
    //按照分页查询所有数据
    IPage<Student> findAllByPage(int currentPage,int pageSize);

    //按照多条件模糊查询数据
    IPage<Student> findByConditionsAndPage(StudentPage studentPage);

    //统计所有状态的全局情况
    Map<String,Object> findGlobalStatus();

    //根据student_id获取学生的所有信息
    Student findById(String student_id);

    //将输入内容作为 学号或姓名进行匹配
    List<Student> findByIdOrName(String content);

    //更新指定学生的离校状态
    Boolean updateStatus(Student student);
}
