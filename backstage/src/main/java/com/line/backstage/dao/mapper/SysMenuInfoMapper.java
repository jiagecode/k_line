package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.SysMenuInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台管理系统菜单表(SysMenuInfo)表数据库访问层
 *
 * @author Zy
 * @since 2021-07-01 11:35:35
 */
public interface SysMenuInfoMapper extends TkBaseMapper<SysMenuInfo> {

    /**
     * 获取菜单
     * @param roleId
     * @return
     */
    List<SysMenuInfo> findByRoleId(Integer roleId);

    Integer queryRoleIdByUserId(@Param("userId") Integer roleId);

    List<SysMenuInfo> queryMenuListByRoleId(@Param("roleId") Integer roleId);
    List<SysMenuInfo> findByParentIdsLike(@Param("parentIdsLike") String parentIdsLike);
    /**
     * 根据菜单ID查询它子节点数量
     *
     * @param parentIdsLike
     * @return
     */
    int getChildCountByMenuId(@Param("parentIdsLike") String parentIdsLike);
}