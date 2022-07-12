package com.txy.graduate.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 学生表(36个字段，后续可分表)
 * 状态码：0-未完成 1-已完成 2-备注
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_student")
public class Student {
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
     * 毕业手续完成进度
     */
    @TableField("student_status")
    private Byte studentStatus;

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
     * 报到证备注
     */
    @TableField("report_note")
    private String reportNote;

    /**
     * 是否备注报到证
     */
    @TableField("report_note_status")
    private Byte reportNoteStatus;

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
     * 是否备注毕业审核
     */
    @TableField("audit_note_status")
    private Byte auditNoteStatus;

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
     * 是否备注欠费缴纳
     */
    @TableField("owe_note_status")
    private Byte oweNoteStatus;

    /**
    * 图书归还状态
    */
    @TableField("book_status")
    private Byte bookStatus;

    /**
     * 图书待还数量
     */
    @TableField("book_amount")
    private Integer bookAmount;

    /**
     * 图书欠款
     */
    @TableField("book_money")
    private BigDecimal bookMoney;

    /**
     * 图书归还备注
     */
    @TableField("book_note")
    private String bookNote;

    /**
     * 是否备注图书归还
     */
    @TableField("book_note_status")
    private Byte bookNoteStatus;

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
     * 是否备注档案确认
     */
    @TableField("archives_note_status")
    private Byte archivesNoteStatus;

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
     * 是否公寓退宿备注
     */
    @TableField("dormitory_note_status")
    private Byte dormitoryNoteStatus;

    /**
    * 毕业证领取状态
    */
    @TableField("diploma_status")
    private Byte diplomaStatus;

    /**
    * 毕业证领取时间
    */
    @TableField("diploma_time")
    private Date diplomaTime;

    /**
     * 毕业证领取备注
     */
    @TableField("diploma_note")
    private String diplomaNote;

    /**
     * 是否备注毕业证领取
     */
    @TableField("diploma_note_status")
    private Byte diplomaNoteStatus;

    /**
    * 备注
    */
    @TableField("note")
    private String note;
}