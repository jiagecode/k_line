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
 * 后台管理系统角色与菜单对应关系(SysPower)实体类
 *
 * @author Zy
 * @since 2021-07-01 11:35:45
 */
@Data
@Table(name = "t_sys_power")
@ApiModel("后台管理系统角色与菜单对应关系")
public class SysPower extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = 971714523432820595L;
    
    /**
    * 编号
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("编号")
    private Integer id;
    
    /**
    * 角色id
    */            
    @ApiModelProperty("角色id")
    private Integer roleId;
    
    /**
    * 菜单id
    */            
    @ApiModelProperty("菜单id")
    private Integer menuId;
    
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
    private Integer del;
    
}