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
 * 银行卡信息(BankCardInfo)实体类
 *
 * @author Zy
 * @since 2021-06-24 10:49:55
 */
@Data
@Table(name = "t_bank_card_info")
@ApiModel("银行卡信息")
public class BankCardInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -88106156845521615L;
    
             
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("$column.comment")
    private Integer bankCardId;
    
    /**
    * 用户ID
    */            
    @ApiModelProperty("用户ID")
    private Integer userId;
    
    /**
    * 银行名称
    */            
    @ApiModelProperty("银行名称")
    private String bankName;
    
    /**
    * 省份
    */            
    @ApiModelProperty("省份")
    private String province;
    
    /**
    * 城市名
    */            
    @ApiModelProperty("城市名")
    private String city;
    
    /**
    * 支行
    */            
    @ApiModelProperty("支行")
    private String subBranch;
    
    /**
    * 开户名
    */            
    @ApiModelProperty("开户名")
    private String cardOwnerName;
    
    /**
    * 卡号
    */            
    @ApiModelProperty("卡号")
    private String cardNo;
    
    /**
    * 身份证号
    */            
    @ApiModelProperty("身份证号")
    private String cardOwnerNo;
    
    /**
    * 预留手机号
    */            
    @ApiModelProperty("预留手机号")
    private String cardPhone;
    
    /**
    * 卡状态 0-可用 1-禁用
    */            
    @ApiModelProperty("卡状态 0-可用 1-禁用")
    private Integer cardStatus;
    
    /**
    * 创建时间
    */            
    @ApiModelProperty("创建时间")
    private Date addDate;
    
    /**
    * 修改时间
    */            
    @ApiModelProperty("修改时间")
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
    private Integer del;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remarks;
}