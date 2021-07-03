package com.line.backstage.service;

import com.line.backstage.entity.SysPower;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 后台管理系统角色与菜单对应关系(SysPower)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:35:45
 */
public interface SysPowerService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysPower 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SysPower sysPower);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysPower 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SysPower sysPower);
 
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
     * @param sysPower 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SysPower sysPower);
 
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPower queryById(Integer id);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysPower 查询条数
     * @return 对象列表
     */
    PageWrapper<SysPower> list(Integer loginUserId, SysPower sysPower);
}