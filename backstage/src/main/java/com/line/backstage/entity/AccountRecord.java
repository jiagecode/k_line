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
 * 用户资金账户变动记录(AccountRecord)实体类
 *
 * @author jack
 * @since 2000-06-29 14:46:07
 */
@Data
@Table(name = "t_account_record")
@ApiModel("用户资金账户变动记录")
public class AccountRecord extends QueryRequest implements Serializable {

    private static final long serialVersionUID = 362099619904608448L;

    /**
     * 账户变动记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("账户变动记录ID")
    private Integer recordId;

    /**
     * 账户ID
     */
    @ApiModelProperty("账户ID")
    private Integer accountId;

    /**
     * 变动类型 1-充值 2-提现 3-买入 4-卖出
     */
    @ApiModelProperty("变动类型 1-充值 2-提现 3-买入 4-卖出")
    private Integer recordType;

    /**
     * 变动金额
     */
    @ApiModelProperty("变动金额")
    private Double changeMoney;

    /**
     * 变动前金额
     */
    @ApiModelProperty("变动前金额")
    private Double beforeMoney;

    /**
     * 变动后金额
     */
    @ApiModelProperty("变动后金额")
    private Double afterMoney;

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
     * 关联订单
     */
    @ApiModelProperty("关联订单")
    private Integer orderId;
    /**
     * 提现/充值id
     */
    @ApiModelProperty("提现/充值id")
    private Integer cashId;

    /**
     * 手续费
     */
    @ApiModelProperty("手续费")
    private Double serviceCharge;
    private Double commissionMoney;

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
    private String remarks;
    private Integer diyId ;
}