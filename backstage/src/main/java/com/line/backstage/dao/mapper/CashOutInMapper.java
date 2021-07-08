package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.CashOutIn;
import com.line.backstage.entity.sysentity.ManCashVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户资金充值或提现记录(CashOutIn)表数据库访问层
 *
 * @author Zy
 * @since 2021-07-01 11:34:38
 */
public interface CashOutInMapper extends TkBaseMapper<CashOutIn> {
    List<CashOutIn> queryMyCashOutInList(@Param("accountId") Integer accountId, @Param("cashType")Integer cashType);
    /**
     * 查询充值记录
     * @param vo
     * @return
     */
    List<ManCashVo> queryManCashVoList(ManCashVo vo);
    Double sumAllCash(Map map);
    Integer updateForDiy(@Param("diyId") Integer diyId,@Param("userId") Integer userId,@Param("del") Integer del,@Param("sysUserId")Integer sysUserId);
}