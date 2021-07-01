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
 * 后台管理系统用户表(SysUserInfo)实体类
 *
 * @author Zy
 * @since 2021-07-01 11:36:19
 */
@Data
@Table(name = "t_sys_user_info")
@ApiModel("后台管理系统用户表")
public class SysUserInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -34593848385249339L;
    
    /**
    * 后台用户id
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("后台用户id")
    private Integer sysUserId;
    
    /**
    * 登录账号
    */            
    @ApiModelProperty("登录账号")
    private String sysUserCode;
    
    /**
    * 登录密码
    */            
    @ApiModelProperty("登录密码")
    private String sysUserPassword;
    
    /**
    * 用户名
    */            
    @ApiModelProperty("用户名")
    private String sysUserName;
    
    /**
    * 电话
    */            
    @ApiModelProperty("电话")
    private String sysUserPhone;
    
    /**
    * 角色编号
    */            
    @ApiModelProperty("角色编号")
    private Integer sysRoleId;
    
    /**
    * 头像
    */            
    @ApiModelProperty("头像")
    private String sysUserImg;
    
    /**
    * 创建时间
    */            
    @ApiModelProperty("创建时间")
    private Date addDate;
    
    /**
    * 修改时间
    */            
    @ApiModelProperty("修改时间")
    private Date editDate;
    
    /**
    * 创建人
    */            
    @ApiModelProperty("创建人")
    private Integer addUserId;
    
    /**
    * 修改人
    */            
    @ApiModelProperty("修改人")
    private Integer editUserId;
    
    /**
    * 数据状态
    */            
    @ApiModelProperty("数据状态")
    private Object del;
    
}