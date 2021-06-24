package com.line.backstage.entity;

import java.util.Date;
import java.io.Serializable;

import com.line.backstage.bases.QueryRequest;
import io.swagger.annotations.*;
import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
 
/**
 * 日志表(UserLog)实体类
 *
 * @author Zy
 * @since 2021-06-23 22:08:31
 */
@Data
@Table(name = "t_user_log")
@ApiModel("日志表")
public class UserLog extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = 981955826030840720L;
    
    /**
    * 日志ID
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("日志ID")
    private Integer id;
    
    /**
    * 用户ID
    */            
    @ApiModelProperty("用户ID")
    private Integer userId;
    
    /**
    * 操作描述
    */            
    @ApiModelProperty("操作描述")
    private String description;
    
    /**
    * 请求IP
    */            
    @ApiModelProperty("请求IP")
    private String ip;
    
    /**
    * 请求地址
    */            
    @ApiModelProperty("请求地址")
    private String requestUrl;
    
    /**
    * 模块名称
    */            
    @ApiModelProperty("模块名称")
    private String modelName;
    
    /**
    * 操作名称
    */            
    @ApiModelProperty("操作名称")
    private String actionName;
    
    /**
    * 原始请求参数
    */            
    @ApiModelProperty("原始请求参数")
    private String originalParams;
    
    /**
    * 请求参数
    */            
    @ApiModelProperty("请求参数")
    private String params;
    
    /**
    * 执行时长-毫秒
    */            
    @ApiModelProperty("执行时长-毫秒")
    private Double executeTime;
    
    /**
    * 请求结果
    */            
    @ApiModelProperty("请求结果")
    private String responseResult;
    
    /**
    * 1-成功/0-失败
    */            
    @ApiModelProperty("1-成功/0-失败")
    private Object executeStatus;
    
    /**
    * 创建时间
    */            
    @ApiModelProperty("创建时间")
    private Date createTime;
    
}