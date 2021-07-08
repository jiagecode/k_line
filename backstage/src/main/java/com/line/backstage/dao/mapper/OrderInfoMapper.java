package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.entity.sysentity.ManOrderVo;
import org.apache.ibatis.annotations.Param;

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
    Integer updateForDiy(@Param("diyId") Integer diyId, @Param("userId") Integer userId, @Param("del") Integer del, @Param("sysUserId")Integer sysUserId);
}