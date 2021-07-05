package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.entity.sysentity.ManOrderVo;

import java.util.List;
import java.util.Map;

/**
 * 订单信息(OrderInfo)表数据库访问层
 *
 * @author Zy
 * @since 2021-06-29 14:53:17
 */
public interface OrderInfoMapper extends TkBaseMapper<OrderInfo> {

    List<ManOrderVo> queryManOrderVoForPage(ManOrderVo manOrderVo);

    Integer countTodayOrderNum(Map map);
}