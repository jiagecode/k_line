package com.line.backstage.service;

import com.line.backstage.entity.SendMassage;
import com.line.backstage.utils.PageWrapper;

/**
 * 聊天记录(SendMassage)表服务实现类
 *
 * @author fimex
 * @since 2021-08-12 14:29:14
 */
public interface SendMassageService {

    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SendMassage sendMassage);

    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SendMassage sendMassage);

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
     * @param sendMassage 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SendMassage sendMassage);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SendMassage queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 查询条数
     * @return 对象列表
     */
    PageWrapper<SendMassage> list(Integer loginUserId, SendMassage sendMassage);
}
