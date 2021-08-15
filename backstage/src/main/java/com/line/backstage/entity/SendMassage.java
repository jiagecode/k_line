package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.*;
import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 聊天记录(SendMassage)实体类
 * @author fimex
 */
@Data
@Table(name = "t_send_massage")
@ApiModel("聊天记录")
public class SendMassage extends QueryRequest implements Serializable {

    private static final long serialVersionUID = -46805760240445027L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("$column.comment")
    private Integer id;

    /**
    * 发送
    */
    @ApiModelProperty("发送")
    private Integer send;

    /**
    * 接收
    */
    @ApiModelProperty("接收")
    private Integer receive;

    /**
    * 内容
    */
    @ApiModelProperty("内容")
    private String content;

    /**
    * 添加人
    */
    @ApiModelProperty("添加人")
    private Integer addUserId;

    /**
    * 添加时间
    */
    @ApiModelProperty("添加时间")
    private Date addTime;

    /**
    * 修改人
    */
    @ApiModelProperty("修改人")
    private Integer editUserId;

    /**
    * 修改时间
    */
    @ApiModelProperty("修改时间")
    private Date editTime;

    /**
    * 数据状态1 有效 0无效
    */
    @ApiModelProperty("数据状态1 有效 0无效")
    private Integer del;

}
