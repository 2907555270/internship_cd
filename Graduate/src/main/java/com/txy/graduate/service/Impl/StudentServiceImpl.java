package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.config.Page;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Resource(name = "studentMapper")
    private StudentMapper studentMapper;

    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;

    @Resource(name = "sysRoleMapper")
    private SysRoleMapper sysRoleMapper;

    //学生权限
    @Value("normal")
    private String code;


    /**
     * 查询
     */

    @Override
    //查询所有的学生的基础信息
    public List<StudentDTO> queryAll() {
        return studentMapper.selectAll();
    }

    @Override
    //分页查询所有学生的基础信息
    public Page<StudentDTO> queryByPage(Page<StudentDTO> page) {
        //统计学生的数据量:精确统计
        page.setCount(studentMapper.selectAmount());
        //查询学生的数据
        page.setObjects(studentMapper.selectAllByPage(page));
        page.setTotal(page.getObjects().size());
        return page;
    }

    @SneakyThrows
    @Override
    public Page<StudentDTO> queryByConditionsAndPage(Map<String, Object> map) {
        //判断map为空,不执行查询
        if (map == null)
            return null;

        //将map中的student层级下的所有属性展平
        StudentDTO studentDTO = QueryUtil.map2obj(map, StudentDTO.class);

        //封装分页条件
        Page<StudentDTO> page = new Page<>((int) map.get("currentPage"), (int) map.get("pageSize"));
        studentDTO.setPage(page);

        //执行模糊+分页查询
        List<StudentDTO> studentDTOS = studentMapper.selectByConditionsAndPage(studentDTO);

        //封装分页查询结果
        page.setObjects(studentDTOS);
        page.setTotal(studentDTOS.size());
        page.setCount(studentMapper.selectAmount());
        return page;
    }

    @Override
    public List<Student> queryByIdOrName(String content) {
        return studentMapper.selectByIdOrName(content);
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

    @Override
    public Student queryByStudentId(String studentId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",studentId);
        wrapper.select("id");
        return studentMapper.selectOne(wrapper);
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
