package com.line.backstage.service;

/**
 * @Author
 * @Date 2021/7/14 16:28
 * @Version 1.0
 * @Desc 订单结算
 */
public interface TaskOrderSettlementService {
    /**
     * 处理订单结算
     */
    void dealOrderSettlement();

    /**
     * 处理单个订单结算
     * @param orderIdStr
     */
    void dealSettlementByOrderId(String orderIdStr);
}
