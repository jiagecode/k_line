package com.line.backstage.scheduler;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.websocket.KLineDataServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendDataToClientSchedulerTask {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每秒执行 数据推送
     */
    @Scheduled(cron = "0/1 * * * * ? ")
    private void proces() {
        // 获取当前分钟的时间戳
        String minuteStamp = DateUtil.getYesterdayStamp();

        // 根据set推送
        if (KLineDataServer.codeList.size() == 0) {
            return;
        }
        KLineDataServer.codeList.stream().forEach(item -> {
            // 查询并推送数据
            redisTemplate.convertAndSend(item, redisUtil.get(item + "_ss_" + minuteStamp));
        });
    }

}
