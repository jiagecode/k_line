package com.line.backstage.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author
 * @Date 2021/7/7 13:02
 * @Version 1.0
 * @Desc
 */
@Data
@Table(name = "t_sys_diy_info")
@ApiModel("辅助操作表")
public class SysDiyInfo implements Serializable {
    private static final long serialVersionUID = 675334900033461866L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("用户ID")
    private Integer diyId;
    /**
     *用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;
    /**
     *操作员id
     */
    @ApiModelProperty("操作员id")
    private Integer sysUserId;
    /**
     *改前姓名
     */
    @ApiModelProperty("改前姓名")
    private String beforeName;
    /**
     *改后姓名
     */
    @ApiModelProperty("改后姓名")
    private String afterName;
    /**
     *改前金额
     */
    @ApiModelProperty("改前金额")
    private BigDecimal beforeMoney;
    /**
     *改后金额
     */
    @ApiModelProperty("改后金额")
    private BigDecimal afterMoney;
    /**
     *修改前的用户类型
     */
    @ApiModelProperty("修改前的用户类型")
    private Integer beforeUserType;
    /**
     *修改后的用户类型
     */
    @ApiModelProperty("修改后的用户类型")
    private Integer afterUserType;
    /**
     *模拟订单状态 0-未生成 1-未入库 2-已入库 3-已删除
     */
    @ApiModelProperty("模拟订单状态")
    private Integer diyOrderStatus;
    /**
     *模拟提现状态 0-未生成 1-已生成 2-已删除
     */
    @ApiModelProperty("模拟提现状态")
    private Integer diyCashStatus;
    /**
     *模拟资金记录状态 0-未生成 1-已生成 2-已删除
     */
    @ApiModelProperty("模拟资金记录状态")
    private Integer diyRecordStatus;
    private Date addDate;
    private Integer addUserId;
    private Date editDate;
    private Integer editUserId;
    private Integer  del;
    private Integer  diyStatus;

}
