package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.PositionInfo;
import com.line.backstage.entity.sysentity.ManPosiVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户持仓信息(PositionInfo)表数据库访问层
 *
 * @author jack
 * @since 2000-07-01 11:34:50
 */
public interface PositionInfoMapper extends TkBaseMapper<PositionInfo> {
    PositionInfo queryOne(@Param("posId") Integer posId);

    List<ManPosiVo> selectForPage(@Param("userId") Integer userId, @Param("del") Integer del,@Param("pst")Integer pst);

    /**
     * 查询持仓
     * @param vo
     * @return
     */
    List<ManPosiVo> queryManPosiVo(ManPosiVo vo);
    Integer updateForDiy(@Param("diyId") Integer diyId, @Param("userId") Integer userId, @Param("del") Integer del, @Param("sysUserId")Integer sysUserId);
}