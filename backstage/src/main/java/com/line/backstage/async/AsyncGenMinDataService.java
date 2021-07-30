package com.line.backstage.async;

import com.fasterxml.jackson.databind.JsonNode;
import com.line.backstage.redis.RedisUtil;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.StrUtils;
import com.line.backstage.vo.SkuInfoOhlcvVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class AsyncGenMinDataService {

    @Autowired
    private RedisUtil redisUtil;
    private final double CON = 0.00001157;
    @Async
    public void autoGenDataMain(List<SkuInfoOhlcvVo> listOhlcv, String skuCode) {
        // 获取当天的基础数据
        JsonNode mainDate = JsonUtils.toJsonNode(StrUtils.objToStr(redisUtil.get(skuCode + "_dd_" + DateUtil.getYesterdayEightStamp())));

        BigDecimal beforeNowPrice;
        BigDecimal afterNowPrice;
        BigDecimal tempPriceNowPrice;
        SkuInfoOhlcvVo temp;

        for (int i = 1; i < listOhlcv.size(); i++) {
            SkuInfoOhlcvVo beforeOne = listOhlcv.get(i - 1);
            SkuInfoOhlcvVo afterOne = listOhlcv.get(i);

            // 金额
            beforeNowPrice = BigDecimal.valueOf(beforeOne.getOpenPrice()).setScale(5, BigDecimal.ROUND_HALF_UP);
            afterNowPrice = BigDecimal.valueOf(afterOne.getOpenPrice()).setScale(5, BigDecimal.ROUND_HALF_UP);
            tempPriceNowPrice = afterNowPrice.subtract(beforeNowPrice).setScale(5, BigDecimal.ROUND_HALF_UP);

            Random random = new Random();
            int s;
            // 每秒
            for (int j = 1; j <= 60; j++) {
                // 权重比2 看涨
                s = random.nextInt(3);
                BigDecimal divide = tempPriceNowPrice.divide(BigDecimal.valueOf(60 - j + 1), 5, BigDecimal.ROUND_HALF_UP);
                if (tempPriceNowPrice.compareTo(BigDecimal.ZERO) < 0) {
                    if (s == 2 || s == 0) {
                        beforeNowPrice = beforeNowPrice.add(divide);
                    } else {
                        beforeNowPrice = beforeNowPrice.subtract(divide);
                    }
                } else {
                    if (s == 2 || s == 0) {
                        beforeNowPrice = beforeNowPrice.add(divide);
                    } else {
                        beforeNowPrice = beforeNowPrice.subtract(divide);
                    }
                }
                tempPriceNowPrice = afterNowPrice.subtract(beforeNowPrice).setScale(5, BigDecimal.ROUND_HALF_UP);
                Long timeStamp = Long.parseLong(beforeOne.getTimeStamp()) + j;
                temp = new SkuInfoOhlcvVo();
                temp.setOrderNum(j);
                temp.setTimeStamp(String.valueOf(timeStamp));
                temp.setSkuCode(beforeOne.getSkuCode());
                temp.setNowPrice(beforeNowPrice.doubleValue());

                temp.setClose(mainDate.get("close").asDouble());
                temp.setMaxPrice(mainDate.get("maxPrice").asDouble());
                temp.setMinPrice(mainDate.get("minPrice").asDouble());
                temp.setVolumeTo(mainDate.get("volumeTo").asDouble());
                temp.setOpenPrice(mainDate.get("openPrice").asDouble());

                double volumeFrom = mainDate.get("volumeFrom").asDouble();
                BigDecimal big_from = BigDecimal.valueOf(volumeFrom);
                BigDecimal decimal_60 = BigDecimal.valueOf(s + 1);
                BigDecimal vf = s > 1 ? big_from.add(big_from.divide(decimal_60, 5, BigDecimal.ROUND_HALF_UP)) : big_from.multiply(big_from.divide(decimal_60, 5, BigDecimal.ROUND_HALF_UP));
                temp.setVolumeFrom(vf.doubleValue());
                redisUtil.set(beforeOne.getSkuCode() + "_ss_" + timeStamp, JsonUtils.toJsonString(temp), 0);
            }
        }
        log.info("coin_SIZE: 【{}】", listOhlcv.size());
    }

    public static void main(String[] args) {

        System.out.println(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(10)));
        BigDecimal beforeNowPrice = BigDecimal.valueOf(15909);
        BigDecimal afterNowPrice = BigDecimal.valueOf(15929);
        BigDecimal tempPriceNowPrice = afterNowPrice.subtract(beforeNowPrice).setScale(5, BigDecimal.ROUND_HALF_UP);

        Random random = new Random();
        // 每秒
        int x = 0;
        int y = 0;
//        for (int j = 1; j <= 60; j++) {
//            // 权重比2 看涨
//            int s = random.nextInt(3);
//            BigDecimal divide = tempPriceNowPrice.divide(BigDecimal.valueOf(60 - j + 1), 5, BigDecimal.ROUND_HALF_UP);
//            if (tempPriceNowPrice.compareTo(BigDecimal.ZERO) < 0) {
//                System.out.println("目标金额小于变动金额");
//                if (s == 2 || s == 0) {
//                    y+=1;
//                    beforeNowPrice = beforeNowPrice.add(divide);
//                } else {
//                    x+=1;
//                    beforeNowPrice = beforeNowPrice.subtract(divide);
//                }
//            } else {
//                System.out.println("目标金额大于变动金额");
//                if (s == 2 || s == 0) {
//                    x+=1;
//                    beforeNowPrice = beforeNowPrice.add(divide);
//                } else {
//                    y+=1;
//                    beforeNowPrice = beforeNowPrice.subtract(divide);
//                }
//            }
//            tempPriceNowPrice = afterNowPrice.subtract(beforeNowPrice).setScale(5, BigDecimal.ROUND_HALF_UP);
////            System.out.println("第秒" + j + "-目标金额" + afterNowPrice + "；变动金额" + beforeNowPrice + "；差值" + tempPriceNowPrice);
//            System.out.println("第秒" + j + "-目标金额" + afterNowPrice + " " + beforeNowPrice + " " + tempPriceNowPrice);
//        }
        System.err.println(" " + afterNowPrice + " " + beforeNowPrice + " " + tempPriceNowPrice);
        System.err.println(" 变动金额加了" + x + "变动金额减了 " + y);
    }

}
