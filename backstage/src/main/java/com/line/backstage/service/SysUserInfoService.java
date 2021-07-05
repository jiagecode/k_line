package com.line.backstage.service;
 
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.entity.sysentity.*;
import com.line.backstage.utils.PageWrapper;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询用户列表
     * @param loginUserId
     * @param sysUserInfo
     * @return
     */
    PageWrapper<ManUserVo> queryManUserVoForPage(Integer loginUserId, ManUserVo sysUserInfo);

    /**
     * 查询充值列表数据
     * @param loginUserId
     * @param manCashVo
     * @return
     */
    PageWrapper<ManCashVo> queryManCashVoForPage(Integer loginUserId, ManCashVo manCashVo);
    PageWrapper<ManOrderVo> queryManOrderVoForPage(Integer loginUserId, ManOrderVo manOrderVo);
    PageWrapper<ManRecordVo> queryManRecordVoForPage(Integer loginUserId,ManRecordVo recordVo);
    PageWrapper<ManBankVo> queryManBankVoForPage(Integer loginUserId, ManBankVo recordVo);
    /**
     * 系统管理员登录
     * @param sysUserInfo
     * @return
     */
    SysUserInfo login(SysUserInfo sysUserInfo);

    /**
     * 菜单权限
     * @param userId
     * @return
     */
    Map getUserInfo(Integer userId);
}