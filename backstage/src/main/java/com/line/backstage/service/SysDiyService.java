package com.line.backstage.service;

import com.line.backstage.entity.CashOutIn;
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.entity.sysentity.ManOptionVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date 2000/7/7 9:44
 * @Version 1.0
 * @Desc
 */
public interface SysDiyService {
    /**
     * 查询操作用户信息
     * @param loginUserId
     * @param diyUserId
     * @return
     */
    Map<String,Object> querySpecialUserInfo(Integer loginUserId,String diyUserId);

    /**
     * 修改操作用户信息
     * @param map
     * @param loginUserId
     * @return
     */
    Integer updateSpecialUserInfo(Integer loginUserId,Map<String,Object> map);

    /**
     * 查询SKU下拉列表
     * @param loginUserId
     * @return
     */
    List<Map<String,Object>> querySkuDataList(Integer loginUserId);

    /**
     * 生成模拟数据并返回
     * @param loginUserId
     * @param map
     * @return
     */
    Map<String,Object> createImitateData(Integer loginUserId,Map<String,Object> map);

    /**
     * 编辑操作记录
     * @param loginUserId
     * @param map
     * @return
     */
    Integer editDiyRecordByType(Integer loginUserId,Map<String,Object> map);

    /**
     * 修改用户资金
     * @param loginUserId
     * @param map
     * @return
     */
    Integer editUserMoney(Integer loginUserId,Map<String,Object> map);

    /**
     * 提现审核
     * @param loginUserId
     * @param cash
     * @return
     */
    Integer checkForCashOutIn(Integer loginUserId, CashOutIn cash);

    /**
     * 统计提现/充值金额
     * @param loginUserId
     * @param cashType
     * @return
     */
    Double queryAllCashMoney(Integer loginUserId,Integer cashType);

    /**
     * 查询代理商下拉框
     * @param loginUserId
     * @return
     */
    List<ManOptionVo> queryOptionsForAgent(Integer loginUserId);

    OrderInfo queryDetailForOrder(Integer loginUserId,Integer orderId);
}
