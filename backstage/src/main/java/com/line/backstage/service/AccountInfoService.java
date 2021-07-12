package com.line.backstage.service;
 
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 用户账户(AccountInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:34:17
 */
public interface AccountInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, AccountInfo accountInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, AccountInfo accountInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param accountId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer accountId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, AccountInfo accountInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param accountId 主键
     * @return 实例对象
     */
    AccountInfo queryById(Integer accountId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<AccountInfo> list(Integer loginUserId, AccountInfo accountInfo);

    /**
     * 根据userId查询AccountInfo
     * @param loginUserId
     * @return
     */
    AccountInfo queryByLoginUserId(Integer loginUserId);
}