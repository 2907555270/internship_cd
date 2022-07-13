package com.txy.graduate.domain.po;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
* 流程配置表
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_process")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Process {
    /**
    * 主键id
    */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
    * 标题
    */
    @TableField("title")
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
    * 内容
    */
    @TableField("content")
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
    * 办理地址
    */
    @TableField("address")
    @NotBlank(message = "办理地址不能为空")
    private String address;

    /**
    * 状态
    */
    @TableField("status")
    private Byte status;

    /**
    * 配置类型
    */
    @TableField("type")
    @NotNull(message = "配置类型不能为空")
    private Byte type;

    /**
    * 开始时间
    */
    @TableField("start_time")
    //@FutureOrPresent(message = "开始时间需要在将来或未来")
    private Date startTime;

    /**
    * 结束时间
    */
    @TableField("end_time")
    //@Future(message = "结束时间需要在未来")
    private Date endTime;

    /**
    * 联系电话
    */
    @TableField("phone")
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$",message = "电话号码格式不正确")
    private String phone;

    /**
    * 备注
    */
    @TableField("note")
    private String note;

    /**
     * 地点图片
     */
    @TableField("img")
    private String img;

    /**
     * 地点图片根路径
     */
    @TableField("base_img_path")
    private String baseImgPath;

    /**
     * 用与接受和发送images完整路径
     */
    @TableField(exist = false)
    private List<String> imgPaths;
}