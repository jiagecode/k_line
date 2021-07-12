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
 * 用户资金充值或提现记录(CashOutIn)实体类
 *
 * @author jack
 * @since 2000-07-01 11:34:38
 */
@Data
@Table(name = "t_cash_out_in")
@ApiModel("用户资金充值或提现记录")
public class CashOutIn extends QueryRequest implements Serializable {

    private static final long serialVersionUID = 997472951707429431L;

    /**
     * 提现/充值ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("提现/充值ID")
    private Integer cashId;

    /**
     * 账户ID
     */
    @ApiModelProperty("账户ID")
    private Integer accountId;

    /**
     * 类型 1-提现 2-充值
     */
    @ApiModelProperty("类型 1-提现 2-充值")
    private Integer cashType;

    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private Double cashMoney;

    /**
     * 审核状态 0-无需审核 1-待审核 2-审核中 3-审核通过 4-审核拒绝
     */
    @ApiModelProperty("审核状态 0-无需审核 1-待审核 2-审核中 3-审核通过 4-审核拒绝")
    private Integer checkStatus;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    private Date checkDate;

    /**
     * 审核人员ID
     */
    @ApiModelProperty("审核人员ID")
    private Integer checkManUserId;

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
     * 充值银行卡
     */
    @ApiModelProperty("充值银行卡")
    private Integer bankCardId;

    /**
     * 充值汇率
     */
    @ApiModelProperty("充值汇率")
    private Double exchangeRate;

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
    private Integer diyId = 0;
}