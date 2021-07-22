package com.line.backstage.scheduler;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.StrUtils;
import com.line.backstage.vo.SkuInfoVo;
import com.line.backstage.websocket.KLineDataServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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

        // 判断在线用户数 没有不推送
        if (KLineDataServer.onlineCount.intValue() == 0) {
            System.out.println("没有用户访问..不推送数据.." + minuteStamp);
            return;
        }

        String coinObjList = StrUtils.objToStr(redisUtil.get("CoinObjList"));
        List<SkuInfoVo> skuList = JsonUtils.toList(coinObjList, SkuInfoVo.class);
        for (SkuInfoVo skuInfoVo : skuList) {
            // 查询并推送数据
            redisTemplate.convertAndSend(skuInfoVo.getSkuCode(), StrUtils.objToStr(redisUtil.get(skuInfoVo.getSkuCode() + "_ss_" + minuteStamp)));
        }
    }

}
