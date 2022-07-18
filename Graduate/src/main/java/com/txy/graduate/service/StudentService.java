package com.txy.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.config.Page;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.dto.StudentDTO;
import com.txy.graduate.domain.vo.Status;
import com.txy.graduate.domain.po.Student;

import java.util.List;
import java.util.Map;


public interface StudentService extends IService<Student>{
    /**
     * student表查询
     */

    //查询所有学生的数据
    List<StudentDTO> queryAll();

    //分页查询所有学生的数据
    Page<StudentDTO> queryByPage(Page<StudentDTO> page);

    //分页加多条件模糊查询
    Page<StudentDTO> queryByConditionsAndPage(Map<String,Object> map);

    //将输入内容作为 学号或姓名进行匹配
    List<Student> queryByIdOrName(String content);



    //统计所有状态的全局情况
    List<Status> queryGlobalStatus();


    /**
     * 项目无关的方法-----------------------------------------------------------------------------
     */
    //根据student_id查询学生信息
    Student queryByStudentId(String studentId);

    //新增学生信息：同时注册新用户，绑定普通用户角色
    boolean saveStudent(Student student);

    //删除学生信息：同时删除用户，解绑用户角色
    Result removeStudent(Long id);


}
