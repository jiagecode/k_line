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
 * 后台管理系统充值路径配置表(SysRechageConfig)实体类
 *
 * @author jack
 * @since 2000-07-01 11:35:55
 */
@Data
@Table(name = "t_sys_rechage_config")
@ApiModel("后台管理系统充值路径配置表")
public class SysRechageConfig extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -43429821848051468L;
    
             
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("$column.comment")
    private Integer id;
    
    /**
    * 充值地址
    */            
    @ApiModelProperty("充值地址")
    private String rechageUrl;
    
    /**
    * 充值描述
    */            
    @ApiModelProperty("充值描述")
    private String rechageDesc;
    
    /**
    * 启用状态 1-可用 0-停用
    */            
    @ApiModelProperty("启用状态 1-可用 0-停用")
    private Object rechageStatus;
    
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
    * 修改人
    */            
    @ApiModelProperty("修改人")
    private Date editDate;
    
    /**
    * 修改时间
    */            
    @ApiModelProperty("修改时间")
    private Integer editUserId;
    
    /**
    * 数据状态
    */            
    @ApiModelProperty("数据状态")
    private Object del;
    
}