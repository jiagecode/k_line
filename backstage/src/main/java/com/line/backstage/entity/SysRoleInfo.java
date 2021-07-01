package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
 
/**
 * 后台管理系统角色表(SysRoleInfo)实体类
 *
 * @author Zy
 * @since 2021-07-01 11:36:06
 */
@Data
@Table(name = "t_sys_role_info")
@ApiModel("后台管理系统角色表")
public class SysRoleInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = 299405415291588540L;
    
    /**
    * 角色id
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("角色id")
    private Integer roleId;
    
    /**
    * 角色名称
    */            
    @ApiModelProperty("角色名称")
    private String roleName;
    
    /**
    * 角色等级
    */            
    @ApiModelProperty("角色等级")
    private Object roleLevel;
    
    /**
    * 创建时间
    */            
    @ApiModelProperty("创建时间")
    private Date addDate;
    
    /**
    * 创建人
    */            
    @ApiModelProperty("创建人")
    private Integer addUserId;
    
    /**
    * 修改时间
    */            
    @ApiModelProperty("修改时间")
    private Date editDate;
    
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