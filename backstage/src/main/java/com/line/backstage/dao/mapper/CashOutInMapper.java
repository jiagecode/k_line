package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.CashOutIn;
import com.line.backstage.entity.sysentity.ManCashVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户资金充值或提现记录(CashOutIn)表数据库访问层
 *
 * @author jack
 * @since 2000-07-01 11:34:38
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

    /**
     * 创建代理业绩
     * @param agentId
     * @param userId
     * @return
     */
    Integer insertOneAgentScore(@Param("agentId") Integer agentId,@Param("userId") Integer userId);

    /**
     * 查询代理业绩
     * @param agentId
     * @return
     */
    BigDecimal queryAgentScore(@Param("agentId") Integer agentId);

    /**
     * 跟新代理业绩
     * @param agentId
     * @param inCash
     * @param outCash
     * @return
     */
    Integer updateAgentScore(@Param("agentId") Integer agentId,@Param("inCash") BigDecimal inCash,@Param("outCash")BigDecimal outCash);
}