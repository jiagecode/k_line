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
 * 后台管理系统提现审核记录表(SysCashCheck)实体类
 *
 * @author jack
 * @since 2000-07-01 11:35:16
 */
@Data
@Table(name = "t_sys_cash_check")
@ApiModel("后台管理系统提现审核记录表")
public class SysCashCheck extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -31556055901181470L;
    
    /**
    * 编号
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("编号")
    private Integer id;
    
    /**
    * 提现编号
    */            
    @ApiModelProperty("提现编号")
    private Integer cashId;
    
    /**
    * 审核结果 1-通过 2-拒绝
    */            
    @ApiModelProperty("审核结果 1-通过 2-拒绝")
    private Object cashResult;
    
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