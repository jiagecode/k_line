package com.line.backstage.runner;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.service.SkuInfoService;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.vo.SkuInfoVo;
import com.line.backstage.vo.UserInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Order(1)
public class CoinListApplicationRunner implements ApplicationRunner {

    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 用户列表
        List<UserInfoVo> userInfos = userInfoService.queryListAll();
        userInfos.remove(Collections.singletonList(null));
        if (CollectionUtils.isEmpty(userInfos)) {
            throw new RuntimeException("获取用户列表失败！");
        }

        // 商品列表
        List<SkuInfoVo> skuList = skuInfoService.listAll();
        skuList.remove(Collections.singletonList(null));
        if (CollectionUtils.isEmpty(skuList)) {
            throw new RuntimeException("获取货币列表失败！");
        }
        String CoinCodesParam = skuList.stream().map(SkuInfoVo::getSkuCode).collect(Collectors.joining(","));
        redisUtil.set("CoinCodesParam", CoinCodesParam, 0);
//        System.out.println("CoinCodesParam：" + CoinCodesParam);

        String CoinObjList = JsonUtils.toJsonString(skuList);
        redisUtil.set("CoinObjList", CoinObjList, 0);
//        System.out.println("CoinObjList：" + CoinObjList);

        String UserInfoList = JsonUtils.toJsonString(userInfos);
        redisUtil.set("UserInfoList", UserInfoList, 0);
//        System.out.println("UserInfoList：" + UserInfoList);


    }
}
