package com.line.backstage.entity.sysentity;

import com.line.backstage.entity.CashOutIn;
import lombok.Data;

import java.util.Date;

/**
 * @Author
 * @Date 2000/7/4 9:22
 * @Version 1.0
 * @Desc 后台管理系统-提现充值对象
 */
@Data
public class ManCashVo extends CashOutIn {
    /**
     * 交易姓名
     */
    private String userRealName;
    /**
     * 用户账户余额
     */
    private Double userMoney;
    /**
     * 查询开始时间
     */
    private Date beginDate;
    /**
     * 查询结束时间
     */
    private Date endDate;
}
