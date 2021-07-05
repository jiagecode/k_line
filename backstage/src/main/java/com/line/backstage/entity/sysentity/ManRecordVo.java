package com.line.backstage.entity.sysentity;

import com.line.backstage.entity.AccountRecord;
import lombok.Data;

import java.util.Date;

/**
 * @Author DWanBean
 * @Date 2021/7/4 20:58
 * @Version 1.0
 * @Desc 管理系统平仓日志
 */
@Data
public class ManRecordVo extends AccountRecord {
    /**
     * 查询开始时间
     */
    private Date beginDate;
    /**
     * 查询结束时间
     */
    private Date endDate;
    /**
     * 交易姓名
     */
    private String userRealName;
    /**
     * 查询类型 1-盈利 2-亏损
     */
    private Integer queryDataFlag;
    /**
     * 查询类型 1-平仓 2-个人报表 3-红利报表 4-佣金报表
     */
    private Integer queryType;
    /**
     * 所属用户
     */
    private Integer userId;
    private Integer integral;
    private String agentName;
}
