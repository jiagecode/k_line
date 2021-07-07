package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.*;
import lombok.Data;

import javax.persistence.*;

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
    private Integer accountStatus;
    
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
    private Integer del;
    /**
     * 用户订单数
     */
    @ApiModelProperty("用户订单数")
    private Integer orderNum;
    /**
     *总盈亏金额
     */
    @ApiModelProperty("总盈亏金额")
    private Double allMoney;
    /**
     *入金总额
     */
    @ApiModelProperty("入金总额")
    private Double allInMoney;
    /**
     *入金次数
     */
    @ApiModelProperty("入金次数")
    private Integer allInNum;
    /**
     *手动入金
     */
    @ApiModelProperty("手动入金")
    private Double handInMoney;
    /**
     *出金总额
     */
    @ApiModelProperty("出金总额")
    private Double allOutMoney;
    /**
     *出金次数
     */
    @ApiModelProperty("出金次数")
    private Integer allOutNum;
    /**
     *佣金
     */
    @ApiModelProperty("佣金")
    private Double allCommission;
    /**
     *红利
     */
    @ApiModelProperty("红利")
    private Double allBonus;
    /**
     *实际余额
     */
    @ApiModelProperty("实际余额")
    private Double reallyMoney;
    /**
     *净入金
     */
    @ApiModelProperty("净入金")
    private Double reallyInMoney;
    /**
     *手续费
     */
    @ApiModelProperty("手续费")
    private Double allFee;
    @ApiModelProperty("备注")
    private String remarks;
    @Transient
    private String agentName;
    @Transient
    private String userRealName;
    /**
     * 查询开始时间
     */
    @Transient
    private Date beginDate;
    /**
     * 查询结束时间
     */
    @Transient
    private Date endDate;
    /**
     * 当日盈亏金额
     */
    @Transient
    private Double todayMoney;
    /**
     * 出金审核中
     */
    @Transient
    private Double allCheckMoney;
    /**
     * 今日日期int值
     */
    @Transient
    private Integer todayNum;
    /**
     * 自定义金额
     */
    private BigDecimal diyMoney = BigDecimal.ZERO;
}