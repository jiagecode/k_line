package com.line.backstage.entity.sysentity;

import com.line.backstage.entity.PositionInfo;
import lombok.Data;

import java.util.Date;

/**
 * @Author DWanBean
 * @Date 2021/7/30 20:44
 * @Version 1.0
 * @Desc
 */
@Data
public class ManPosiVo extends PositionInfo {
    /**
     * 用户名
     */
    private String userRealName;
    private Integer orderId;
    private Integer agentId;
    private String agentName;
    private Date settlementDate;
    private Integer orderCycle;
    private Double expectedReturn;
}
