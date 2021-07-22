package com.line.backstage.service;
 
import com.line.backstage.entity.SysMenuInfo;
import com.line.backstage.utils.PageWrapper;

import java.util.Map;

/**
 * 后台管理系统菜单表(SysMenuInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:35:35
 */
public interface SysMenuInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SysMenuInfo sysMenuInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SysMenuInfo sysMenuInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param menuId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer menuId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SysMenuInfo sysMenuInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysMenuInfo queryById(Integer menuId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<SysMenuInfo> list(Integer loginUserId, SysMenuInfo sysMenuInfo);

    /**
     * 新增
     * @param userId
     * @param menu
     * @return
     */
    int insertForOne(Integer userId, SysMenuInfo menu);
    int updateForOne(Integer userId, SysMenuInfo menu);
    int deleteForOne(Integer userId, SysMenuInfo menu);
    Map<String,Object> queryMyMenuList(Integer loginUserId, SysMenuInfo sysMenuInfo);
}