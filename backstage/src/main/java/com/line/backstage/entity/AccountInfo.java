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
 * 用户账户(AccountInfo)实体类
 *
 * @author Zy
 * @since 2021-07-01 11:34:17
 */
@Data
@Table(name = "t_account_info")
@ApiModel("用户账户")
public class AccountInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -44267745190089681L;
    
    /**
    * 账户ID
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("账户ID")
    private Integer accountId;
    
    /**
    * 用户ID
    */            
    @ApiModelProperty("用户ID")
    private Integer userId;
    
    /**
    * 账户资金
    */            
    @ApiModelProperty("账户资金")
    private Double accountMoney;
    
    /**
    * 账户状态 0-正常 1-禁止提现 
    */            
    @ApiModelProperty("账户状态 0-正常 1-禁止提现 ")
    private Object accountStatus;
    
    /**
    * 创建日期
    */            
    @ApiModelProperty("创建日期")
    private Date addDate;
    
    /**
    * 修改日期
    */            
    @ApiModelProperty("修改日期")
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
    private Object del;
    
}