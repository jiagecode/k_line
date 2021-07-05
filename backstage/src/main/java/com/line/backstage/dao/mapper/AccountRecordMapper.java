package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.entity.sysentity.ManRecordVo;

import java.util.List;
import java.util.Map;

/**
 * 用户资金账户变动记录(AccountRecord)表数据库访问层
 *
 * @author Zy
 * @since 2021-06-29 14:46:07
 */
public interface AccountRecordMapper extends TkBaseMapper<AccountRecord> {

    List<ManRecordVo> queryManRecordVoForPage(ManRecordVo recordVo);
    Map<String,Object> sumAllMoneyRecord(Map map);
}