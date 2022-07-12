package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Status;
import com.txy.graduate.domain.Student;
import com.txy.graduate.domain.sys.SysRole;
import com.txy.graduate.domain.sys.SysUser;
import com.txy.graduate.domain.sys.SysUserRole;
import com.txy.graduate.mapper.StudentMapper;
import com.txy.graduate.mapper.SysRoleMapper;
import com.txy.graduate.mapper.SysUserMapper;
import com.txy.graduate.service.StudentService;
import com.txy.graduate.util.QueryWrapperUtil;
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

    //学生权限
    @Value("normal")
    private String code;

    /**
     * 查询
     */

    @SneakyThrows
    @Override
    public IPage<Student> queryStudent(Map<String, Object> map) {
        //判断map是否为空
        if (map == null) {
            return null;
        }

        Student student;

        //判断map的结构中是否有student层级
        Object obj = map.get("student");
        //根据map层级判断封装方式
        if (obj == null)
            student = QueryWrapperUtil.map2obj(map, Student.class);
        else
            student = QueryWrapperUtil.map2obj((Map<String, Object>) map.get("student"), Student.class);

        //获取查询条件
        QueryWrapper<Student> wrapper = QueryWrapperUtil.queryWrapper_LikeMany(student);

        return studentMapper.selectPage(QueryWrapperUtil.getPageFromMap(map), wrapper);
    }

    @Override
    public List<Student> queryByIdOrName(String content) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.like("student_id", content);
        //TODO:尽量避免使用or,采用union all
        wrapper.or();
        wrapper.like("student_name", content);
        return studentMapper.selectList(wrapper);
    }

    @Override
    public Student queryStudentById(String studentId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        return studentMapper.selectOne(wrapper);
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

    @Transactional
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
        wrapper.eq("code", code);
        SysRole sysRole = sysRoleMapper.selectOne(wrapper);

        //为学生用户user设置权限
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(sysRole.getId());
        boolean flag3 = sysRoleMapper.insertUserAndRole(sysUserRole) > 0;
        return flag1 && flag2 && flag3;
    }

    //TODO:执行了太多的查询，需要优化SQL
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean removeStudent(Long id) {

        //根据学生id去查询学生的全部信息
        Student student = studentMapper.selectById(id);

        //根据学生id信息查询用户信息
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",student.getStudentId());
        SysUser sysUser = sysUserMapper.selectOne(wrapper);

        //解绑学生的权限信息
        boolean flag1 = sysRoleMapper.deleteUserAndRoleByUId(sysUser.getId()) > 0;

        //删除学生账号
        boolean flag2 = sysUserMapper.deleteByUserName(sysUser.getUsername()) > 0;

        //删除学生student信息
        boolean flag3 = studentMapper.deleteById(id)>0;

        return flag1 && flag2 && flag3;
    }
}
