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
 
/**
 * 角色菜单表  (SysPower)实体类
 *
 * @author Zy
 * @since 2021-07-03 11:21:56
 */
@Data
@Table(name = "t_sys_power")
@ApiModel("角色菜单表  ")
public class SysPower extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = 675334900933461866L;
    
             
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("$column.comment")
    private Integer rmId;
    
    /**
    * 角色ID
    */            
    @ApiModelProperty("角色ID")
    private Integer roleId;
    
    /**
    * 菜单ID
    */            
    @ApiModelProperty("菜单ID")
    private Integer menuId;
    
}