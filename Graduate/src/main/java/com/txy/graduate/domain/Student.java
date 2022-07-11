package com.txy.graduate.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    /**
    * 主键Id
    */
    @TableId
    private Long id;

    /**
    * 学号
    */
    private String studentId;

    /**
    * 姓名
    */
    private String studentName;

    /**
    * 系部
    */
    private String studentDep;

    /**
    * 专业
    */
    private String studentPre;

    /**
    * 班级
    */
    private String studentClass;

    /**
    * 总完成状态
    */
    private Integer studentStatus;

    /**
    * 报到证状态
    */
    private Integer reportStatus;

    /**
    * 报到证领取时间
    */
    private Date reportTime;

    /**
    * 毕业审核状态
    */
    private Integer auditStatus;

    /**
    * 欠费缴纳状态
    */
    private Integer oweStatus;

    /**
    * 欠费金额
    */
    private Long oweAmount;

    /**
    * 图书归还状态
    */
    private Integer bookStatus;

    /**
    * 档案状态
    */
    private Integer archivesStatus;

    /**
    * 档案去向
    */
    private String archivesAddress;

    /**
    * 档案确认时间
    */
    private Date archivesTime;

    /**
    * 公寓退宿状态
    */
    private Integer dormitoryStatus;

    /**
    * 毕业证领取状态
    */
    private Integer diplomaStatus;

    /**
    * 毕业证领取时间
    */
    private Date diplomaTime;

    /**
    * 备注
    */
    private String note;
}