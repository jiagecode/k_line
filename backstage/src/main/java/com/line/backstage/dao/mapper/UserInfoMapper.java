package com.line.backstage.dao.mapper;

import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.entity.sysentity.ManUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户信息(UserInfo)表数据库访问层
 *
 * @author jack
 * @since 2000-06-23 21:25:42
 */
public interface UserInfoMapper extends TkBaseMapper<UserInfo> {
    /**
     * 多添加查询用户
     * @param vo
     * @return
     */
    List<ManUserVo> queryManUserVoList(ManUserVo vo);

    /**
     * 统计用户数
     * @param map
     * @return
     */
    Integer countUserNum(Map map);

    Integer queryUserIdForPhone(@Param("tel") String phone);

    String queryAgentNameByAgentId(@Param("agentId")Integer agentId);
    Integer updateLastLoginDate(@Param("userId")Integer loginUserId);
    Integer queryWinRate(@Param("userId")Integer loginUserId);
    Double queryCommissionRate(@Param("userId")Integer loginUserId);
}