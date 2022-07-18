package com.txy.graduate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txy.graduate.config.Page;
import com.txy.graduate.domain.dto.StudentDTO;
import com.txy.graduate.domain.po.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper()
public interface StudentMapper extends BaseMapper<Student> {
    //查询数据中有价值的字段数据，并替换显示内容
    List<StudentDTO> selectAll();

    //分页查询学生的所有有价值的字段数据，并替换显示内容
    List<StudentDTO> selectAllByPage(Page<StudentDTO> page);

    //统计学生表的总数据量：精确统计
    int selectAmount();

    //粗略统计学生表的数据量：模糊统计MAX(ID)
    int selectAmountQuick();

    //多条件后缀模糊查询+分页查询 学生信息
    List<StudentDTO> selectByConditionsAndPage(StudentDTO studentDTO);


    //分组查询每个状态的完成人数
    Map<String, Object> selectStatusCount();

    //统计每个状态中备注中的人数
    Map<String, Object> selectNoteCount();

    //根据studentId删除student信息
    int deleteByStudentId(String student_id);

    //对输入内容按id或name进行模糊查询
    List<Student> selectByIdOrName(String content);

    //是否存在该学号对应的学生信息
    Student isExistedByStudentId(String studentId);

    //根据id查询学生的studentId
    String selectStudentIdById(Long id);

}
