package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.AccountInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户账户(AccountInfo)表数据库访问层
 *
 * @author Zy
 * @since 2021-07-01 11:34:17
 */
public interface AccountInfoMapper extends TkBaseMapper<AccountInfo> {
    /**
     * 根据用户id查询对应账户id
     * @param userId
     * @return
     */
    Integer queryMyAccountIdByUserId(@Param("userId")Integer userId);
    Double queryMyMoneyByUserId(@Param("userId")Integer userId);
}