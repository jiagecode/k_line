package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单信息(OrderInfo)实体类
 *
 * @author Zy
 * @since 2021-06-29 14:53:16
 */
@Data
@Table(name = "t_order_info")
@ApiModel("订单信息")
public class OrderInfo extends QueryRequest implements Serializable {

    private static final long serialVersionUID = 793262494587395679L;

    /**
     * 订单ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("订单ID")
    private Integer orderId;

    @ApiModelProperty("持仓Id")
    private Integer positionId;

    /**
     * 订单类型 1-买入 2-卖出
     */
    @ApiModelProperty("订单类型 1-买入 2-卖出")
    private Integer orderType;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Integer userId;

    /**
     * 订单状态 0-预生成 1-已成交 2-已完成
     */
    @ApiModelProperty("订单状态 0-预生成 1-已成交 2-已完成")
    private Integer orderStatus;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private Integer skuId;

    /**
     * 商品代码
     */
    @ApiModelProperty("商品代码")
    private String skuCode;

    /**
     * 商品数量
     */
    @ApiModelProperty("商品数量")
    private Double skuQty;

    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private Double skuPrice;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    private Double orderAmount;

    /**
     * 订单手续费
     */
    @ApiModelProperty("订单手续费")
    private Double orderCharge;

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
     * 订单周期(单位：秒)
     */
    @ApiModelProperty("订单周期(单位：秒)")
    private Integer orderCycle;

    /**
     * 投资金额
     */
    @ApiModelProperty("投资金额")
    private Double investAmount;

    /**
     * 投资方向 1-买涨 2-买亏
     */
    @ApiModelProperty("投资方向 1-买涨 2-买亏")
    private Integer investType;

    /**
     * 预期收益
     */
    @ApiModelProperty("预期收益")
    private Double expectedReturn;

    /**
     * 保底金额
     */
    @ApiModelProperty("保底金额")
    private Double guaranteedAmount;

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
    private Integer inPoint;
    private Integer outPoint;
    private Integer integral;
    private Double subMoney;
    private Double resultMoney;
    private String skuName;
}