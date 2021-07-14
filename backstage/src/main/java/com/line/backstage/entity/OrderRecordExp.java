package com.line.backstage.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author DWanBean
 * @Date 2021/7/14 18:06
 * @Version 1.0
 * @Desc
 */
@Data
public class OrderRecordExp {
    private Integer expId;
    private Integer orderId;
    private Integer userId;
    private Integer todayNum;
    private Double expMoney;
    private Date addDate;
    private Date editDate;
    private Integer del;
}
