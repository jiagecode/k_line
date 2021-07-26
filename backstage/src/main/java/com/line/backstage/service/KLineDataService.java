package com.line.backstage.service;

import com.line.backstage.vo.SkuInfoOhlcvVo;

import java.util.List;

public interface KLineDataService {

    /**
     * 查询日线数据
     *
     * @return 对象列表
     */
    List<Object> dayList(Integer loginUserId, String code);

    /**
     * 查询小时数据
     *
     * @return 对象列表
     */
    List<Object> hourList(Integer loginUserId, String code);

    /**
     * 查询分钟线数据
     *
     * @return 对象列表
     */
    List<Object> minList(Integer loginUserId, String code);

    /**
     * 查询历史秒线数据
     *
     * @return 对象列表
     */
    List<Object> ssList(Integer loginUserId, String code);
}
