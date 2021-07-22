package com.line.backstage.service;
 
import com.line.backstage.entity.SkuCusInfo;
import com.line.backstage.utils.PageWrapper;

import java.util.List;

/**
 * 自选商品信息表(SkuCusInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-02 18:09:07
 */
public interface SkuCusInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SkuCusInfo skuCusInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SkuCusInfo skuCusInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param skuId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer skuId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SkuCusInfo skuCusInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param skuId 主键
     * @return 实例对象
     */
    SkuCusInfo queryById(Integer skuId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<SkuCusInfo> list(Integer loginUserId, SkuCusInfo skuCusInfo);

    /**
     * 查询自选商品的code
     * @param loginUserId
     * @return
     */
    String queryMyCusCode(Integer loginUserId);
}