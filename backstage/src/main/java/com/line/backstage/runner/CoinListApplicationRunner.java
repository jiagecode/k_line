package com.line.backstage.runner;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.service.SkuInfoService;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.vo.SkuInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 启动后设置生成序列
 */
@Component
@Order(1)
public class CoinListApplicationRunner implements ApplicationRunner {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SkuInfoVo> skuList = skuInfoService.listAll();
        skuList.remove(Collections.singletonList(null));
        if (CollectionUtils.isEmpty(skuList)) {
            return;
        }
        String CoinCodesParam = skuList.stream().map(SkuInfoVo::getSkuCode).collect(Collectors.joining(","));
        // 先删除
        if (redisUtil.hasKey("CoinCodesParam")) {
            redisUtil.delete("CoinCodesParam");
        }
        redisUtil.set("CoinCodesParam", CoinCodesParam, 0);
        System.out.println("CoinCodesParam：" + CoinCodesParam);

        String CoinObjList = JsonUtils.toJsonString(skuList);
        // 先删除
        if (redisUtil.hasKey("CoinObjList")) {
            redisUtil.delete("CoinObjList");
        }
        redisUtil.set("CoinObjList", CoinObjList, 0);
        System.out.println("CoinObjList：" + CoinObjList);

    }
}
