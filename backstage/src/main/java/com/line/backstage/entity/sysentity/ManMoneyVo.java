package com.line.backstage.entity.sysentity;

import lombok.Data;

import java.util.Date;

/**
 * @Author DWanBean
 * @Date 2021/7/5 14:20
 * @Version 1.0
 * @Desc 资金报表汇总数据
 */
@Data
public class ManMoneyVo {
    /**
     *总盈亏金额
     */
    private Double allMoney;
    /**
     *入金总额
     */
    private Double allInMoney;
    /**
     *出金总额
     */
    private Double allOutMoney;
    /**
     * 当日盈亏金额
     */
    private Double todayMoney;
    /**
     *佣金
     */
    private Double allCommission;
    /**
     *红利
     */
    private Double allBonus;

    /**
     * 用户名
     */
    private String userRealName;
    /**
     * 查询开始时间
     */
    private Date beginDate;
    /**
     * 查询结束时间
     */
    private Date endDate;
    /**
     * 今日日期int值
     */
    private Integer todayNum;
    private Integer del;
}
