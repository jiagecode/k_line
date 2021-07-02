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
 * 系统菜单(SysMenuInfo)实体类
 *
 * @author Zy
 * @since 2021-07-02 19:24:07
 */
@Data
@Table(name = "t_sys_menu_info")
@ApiModel("系统菜单")
public class SysMenuInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = 785518365554004063L;

    public static final Long ROOT = 0L;
             
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("$column.comment")
    private Integer id;
    
    /**
    * 菜单编号
    */            
    @ApiModelProperty("菜单编号")
    private String menuId;
    
    /**
    * 父级编号
    */            
    @ApiModelProperty("父级编号")
    private Integer parentId;
    
                
    @ApiModelProperty("$column.comment")
    private String parentIds;
    
    /**
    * 排序号
    */            
    @ApiModelProperty("排序号")
    private Integer treeSort;
    
    /**
    * 参考文献
    */            
    @ApiModelProperty("参考文献")
    private String treeSorts;
    
    /**
    * 是否是叶子节点
    */            
    @ApiModelProperty("是否是叶子节点")
    private String treeLeaf;
    
    /**
    * （从1开始，快速分级查询，根据层级缩进）
    */            
    @ApiModelProperty("（从1开始，快速分级查询，根据层级缩进）")
    private Integer treeLevel;
    
                
    @ApiModelProperty("$column.comment")
    private String treeNames;
    
    /**
    * 菜单名称
    */            
    @ApiModelProperty("菜单名称")
    private String menuName;
    
    /**
    * 菜单类型
    */            
    @ApiModelProperty("菜单类型")
    private String menuType;
    
    /**
    * 路径
    */            
    @ApiModelProperty("路径")
    private String menuHref;
    
    /**
    * 对应前端Vue组件名称
    */            
    @ApiModelProperty("对应前端Vue组件名称")
    private String menuComponent;
    
                
    @ApiModelProperty("$column.comment")
    private String menuTarget;
    
    /**
    * 图标
    */            
    @ApiModelProperty("图标")
    private String menuIcon;
    
    /**
    * 1-显示，2-隐藏
    */            
    @ApiModelProperty("1-显示，2-隐藏")
    private String showType;
    
    /**
    * 权限字符串
    */            
    @ApiModelProperty("权限字符串")
    private String permission;
    
                
    @ApiModelProperty("$column.comment")
    private String remark;
    
    /**
    * 数据状态
    */            
    @ApiModelProperty("数据状态")
    private int del;
    
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
    * 新增日期
    */            
    @ApiModelProperty("新增日期")
    private Date addDate;
    
    /**
    * 修改日期
    */            
    @ApiModelProperty("修改日期")
    private Date editDate;
    
}