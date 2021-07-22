package com.line.backstage.entity.sysentity;

import com.line.backstage.entity.BankCardInfo;
import lombok.Data;

import java.util.Date;

/**
 * @Author
 * @Date 2000/7/5 9:50
 * @Version 1.0
 * @Desc 后台管理系统-银行卡对象
 */
@Data
public class ManBankVo extends BankCardInfo {
    /**
     * 交易姓名
     */
    private String userRealName;
    /**
     * 查询开始时间
     */
    private Date beginDate;
    /**
     * 查询结束时间
     */
    private Date endDate;
}
