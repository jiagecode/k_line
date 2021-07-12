package com.line.backstage.service;
 
import com.line.backstage.entity.SysCashCheck;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 后台管理系统提现审核记录表(SysCashCheck)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:35:16
 */
public interface SysCashCheckService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SysCashCheck sysCashCheck);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SysCashCheck sysCashCheck);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param id 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer id);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SysCashCheck sysCashCheck);
 
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysCashCheck queryById(Integer id);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 查询条数
     * @return 对象列表
     */
    PageWrapper<SysCashCheck> list(Integer loginUserId, SysCashCheck sysCashCheck);
}