package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.config.Result;
import com.txy.graduate.domain.dto.StudentDTO;
import com.txy.graduate.domain.vo.Status;
import com.txy.graduate.domain.po.Student;
import com.txy.graduate.domain.po.SysRole;
import com.txy.graduate.domain.po.SysUser;
import com.txy.graduate.domain.po.SysUserRole;
import com.txy.graduate.mapper.StudentMapper;
import com.txy.graduate.mapper.SysRoleMapper;
import com.txy.graduate.mapper.SysUserMapper;
import com.txy.graduate.service.StudentService;
import com.txy.graduate.util.QueryUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //设置基础查询字段列表
    private final String[] baseColumns = {"id","student_id","student_name","student_dep",
            "student_pre","student_class","student_status"};

    //学生权限
    @Value("normal")
    private String code;


    /**
     * 查询
     */

    @Override
    public List<StudentDTO> queryAll() {
        return studentMapper.selectAll();
    }

    @SneakyThrows
    @Override
    public IPage<Student> queryStudent(Map<String, Object> map) {

        //判断map为空,不执行查询
        if (map == null)
            return null;

        Student student;
        //判断map的结构中是否有student层级
        Object obj = map.get("student");
        //根据map层级判断封装方式
        if (obj == null)
            student = QueryUtil.map2obj(map, Student.class);
        else
            student = QueryUtil.map2obj((Map<String, Object>) map.get("student"), Student.class);

        //获取查询条件
        QueryWrapper<Student> wrapper = QueryUtil.queryWrapper_LikeMany(student);
        wrapper.select(baseColumns);

        return studentMapper.selectPage(QueryUtil.getPageFromMap(map), wrapper);
    }

    @Override
    public List<Student> queryByIdOrName(String content) {
        return studentMapper.selectByIdOrName(content);
    }

    @Override
    public boolean isExistedByStudentId(String studentId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",studentId);
        wrapper.select("id");
        return studentMapper.selectOne(wrapper)!=null;
    }


    @Override
    public List<Status> queryGlobalStatus() {
        ArrayList<Status> statuses = new ArrayList<>();

        //获取每个状态的完成人数
        Map<String, Object> statusMap = studentMapper.selectStatusCount();

        //获取note的数据
        Map<String, Object> noteMap = studentMapper.selectNoteCount();

        //获取总人数
        Long total = Long.parseLong(statusMap.get("total").toString());
        statusMap.remove("total");

        //构建各个状态的统计结果
        statusMap.forEach((key, val) -> {
            Long value = Long.parseLong(val.toString());
            Status status = new Status();
            status.setTitle(key);
            status.setCompleted(value);
            status.setTotal(total);
            status.setUnfinished(total - value);
            status.setNoteNum(Long.parseLong(noteMap.get(key).toString()));
            statuses.add(status);
        });

        return statuses;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean saveStudent(Student student) {

        //添加学生student信息
        boolean flag1 = studentMapper.insert(student) > 0;

        //添加学生用户user信息
        SysUser sysUser = new SysUser();
        sysUser.setUsername(student.getStudentId());
        sysUser.setPassword(student.getStudentId());
        boolean flag2 = sysUserMapper.insert(sysUser) > 0;

        //根据学生对应的权限code查询权限id
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        wrapper.select("id");
        Long role_id = sysRoleMapper.selectOne(wrapper).getId();

        //为学生用户user设置权限
        SysUserRole sysUserRole = new SysUserRole();
        //上面插入user的主键id会被自动封装到对象中
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(role_id);
        //todo：需要将SysUserRole单独分出去一个Mapper
        boolean flag3 = sysRoleMapper.insertUserAndRole(sysUserRole) > 0;

        return flag1 && flag2 && flag3;
    }

    //需要抛出异常才能触发此处的事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result removeStudent(Long id) {

        //根据学生id去查询学生的全部信息
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.select("student_id");
        studentQueryWrapper.eq("id",id);
        Student student = studentMapper.selectOne(studentQueryWrapper);
        if(student==null)
            return Result.result(500,false,null,"未找到该学号对应的学生信息 -_-");
        String studentId = student.getStudentId();

        //根据学生id信息查询用户id
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("id");
        userQueryWrapper.eq("username",studentId);
        Long userId = sysUserMapper.selectOne(userQueryWrapper).getId();

        //解绑学生的权限信息
        //todo:需要将SysUserRole分出去一个Mapper
        if(!(sysRoleMapper.deleteUserAndRoleByUId(userId) > 0))
            return Result.result(500,false,null,"解绑学生的权限信息失败 -_-");

        //删除学生账号
        if(!(sysUserMapper.deleteByUserName(studentId) > 0))
            return Result.result(500,false,null,"删除学生账号失败 -_-");


        //删除学生student信息
        if(!(studentMapper.deleteById(id) > 0))
            return Result.result(500,false,null,"删除学生信息失败 -_-");

        return Result.result(200,true,null,"删除成功 ^_^");
    }
}
