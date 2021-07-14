package com.line.backstage.scheduler;

import com.fasterxml.jackson.databind.JsonNode;
import com.line.backstage.redis.RedisUtil;
import com.line.backstage.service.SkuInfoService;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.HttpClientUtil;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.StrUtils;
import com.line.backstage.vo.SkuInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QueryDataSchedulerTask {

    /**
     * 访问凭证
     */
    private String API_KEY = "2f5295de0ff822eb314074bbbed58c54154a0cf14a1cc9623b5b577046197d9f";

    /**
     * api地址-Multiple Symbols Price
     */
    private String API_PRICE_MULTI = "https://min-api.cryptocompare.com/data/pricemulti";
    /**
     * api地址-Multiple Symbols Full Data
     */
    private String API_PRICE_MULTI_FULL = "https://min-api.cryptocompare.com/data/pricemultifull";

    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 每分钟的0和30秒执行
     */
    @Scheduled(cron = "0,30 1-59 * * * ? ")
    private void proces() {
        // 获取当前分钟的时间戳
        String minuteStamp = DateUtil.getFiveMinuteAfterStamp();
        // 当前秒数
        int million = Integer.parseInt(DateUtil.getMillionNow());
        if (million >= 0 && million < 30) {
            minuteStamp += "_0";
        } else {
            minuteStamp += "_1";
        }

        // 获取redis缓存
        String coinCodesParam = StrUtils.objToStr(redisUtil.get("CoinCodesParam"));
        String coinObjList = StrUtils.objToStr(redisUtil.get("CoinObjList"));
        // 校验查询参数
        if (StringUtils.isEmpty(coinCodesParam)) {
            return;
        }
        // 校验列表，转成对象
        List<SkuInfoVo> skuList = JsonUtils.toList(coinObjList, SkuInfoVo.class);
        String coinsDataFull;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("fsyms", coinCodesParam);
            params.put("tsyms", "USD");
            params.put("api_key", API_KEY);

            // 查询数据
            coinsDataFull = HttpClientUtil.getInstance().doGet(API_PRICE_MULTI_FULL, params, null);
        } catch (Exception e) {
            throw new RuntimeException("查询数据失败：" + e.getMessage());
        }
        System.out.println("-------------------------------------------------------------------------------------------------------");
        // 解析返回的结果
        JsonNode coinNode = JsonUtils.toJsonNode(coinsDataFull);
        if (coinNode.hasNonNull("RAW")) {
            JsonNode raw = coinNode.get("RAW");
            // 遍历获取每个品种的信息
            for (SkuInfoVo sku : skuList) {
                // 获取品种
                if (!raw.hasNonNull(sku.getSkuCode())) {
                    continue;
                }
                JsonNode coinItem = raw.get(sku.getSkuCode());
                JsonNode usd = coinItem.get("USD");
                String lastupdate = usd.get("LASTUPDATE").asText("");
                String price = usd.get("PRICE").asText("");
                String openday = usd.get("OPENDAY").asText("");
                String highday = usd.get("HIGHDAY").asText("");
                String lowday = usd.get("LOWDAY").asText("");
//                System.out.println(sku.getSkuName() + "-" + lastupdate + "-" + "-" + price + "-" + "-" + openday + "-" + "-" + highday + "-" + "-" + lowday);
                // 回写到list
                sku.setNowPrice(Double.valueOf(price));
                sku.setOpenPrice(Double.valueOf(openday));
                sku.setMinPrice(Double.valueOf(lowday));
                sku.setMaxPrice(Double.valueOf(highday));
                sku.setEditDate(DateUtil.timeStamp2Date(lastupdate));
                // 写入redis
                redisUtil.set(sku.getSkuCode() + "_" + minuteStamp, JsonUtils.toJsonString(sku), 0);
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------------------");
        // 新数据写入到数据库
        skuInfoService.updateAll(skuList);
        // 写入redis
        String CoinObjList = JsonUtils.toJsonString(skuList);
//        if (redisUtil.hasKey("CoinObjList")) {
//            redisUtil.delete("CoinObjList");
//        }
        redisUtil.set("CoinObjList", CoinObjList, 0);
    }
}
