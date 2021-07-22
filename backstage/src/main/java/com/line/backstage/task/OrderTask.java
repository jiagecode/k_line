package com.line.backstage.task;

import com.line.backstage.service.TaskOrderSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author
 * @Date 2021/7/14 18:25
 * @Version 1.0
 * @Desc
 */
@Component
public class OrderTask {
    @Autowired
    private TaskOrderSettlementService settlementService;

   // @Scheduled(cron = "*/1 * * * * ?")
    public void dealOrderTask(){
        settlementService.dealOrderSettlement();
    }
}
