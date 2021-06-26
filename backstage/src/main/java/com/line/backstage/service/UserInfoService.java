package com.line.backstage.service;

import com.line.backstage.entity.UserInfo;
import com.line.backstage.utils.PageWrapper;

/**
 * 用户信息(UserInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-06-24 10:48:02
 */
public interface UserInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, UserInfo userInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, UserInfo userInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param userId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer userId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, UserInfo userInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserInfo queryById(Integer userId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<UserInfo> list(Integer loginUserId, UserInfo userInfo);
}