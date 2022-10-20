package com.txy.graduate.domain.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生信息展示类：只用于查询
 */
@Data
@NoArgsConstructor
public class StudentDto{
    @ExcelIgnore
    private Long id;

    @ExcelProperty(value = "学号")
    private String studentId;

    @ExcelProperty(value = "姓名")
    private String studentName;

    @ExcelProperty(value = "部门")
    private String studentDep;

    @ExcelProperty(value = "专业")
    private String studentPre;

    @ExcelProperty(value = "班级")
    private String studentClass;

    @ExcelProperty(value = "完成状态")
    private String finnishStatus;

    @ExcelIgnore
    private String schoolCode;
}
