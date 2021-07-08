package com.line.backstage.service;

import java.util.List;
import java.util.Map;

/**
 * @Author DWanBean
 * @Date 2021/7/7 9:44
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
}