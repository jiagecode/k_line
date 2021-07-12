package com.line.backstage.service;
 
import com.line.backstage.entity.BankCardInfo;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 银行卡信息(BankCardInfo)表服务实现类
 *
 * @author jack
 * @since 2000-06-24 10:49:55
 */
public interface BankCardInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param bankCardInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, BankCardInfo bankCardInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param bankCardInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, BankCardInfo bankCardInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param bankCardId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer bankCardId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param bankCardInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, BankCardInfo bankCardInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param bankCardId 主键
     * @return 实例对象
     */
    BankCardInfo queryById(Integer bankCardId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param bankCardInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<BankCardInfo> list(Integer loginUserId, BankCardInfo bankCardInfo);
}