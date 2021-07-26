package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.entity.sysentity.ManOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单信息(OrderInfo)表数据库访问层
 *
 * @author jack
 * @since 2000-06-29 14:53:17
 */
public interface OrderInfoMapper extends TkBaseMapper<OrderInfo> {
    /**
     * 根据结算时间查询待结算订单
     * @param settlementDate
     * @return
     */
    List<OrderInfo> queryAllNeedSettlementOrders(@Param("settlementDate") Date settlementDate);
    List<ManOrderVo> queryManOrderVoForPage(ManOrderVo manOrderVo);
    OrderInfo queryOneById(@Param("orderId")Integer orderId);
    Integer countTodayOrderNum(Map map);
    Integer notEndOrderNum(@Param("userId")Integer loginUserId);
    Integer updateForDiy(@Param("diyId") Integer diyId, @Param("userId") Integer userId, @Param("del") Integer del, @Param("sysUserId")Integer sysUserId);
}