package com.txy.graduate.domain.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 学生业务表 状态码：0：未完成，1：已完成...
    */
@Data
@NoArgsConstructor
@TableName("tb_student_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDetail implements Serializable {
    /**
    * 主键id
    */
    @TableField("id")
    private Long id;

    /**
    * 身份证：唯一标识，不允许为空
    */
    @TableField("id_card")
    private String idCard;

    /**
    * 用户名
    */
    @TableField("username")
    private String username;

    /**
    * 毕业手续完成进度
    */
    @TableField("graduation_status")
    private Byte graduationStatus;

    /**
    * 报到证领取状态
    */
    @TableField("report_status")
    private Byte reportStatus;

    /**
    * 报到证领取时间
    */
    @TableField("report_time")
    private Date reportTime;

    /**
    * 报到证领取备注
    */
    @TableField("report_note")
    private String reportNote;

    /**
    * 毕业审核状态
    */
    @TableField("audit_status")
    private Byte auditStatus;

    /**
    * 毕业审核备注
    */
    @TableField("audit_note")
    private String auditNote;

    /**
    * 欠费缴纳状态
    */
    @TableField("owe_status")
    private Byte oweStatus;

    /**
    * 欠费金额
    */
    @TableField("owe_amount")
    private BigDecimal oweAmount;

    /**
    * 欠费缴纳备注
    */
    @TableField("owe_note")
    private String oweNote;

    /**
    * 图书归还状态
    */
    @TableField("book_status")
    private Byte bookStatus;

    /**
    * 图书归还备注
    */
    @TableField("book_note")
    private String bookNote;

    /**
    * 档案确认状态
    */
    @TableField("archives_status")
    private Byte archivesStatus;

    /**
    * 档案去向
    */
    @TableField("archives_address")
    private String archivesAddress;

    /**
    * 档案确认时间
    */
    @TableField("archives_time")
    private Date archivesTime;

    /**
    * 档案确认备注
    */
    @TableField("archives_note")
    private String archivesNote;

    /**
    * 公寓退宿状态
    */
    @TableField("dormitory_status")
    private Byte dormitoryStatus;

    /**
    * 公寓退宿备注
    */
    @TableField("dormitory_note")
    private String dormitoryNote;

    /**
    * 毕业证领取时间
    */
    @TableField("diploma_time")
    private Date diplomaTime;

    /**
    * 毕业证领取状态
    */
    @TableField("diploma_status")
    private Byte diplomaStatus;

    /**
    * 毕业证领取备注
    */
    @TableField("diploma_note")
    private String diplomaNote;

    /**
    * 备注
    */
    @TableField("note")
    private String note;
}