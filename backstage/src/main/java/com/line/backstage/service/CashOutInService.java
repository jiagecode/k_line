package com.line.backstage.service;
 
import com.line.backstage.entity.CashOutIn;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 用户资金充值或提现记录(CashOutIn)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:34:38
 */
public interface CashOutInService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, CashOutIn cashOutIn);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, CashOutIn cashOutIn);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param cashId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer cashId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, CashOutIn cashOutIn);
 
    /**
     * 通过ID查询单条数据
     *
     * @param cashId 主键
     * @return 实例对象
     */
    CashOutIn queryById(Integer cashId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn 查询条数
     * @return 对象列表
     */
    PageWrapper<CashOutIn> list(Integer loginUserId, CashOutIn cashOutIn);
}