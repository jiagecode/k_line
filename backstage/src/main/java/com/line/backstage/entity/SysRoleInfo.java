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
 * 角色表 ;(SysRoleInfo)实体类
 *
 * @author jack
 * @since 2000-07-03 10:18:23
 */
@Data
@Table(name = "t_sys_role_info")
@ApiModel("角色表 ;")
public class SysRoleInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -27122625382717408L;
    
             
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("$column.comment")
    private Integer roleId;
    
    /**
    * 角色名称
    */            
    @ApiModelProperty("角色名称")
    private String roleName;
    
    /**
    * 英文标识
    */            
    @ApiModelProperty("英文标识")
    private String roleEnglishName;
    
    /**
    * 对应用户的子账户组
    */            
    @ApiModelProperty("对应用户的子账户组")
    private Integer roleType;
    
    /**
    * 1-启用，2-禁用
    */            
    @ApiModelProperty("1-启用，2-禁用")
    private Integer locked;
    
    /**
    * 备注
    */            
    @ApiModelProperty("备注")
    private String universal;
    
                
    @ApiModelProperty("$column.comment")
    private Integer addUserId;
    
                
    @ApiModelProperty("$column.comment")
    private Date addTime;
    
                
    @ApiModelProperty("$column.comment")
    private Integer editUserId;
    
                
    @ApiModelProperty("$column.comment")
    private Date editTime;
    
    /**
    * 数据状态1 有效 0无效
    */            
    @ApiModelProperty("数据状态1 有效 0无效")
    private Integer del;
    
}