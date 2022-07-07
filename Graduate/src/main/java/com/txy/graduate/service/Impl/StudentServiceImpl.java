package com.txy.graduate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txy.graduate.config.StudentPage;
import com.txy.graduate.domain.Statistic;
import com.txy.graduate.domain.Student;
import com.txy.graduate.mapper.StudentMapper;
import com.txy.graduate.service.StudentService;
import com.txy.graduate.util.QueryWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper mapper;

    @Override
    public IPage<Student> findAllByPage(int currentPage, int pageSize) {
        //创建分页对象
        IPage<Student> page = new Page<>(currentPage, pageSize);
        return mapper.selectPage(page, null);
    }

    @Override
    public IPage<Student> findByConditionsAndPage(StudentPage studentPage) {
        //创建分页对象
        IPage<Student> page = new Page<>(studentPage.getCurrentPage(), studentPage.getPageSize());
        //获取student对象
        Student student = studentPage.getStudent();
        //处理模糊查询条件
        QueryWrapper<Student> wrapper = QueryWrapperUtil.queryWrapper_LikeMany(student);
        return mapper.selectPage(page, wrapper);
    }

    @Override
    public Map<String, Object> findGlobalStatus() {
        HashMap<String, Object> map = new HashMap<>();
        List<Statistic> statisticList = new ArrayList<>();

        //查询总人数
        Integer total = mapper.selectCount(null);
        //查询每个状态的人数
        List<Statistic> list = mapper.countStatus();
        //获取所有状态的完成、未完成情况
        int index = 0;
        for (Statistic s : list) {
            String status = s.getStatus();
            while (index < (Integer.parseInt(status))) {
                int sum = 0;
                for (Statistic statistic : list) {
                    //找到比index大且不为空的数据
                    if (Integer.parseInt(statistic.getStatus()) > index) {
                        sum += statistic.getCompleted();
                    }
                }
                statisticList.add(new Statistic(String.valueOf(index + 1), sum, total - sum));
                index++;
            }
        }
        map.put("statisticList", statisticList);
        //查询地址改派的人数
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("note", "地址改派");
        map.put("changeAddress", mapper.selectCount(wrapper));

        return map;
    }

    @Override
    public Student findById(String student_id) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", student_id);
        return mapper.selectOne(wrapper);
    }

    @Override
    public List<Student> findByIdOrName(String content) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.like("student_id", content);
        wrapper.or();
        wrapper.like("student_name", content);
        return mapper.selectList(wrapper);
    }

    @Override
    public Boolean updateStatus(Student student) {
        if (student == null)
            return false;
        return mapper.updateById(student)>0;
    }
}
