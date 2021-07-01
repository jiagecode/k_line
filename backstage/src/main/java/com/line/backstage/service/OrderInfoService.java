package com.line.backstage.service;
 
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 订单信息(OrderInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-06-29 14:53:18
 */
public interface OrderInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, OrderInfo orderInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, OrderInfo orderInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param orderId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer orderId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, OrderInfo orderInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    OrderInfo queryById(Integer orderId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<OrderInfo> list(Integer loginUserId, OrderInfo orderInfo);
}