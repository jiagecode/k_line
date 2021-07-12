package com.line.backstage.service;
 
import com.line.backstage.entity.SysRechageConfig;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 后台管理系统充值路径配置表(SysRechageConfig)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:35:55
 */
public interface SysRechageConfigService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysRechageConfig 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, SysRechageConfig sysRechageConfig);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysRechageConfig 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, SysRechageConfig sysRechageConfig);
 
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
     * @param sysRechageConfig 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, SysRechageConfig sysRechageConfig);
 
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRechageConfig queryById(Integer id);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysRechageConfig 查询条数
     * @return 对象列表
     */
    PageWrapper<SysRechageConfig> list(Integer loginUserId, SysRechageConfig sysRechageConfig);
}