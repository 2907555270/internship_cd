package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.domain.Status;
import com.txy.graduate.domain.Student;
import com.txy.graduate.mapper.StudentMapper;
import com.txy.graduate.service.StudentService;
import com.txy.graduate.util.QueryWrapperUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper mapper;

    /**
     * 查询
     */

    @SneakyThrows
    @Override
    public IPage<Student> queryStudent(Map<String, Object> map) {
        //判断map是否为空
        if (map==null){
            return null;
        }

        Student student = null;

        //判断map的结构中是否有student层级
        Object obj = map.get("student");
        //根据map层级判断封装方式
        if(obj==null)
            student = QueryWrapperUtil.map2obj(map,Student.class);
        else
            student = QueryWrapperUtil.map2obj((Map<String, Object>) map.get("student"), Student.class);

        //获取查询条件
        QueryWrapper<Student> wrapper = QueryWrapperUtil.queryWrapper_LikeMany(student);

        return mapper.selectPage(QueryWrapperUtil.getPageFromMap(map),wrapper);
    }

    @Override
    public List<Student> queryByIdOrName(String content) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.like("student_id", content);
        //TODO:尽量避免使用or,采用union all
        wrapper.or();
        wrapper.like("student_name", content);
        return mapper.selectList(wrapper);
    }

    @Override
    public Student queryStudentById(String studentId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id",studentId);
        return mapper.selectOne(wrapper);
    }


    @Override
    public List<Status> queryGlobalStatus() {
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
}
