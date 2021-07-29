package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.entity.OrderRecordExp;
import com.line.backstage.entity.sysentity.ManRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户资金账户变动记录(AccountRecord)表数据库访问层
 *
 * @author jack
 * @since 2000-06-29 14:46:07
 */
public interface AccountRecordMapper extends TkBaseMapper<AccountRecord> {

    Integer InsertOneExp(OrderRecordExp exp);
    List<ManRecordVo> queryManRecordVoForPage(ManRecordVo recordVo);
    List<AccountRecord> queryH5Page(@Param("accId") Integer accId);
    Map<String,Object> sumAllMoneyRecord(Map map);
    Integer updateForDiy(@Param("diyId") Integer diyId, @Param("userId") Integer userId, @Param("del") Integer del, @Param("sysUserId")Integer sysUserId);
}