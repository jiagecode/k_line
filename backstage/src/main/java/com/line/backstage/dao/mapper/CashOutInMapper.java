package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.CashOutIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户资金充值或提现记录(CashOutIn)表数据库访问层
 *
 * @author Zy
 * @since 2021-07-01 11:34:38
 */
public interface CashOutInMapper extends TkBaseMapper<CashOutIn> {
    List<CashOutIn> queryMyCashOutInList(@Param("accountId") Integer accountId, @Param("cashType")Integer cashType);

}