package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.domain.vo.Status;
import com.txy.graduate.domain.po.Student;

import java.util.List;
import java.util.Map;


public interface StudentService extends IService<Student>{
    /**
     * student表查询
     */

    //查询所有学生的数据
    List<Student> queryAll(Integer... args);

    //分页加多条件模糊查询
    IPage<Student> queryStudent(Map<String,Object> map);

    //将输入内容作为 学号或姓名进行匹配
    List<Student> queryByIdOrName(String content);

    //根据student_id查询学生信息
    boolean isExistedByStudentId(String studentId);

    //统计所有状态的全局情况
    List<Status> queryGlobalStatus();

    //新增学生信息：同时注册新用户，绑定普通用户角色
    boolean saveStudent(Student student);

    //删除学生信息：同时删除用户，解绑用户角色
    boolean removeStudent(Long id);


}
