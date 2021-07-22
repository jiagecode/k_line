package com.line.backstage.scheduler;

import com.line.backstage.service.QueryAndGenDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Async
@Component
public class QueryAndGenDataSchedulerTask {

    @Autowired
    private QueryAndGenDataService queryAndGenDataService;

    /**
     * 每分钟的0和30秒执行
     */
//    @Scheduled(cron = "0,30 1-59 * * * ? ")
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void proces() {
        queryAndGenDataService.queryAndGen();
    }
}
