package com.line.backstage.entity.sysentity;

import com.line.backstage.entity.OrderInfo;
import lombok.Data;

import java.util.Date;

/**
 * @Author DWanBean
 * @Date 2021/7/4 19:08
 * @Version 1.0
 * @Desc
 */
@Data
public class ManOrderVo extends OrderInfo {
    /**
     * 交易姓名
     */
    private String userRealName;

    private String agentName;
    /**
     * 用户账户余额
     */
    private Double userMoney;
    private Double todayMoney;
    private Double allMoney;
    private Double afterMoney;

    /**
     * 查询开始时间
     */
    private Date beginDate;
    /**
     * 查询结束时间
     */
    private Date endDate;

    private Integer accountId;
    private Integer userType;
    private Integer queryDataFlag;
    private Integer todayNum;
}
