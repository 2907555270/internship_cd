package com.txy.graduate.domain.po;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 图书借阅情况
    */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("tb_book_info")
public class BookInfo implements Serializable {
    /**
    * 主键id
    */
    private Long id;

    /**
    * 用户名
    */
    private String username;

    /**
    * 身份证号：唯一标识，不允许为空
    */
    private String idCard;

    /**
    * 欠费金额
    */
    private BigDecimal oweAmount;

    /**
    * 待还图书
    */
    private Integer bookAmount;

    /**
    * 院校代码
    */
    private String schoolCode;
}