package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
 
/**
 * 后台管理系统用户表(SysUserInfo)实体类
 *
 * @author jack
 * @since 2000-07-02 10:16:06
 */
@Data
@Table(name = "t_sys_user_info")
@ApiModel("后台管理系统用户表")
public class SysUserInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -44175677178704398L;
    
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
     * 新密码
     */
    @Transient
    private String sysUserPassword2;
    @Transient
    private String roleName;

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
    private Integer del;
    
}