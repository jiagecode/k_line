package com.line.backstage.scheduler;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.service.SkuInfoService;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.StrUtils;
import com.line.backstage.vo.SkuInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenDataSchedulerTask {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 每分钟执行 数据生成
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    private void proces() {
        // 货币列表
        String coinObjList = StrUtils.objToStr(redisUtil.get("CoinObjList"));
        List<SkuInfoVo> skuList = JsonUtils.toList(coinObjList, SkuInfoVo.class);

        // 获取当前时间戳
        int nowMin = Integer.parseInt(DateUtil.getNowMinuteStamp());
        // 生成数据
        this.handleGenData(skuList, nowMin);
    }

    /**
     * 生成数据的处理
     */
    private void handleGenData(List<SkuInfoVo> skuList, int nowMin) {
        // 对每种货币循环处理
        for (SkuInfoVo sku : skuList) {
            // 获取前一条记录的key，根据redis有的数据
            String beforKey = this.getBeforMin(sku.getSkuCode(), nowMin);
            // 获取后一条记录的key，根据redis有的数据
            String afterKey = this.getAfterMin(sku.getSkuCode(), nowMin);
            System.out.println("前：" + beforKey + " 后：" + afterKey);
            // 生成前一条基础数据
            if (StringUtils.isEmpty(beforKey)) {
                beforKey = sku.getSkuCode() + "_" + (nowMin - 1) + "_1";
                this.createSkuKeyData(sku, beforKey);
            }
            if (StringUtils.isEmpty(afterKey)) {
                afterKey = sku.getSkuCode() + "_" + (nowMin + 1) + "_1";
                this.createSkuKeyData(sku, afterKey);
            }

            // 获取记录 根据key里面的时间，判断需要生成的区间数量 n条 获取对应的基础数据
            String centerKey = sku.getSkuCode() + "_" + nowMin + "_1";
            List<SkuInfoVo> skuInfoVos = this.getGenParamSkuList(sku, centerKey, beforKey, afterKey);

            // 生成记录
            this.handleGenVirtualData(centerKey, beforKey, afterKey, skuInfoVos);

        }
    }

    /**
     * 正式生成记录 每秒两条
     * @param centerKey
     * @param beforKey
     * @param afterKey
     * @param skuInfoVos
     */
    private void handleGenVirtualData(String centerKey, String beforKey, String afterKey, List<SkuInfoVo> skuInfoVos) {
        // 前一条和中间条的生成

        // 中间条和后一条的生成


    }

    /**
     * 获取用于生成的数据
     *
     * @param centerKey
     * @param beforKey
     * @param afterKey
     */
    private List<SkuInfoVo> getGenParamSkuList(SkuInfoVo sku, String centerKey, String beforKey, String afterKey) {
        // TODO 根据key里面的时间，判断需要生成的区间数量
        // TODO 获取基础的数据

        List<SkuInfoVo> result = new ArrayList<>();
        result.add(JsonUtils.parse(StrUtils.objToStr(redisUtil.get(beforKey)), SkuInfoVo.class));

        // 如果没有中间的数据，则用最新数据代替
        if (!redisUtil.hasKey(centerKey)) {
            redisUtil.set(centerKey, JsonUtils.toJsonString(sku), 0);
        }
        result.add(JsonUtils.parse(StrUtils.objToStr(redisUtil.get(centerKey)), SkuInfoVo.class));
        result.add(JsonUtils.parse(StrUtils.objToStr(redisUtil.get(afterKey)), SkuInfoVo.class));
        return result;
    }

    /**
     * 根据当前数据创建一条
     *
     * @param sku
     */
    private void createSkuKeyData(SkuInfoVo sku, String key) {
        redisUtil.set(key, JsonUtils.toJsonString(sku), 0);
    }

    /**
     * 获取前一条记录的key
     *
     * @return
     */
    private String getBeforMin(String skuCode, int nowMin) {
        String result = "";
        for (int i = 1; i <= 3; i++) {
            String key = skuCode + "_" + (nowMin - i) + "_1";
            if (redisUtil.hasKey(key)) {
                result = key;
                break;
            }
        }
        return result;
    }

    /**
     * 获取后一条记录的key
     *
     * @return
     */
    private String getAfterMin(String skuCode, int nowMin) {
        String result = "";
        for (int i = 0; i <= 2; i++) {
            String key = skuCode + "_" + (nowMin + i) + "_1";
            if (redisUtil.hasKey(key)) {
                result = key;
                break;
            }
        }
        return result;
    }

}
