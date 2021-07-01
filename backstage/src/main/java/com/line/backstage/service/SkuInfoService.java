package com.line.backstage.service;
 
import com.line.backstage.entity.SkuInfo;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 商品信息表(SkuInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:35:00
 */
public interface SkuInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SkuInfo skuInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SkuInfo skuInfo);
 
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
     * @param skuInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SkuInfo skuInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param skuId 主键
     * @return 实例对象
     */
    SkuInfo queryById(Integer skuId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<SkuInfo> list(Integer loginUserId, SkuInfo skuInfo);
}