package com.line.backstage.entity.sysentity;

import com.line.backstage.entity.UserInfo;
import lombok.Data;

import java.util.Date;

/**
 * @Author DWanBean
 * @Date 2021/7/3 16:09
 * @Version 1.0
 * @Desc 后台管理系统-用户实体对象
 */
@Data
public class ManUserVo extends UserInfo {
    /**
     * 所属代理id
     */
    private Integer agentId;
    /**
     *所属代理名称
     */
    private String agentName;
    /**
     * 成为代理时间
     */
    private Date agentTime;
    /**
     *红利
     */
    private Double bonusRate;
    /**
     * 佣金
     */
    private Double commissionRate;
    /**
     * 订单数
     */
    private Integer orderNum;

    /**
     * 查询时间使用标志 1-查询今日用户 2-查询今日代理商
     */
    private Integer queryDateFlag;
    /**
     * 查询开始时间
     */
    private Date beginDate;
    /**
     * 查询结束时间
     */
    private Date endDate;
}
