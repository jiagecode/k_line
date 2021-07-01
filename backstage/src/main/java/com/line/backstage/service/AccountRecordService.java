package com.line.backstage.service;
 
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 用户资金账户变动记录(AccountRecord)表服务实现类
 *
 * @author Zy
 * @since 2021-06-29 14:46:07
 */
public interface AccountRecordService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param accountRecord 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, AccountRecord accountRecord);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param accountRecord 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, AccountRecord accountRecord);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param recordId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer recordId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param accountRecord 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, AccountRecord accountRecord);
 
    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    AccountRecord queryById(Integer recordId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param accountRecord 查询条数
     * @return 对象列表
     */
    PageWrapper<AccountRecord> list(Integer loginUserId, AccountRecord accountRecord);
}