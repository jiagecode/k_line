package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.entity.sysentity.ManUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 后台管理系统用户表(SysUserInfo)表数据库访问层
 *
 * @author jack
 * @since 2000-07-01 11:36:19
 */
public interface SysUserInfoMapper extends TkBaseMapper<SysUserInfo> {
    /**
     * 查询首页数据
     * @param dateStr
     * @return
     */
    List<Map<String, Object>> queryHomeDate(@Param("endTime") String dateStr);
}