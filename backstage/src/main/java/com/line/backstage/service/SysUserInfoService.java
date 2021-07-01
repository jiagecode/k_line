package com.line.backstage.service;
 
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 后台管理系统用户表(SysUserInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:36:19
 */
public interface SysUserInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SysUserInfo sysUserInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SysUserInfo sysUserInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param sysUserId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer sysUserId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SysUserInfo sysUserInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param sysUserId 主键
     * @return 实例对象
     */
    SysUserInfo queryById(Integer sysUserId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<SysUserInfo> list(Integer loginUserId, SysUserInfo sysUserInfo);
}