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
 * 后台管理系统菜单表(SysMenuInfo)实体类
 *
 * @author Zy
 * @since 2021-07-01 11:35:35
 */
@Data
@Table(name = "t_sys_menu_info")
@ApiModel("后台管理系统菜单表")
public class SysMenuInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -95619408042321594L;
    
    /**
    * 菜单id
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("菜单id")
    private Integer menuId;
    
    /**
    * 菜单名
    */            
    @ApiModelProperty("菜单名")
    private String menuName;
    
    /**
    * 描述
    */            
    @ApiModelProperty("描述")
    private String menuDesc;
    
    /**
    * 路径
    */            
    @ApiModelProperty("路径")
    private String menuUrl;
    
    /**
    * 菜单类型
    */            
    @ApiModelProperty("菜单类型")
    private Object menuType;
    
    /**
    * 菜单状态
    */            
    @ApiModelProperty("菜单状态")
    private Object menuStatus;
    
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
    * 上级id
    */            
    @ApiModelProperty("上级id")
    private Integer superId;
    
    /**
    * 菜单结构 ‘-’分割
    */            
    @ApiModelProperty("菜单结构 ‘-’分割")
    private String menuIndex;
    
    /**
    * 菜单等级
    */            
    @ApiModelProperty("菜单等级")
    private Object menuLevel;
    
    /**
    * 数据状态
    */            
    @ApiModelProperty("数据状态")
    private Object del;
    
}