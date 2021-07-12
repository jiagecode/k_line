package com.line.backstage.dao.mapper;

import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.entity.sysentity.ManMoneyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户账户(AccountInfo)表数据库访问层
 *
 * @author jack
 * @since 2000-07-01 11:34:17
 */
public interface AccountInfoMapper extends TkBaseMapper<AccountInfo> {
    /**
     * 根据用户id查询对应账户id
     *
     * @param userId
     * @return
     */
    Integer queryMyAccountIdByUserId(@Param("userId") Integer userId);

    Double queryMyMoneyByUserId(@Param("userId") Integer userId);

    List<AccountInfo> queryForPage(AccountInfo accountInfo);

    ManMoneyVo queryManMoneyVo(ManMoneyVo manMoneyVo);

    Double sumAllUserMoney();

    Double sumAllUserProfitAndLoss();

    /**
     * 根据用户id查询 AccountInfo
     *
     * @return
     */
    AccountInfo queryByUserId(@Param("userId") Integer userId);
}