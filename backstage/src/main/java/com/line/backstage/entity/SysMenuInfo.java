package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
 
/**
 * 菜单表(SysMenuInfo)实体类
 *
 * @author jack
 * @since 2000-07-03 10:18:13
 */
@Data
@Table(name = "t_sys_menu_info")
@ApiModel("菜单表")
public class SysMenuInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = 224485757094210450L;

    @Transient
    public static final Integer ROOT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("$column.comment")
    private Integer menuId;
    
    /**
    * 节点上级ID
    */            
    @ApiModelProperty("节点上级ID")
    private Integer parentId;
    
    /**
    * 节点所有上级ID
    */            
    @ApiModelProperty("节点所有上级ID")
    private String parentIds;
    
    /**
    * 当前层级排序号
    */            
    @ApiModelProperty("当前层级排序号")
    private Integer treeSort;
    
    /**
    * 节点所有上级编码
    */            
    @ApiModelProperty("节点所有上级编码")
    private String treeSorts;
    
    /**
    * 是否是叶子节点（0否/1是
    */            
    @ApiModelProperty("是否是叶子节点（0否/1是")
    private String treeLeaf;
    
    /**
    * 节点层级
    */            
    @ApiModelProperty("节点层级")
    private Integer treeLevel;
    
    /**
    * 全节点全名称
    */            
    @ApiModelProperty("全节点全名称")
    private String treeNames;
    
    /**
    * 菜单名称
    */            
    @ApiModelProperty("菜单名称")
    private String menuName;
    
    /**
    * 菜单类型:1目录2菜单3按钮
    */            
    @ApiModelProperty("菜单类型:1目录2菜单3按钮")
    private Integer menuType;
    
    /**
    * vue菜单访问路径
    */            
    @ApiModelProperty("vue菜单访问路径")
    private String menuHref;
    
    /**
    * vue组件名称
    */            
    @ApiModelProperty("vue组件名称")
    private String menuComponent;
    
    /**
    * vue组件图标
    */            
    @ApiModelProperty("vue组件图标")
    private String menuIcon;
    
    /**
    * 1-显示菜单，2-隐藏菜单
    */            
    @ApiModelProperty("1-显示菜单，2-隐藏菜单")
    private Integer showType;
    
    /**
    * vue组件权限
    */            
    @ApiModelProperty("vue组件权限")
    private String permission;
    
    /**
    * 备注
    */            
    @ApiModelProperty("备注")
    private String remarks;
    
                
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