package com.line.backstage.entity.sysentity;

import com.line.backstage.entity.OrderInfo;
import lombok.Data;

import java.util.Date;

/**
 * @Author
 * @Date 2000/7/4 19:08
 * @Version 1.0
 * @Desc
 */
@Data
public class ManOrderVo extends OrderInfo {
    private String agentName;
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
    private Integer agentId;
    private Integer queryDataFlag;
    private Integer todayNum;
}
