package com.line.backstage.dao.mapper;

import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.entity.sysentity.ManUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息(UserInfo)表数据库访问层
 *
 * @author Zy
 * @since 2021-06-23 21:25:42
 */
public interface UserInfoMapper extends TkBaseMapper<UserInfo> {
    /**
     * 多添加查询用户
     * @param vo
     * @return
     */
    List<ManUserVo> queryManUserVoList(ManUserVo vo);

    Integer queryUserIdForPhone(@Param("tel") String phone);
}