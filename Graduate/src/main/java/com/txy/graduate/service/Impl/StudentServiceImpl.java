package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Status;
import com.txy.graduate.domain.Student;
import com.txy.graduate.domain.vo.StatisticVo;
import com.txy.graduate.mapper.StudentMapper;
import com.txy.graduate.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper mapper;

    //@Override
    //public IPage<Student> findAllByPage(int currentPage, int pageSize) {
    //    //创建分页对象
    //    IPage<Student> page = new Page<>(currentPage, pageSize);
    //    return mapper.selectPage(page, null);
    //}
    //
    //@Override
    //public IPage<Student> findByConditionsAndPage(StudentPage studentPage) {
    //    //创建分页对象
    //    IPage<Student> page = new Page<>(studentPage.getCurrentPage(), studentPage.getPageSize());
    //    //获取student对象
    //    Student student = studentPage.getStudent();
    //    //处理模糊查询条件
    //    QueryWrapper<Student> wrapper = QueryWrapperUtil.queryWrapper_LikeMany(student);
    //    return mapper.selectPage(page, wrapper);
    //}
    //

    @Override
    public List<Status> findGlobalStatus() {
        ArrayList<Status> statuses = new ArrayList<>();

        //获取每个状态的完成人数
        Map<String, Object> statusMap = mapper.selectStatusCount();

        //获取note的数据
        Map<String, Object> noteMap = mapper.selectNoteCount();

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
            status.setUnfinished(total-value);
            status.setNoteNum(Long.parseLong(noteMap.get(key).toString()));
            statuses.add(status);
        });

        return statuses;
    }
    //
    //@Override
    //public Student findById(String student_id) {
    //    QueryWrapper<Student> wrapper = new QueryWrapper<>();
    //    wrapper.eq("student_id", student_id);
    //    return mapper.selectOne(wrapper);
    //}
    //
    //@Override
    //public List<Student> findByIdOrName(String content) {
    //    QueryWrapper<Student> wrapper = new QueryWrapper<>();
    //    wrapper.like("student_id", content);
    //    wrapper.or();
    //    wrapper.like("student_name", content);
    //    return mapper.selectList(wrapper);
    //}
    //
    //@Override
    //public Boolean updateStatus(Student student) {
    //    if (student == null)
    //        return false;
    //    return mapper.updateById(student)>0;
    //}
}
