package com.line.backstage.service;
 
import com.line.backstage.entity.PositionInfo;
import com.line.backstage.utils.PageWrapper;
 
/**
 * 用户持仓信息(PositionInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:34:50
 */
public interface PositionInfoService {
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param positionInfo 实例对象
     * @return 是否成功
     */
    int save(Integer loginUserId, PositionInfo positionInfo);
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param positionInfo 实例对象
     * @return 是否成功
     */
    int insert(Integer loginUserId, PositionInfo positionInfo);
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param positionId 主键
     * @return 是否成功
     */
    int delete(Integer loginUserId, Integer positionId);
 
    /**
     * 修改数据
     *
     * @param loginUserId 用户ID
     * @param positionInfo 实例对象
     * @return 是否成功
     */
    int update(Integer loginUserId, PositionInfo positionInfo);
 
    /**
     * 通过ID查询单条数据
     *
     * @param positionId 主键
     * @return 实例对象
     */
    PositionInfo queryById(Integer positionId);
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param positionInfo 查询条数
     * @return 对象列表
     */
    PageWrapper<PositionInfo> list(Integer loginUserId, PositionInfo positionInfo);
}