package com.txy.graduate.domain.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 学生基本信息表
 * 状态码：0-未完成 1-已完成 2-备注
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_student_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentInfo implements Serializable {
    /**
    * 主键Id
    */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
    * 学号
    */
    @TableField("student_id")
    @NotNull(message = "学号不能为空")
    private String studentId;

    /**
    * 姓名
    */
    @TableField("student_name")
    @NotNull(message = "学生姓名不能为空")
    private String studentName;

    /**
    * 系部
    */
    @TableField("student_dep")
    @NotNull(message = "部门不能为空")
    private String studentDep;

    /**
    * 专业
    */
    @TableField("student_pre")
    @NotNull(message = "专业不能为空")
    private String studentPre;

    /**
    * 班级
    */
    @TableField("student_class")
    @NotNull(message = "班级不能为空")
    private String studentClass;

    /**
     * 身份证号
     */
    @TableField("id_card")
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    /**
     * 学生状态
     */
    @TableField("status")
    private Byte status;

    /**
     * 院校代码
     */
    @TableField("school_code")
    private String schoolCode;

    /**
     * 毕业手续完成状态
     */
    @TableField(exist = false)
    private Byte graduationStatus;
}