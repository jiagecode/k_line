package com.line.backstage.runner;

import com.line.backstage.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动后将商品列表取出，放到redis
 */
@Component
@Order(2)
public class GenSequenceApplicationRunner implements ApplicationRunner {


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (redisUtil.hasKey("A_Sequence")) {
            return;
        }
        if (redisUtil.hasKey("B_Sequence")) {
            return;
        }

        // 获取当前倒退5分钟的时间

        redisUtil.set("A_Sequence", "1", 0);
        redisUtil.set("B_Sequence", "2", 0);
    }
}
