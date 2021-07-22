package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.BankCardInfo;
import com.line.backstage.entity.sysentity.ManBankVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 银行卡信息(BankCardInfo)表数据库访问层
 *
 * @author jack
 * @since 2000-06-24 10:49:55
 */
public interface BankCardInfoMapper extends TkBaseMapper<BankCardInfo> {

    List<ManBankVo> queryManBankVoForPage(ManBankVo bankVo);
    Integer queryUserId(@Param("accountId") Integer accountId);
}