package com.line.backstage.service;
 
import com.line.backstage.entity.SysRoleInfo;
import com.line.backstage.utils.PageWrapper;

import java.util.List;
import java.util.Map;

/**
 * 后台管理系统角色表(SysRoleInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:36:06
 */
public interface SysRoleInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SysRoleInfo sysRoleInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SysRoleInfo sysRoleInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param roleId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer roleId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SysRoleInfo sysRoleInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRoleInfo queryById(Integer roleId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<SysRoleInfo> list(Integer loginUserId, SysRoleInfo sysRoleInfo);
    /**
     * 授权菜单信息
     */
    List<Map> authorize(Integer userId);
    List<Integer> findMenuIdsByRoleId(Integer roleId);
}