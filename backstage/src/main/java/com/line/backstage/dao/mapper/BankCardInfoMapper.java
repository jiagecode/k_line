package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.BankCardInfo;
import com.line.backstage.entity.sysentity.ManBankVo;

import java.util.List;

/**
 * 银行卡信息(BankCardInfo)表数据库访问层
 *
 * @author Zy
 * @since 2021-06-24 10:49:55
 */
public interface BankCardInfoMapper extends TkBaseMapper<BankCardInfo> {

    List<ManBankVo> queryManBankVoForPage(ManBankVo bankVo);
}