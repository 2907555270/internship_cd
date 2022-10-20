package com.txy.graduate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.dto.StudentDto;
import com.txy.graduate.domain.po.StudentInfo;

import java.util.List;
import java.util.Map;


public interface StudentService extends IService<StudentInfo>{
    /**
     * student表查询
     */
    //查询所有学生的数据
    List<StudentDto> queryAll(String schoolCode);

    //分页查询所有学生的数据
    IPage<StudentInfo> queryByPage(String schoolCode,int currentPage,int pageSize);

    //分页加多条件模糊查询
    IPage<List<Map<String,Object>>> queryByConditionsAndPage(Map<String,Object> map,String schoolCode,int currentPage,int pageSize);

    //将输入内容作为 学号或姓名进行匹配
    IPage<StudentInfo> queryByIdOrName(String content,String schoolCode);

    //输入学生的Id，精确匹配学生信息
    Map<String,Object> queryById(Long id);

    //输入学生的id，和要获取的字段值：获取学生指定的属性
    Map<String, Object> queryPartInfoById(Map<String, Object> map);

    //获取学生的导航栏信息
    Map<String, Object> queryNav(String schoolCode);

    //统计所有状态的全局情况
    Map<String,Map<String, Object>> queryStatus(StudentInfo studentInfo);

    //修改学生的状态信息
    boolean updateStatus(Map<String,Object> map);


    /**
     * 项目无关的方法-----------------------------------------------------------------------------
     */
    //根据student_id查询学生信息
    StudentInfo queryByStudentId(String studentId);

    //新增学生信息：同时注册新用户，绑定普通用户角色
    boolean saveStudent(StudentInfo student);

    //删除学生信息：同时删除用户，解绑用户角色
    Result removeStudent(Long id);


}
