package com.line.backstage.async;

import com.fasterxml.jackson.databind.JsonNode;
import com.line.backstage.redis.RedisUtil;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.StrUtils;
import com.line.backstage.vo.SkuInfoOhlcvVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class AsyncGenMinDataService {

    @Autowired
    private RedisUtil redisUtil;

    @Async
    public void autoGenDataMain(List<SkuInfoOhlcvVo> listOhlcv, String skuCode) {
        // 获取当天的基础数据
        JsonNode maindata = JsonUtils.toJsonNode(StrUtils.objToStr(redisUtil.get(skuCode + "_dd_" + DateUtil.getYesterdayEightStamp())));
        BigDecimal beforNowPrice;
        BigDecimal afterNowPrice;
        BigDecimal tempPiceNowPrice;
        SkuInfoOhlcvVo temp;
        // 临时字段
        BigDecimal price;
        for (int i = 1; i < listOhlcv.size(); i++) {
            System.out.println("coin: " + listOhlcv.get(0).getSkuCode() + " All:" + listOhlcv.size() + " Now:" + i);
            SkuInfoOhlcvVo beforOne = listOhlcv.get(i - 1);
            SkuInfoOhlcvVo afterOne = listOhlcv.get(i);

            // 金额
            beforNowPrice = BigDecimal.valueOf(beforOne.getOpenPrice()).setScale(4, BigDecimal.ROUND_HALF_UP);
            afterNowPrice = BigDecimal.valueOf(afterOne.getOpenPrice()).setScale(4, BigDecimal.ROUND_HALF_UP);
            tempPiceNowPrice = afterNowPrice.subtract(beforNowPrice).setScale(4, BigDecimal.ROUND_HALF_UP);

            // 生成每秒一条数据
            boolean isLastGen = false;
            for (int j = 1; j <= 60; j++) {
                // 是否关键数据 最后3条
                if (j >= 50) {
                    isLastGen = true;
                }

                price = this.genPrice(beforNowPrice, afterNowPrice, tempPiceNowPrice, isLastGen).setScale(4);
                Long timeStamp = Long.parseLong(beforOne.getTimeStamp()) + j;
                temp = new SkuInfoOhlcvVo();
                temp.setOrderNum(j);
                temp.setTimeStamp(String.valueOf(timeStamp));
                temp.setSkuCode(beforOne.getSkuCode());
                temp.setNowPrice(price.doubleValue());

                temp.setOpenPrice(maindata.get("openPrice").asDouble());
                temp.setClose(maindata.get("close").asDouble());
                temp.setMaxPrice(maindata.get("maxPrice").asDouble());
                temp.setMinPrice(maindata.get("minPrice").asDouble());
                temp.setVolumeTo(maindata.get("volumeTo").asDouble());
                temp.setVolumeFrom(maindata.get("volumeFrom").asDouble());
                redisUtil.set(beforOne.getSkuCode() + "_ss_" + timeStamp, JsonUtils.toJsonString(temp), 0);
            }
        }
    }

    /**
     * 根据差距金额返回生成金额
     *
     * @return
     */
    private BigDecimal genPrice(BigDecimal beforNowPrice, BigDecimal afterNowPrice, BigDecimal price, boolean isLastGen) {
        // 涨跌趋势
        boolean isUp = price.compareTo(BigDecimal.ZERO) > 0;

        // 根据位数生成随机数
        int tempPriceInt = beforNowPrice.intValue();
        BigDecimal result = beforNowPrice;
        BigDecimal priceTemp1 = beforNowPrice;

        Random random = new Random();
        // 1位数 0.2以内
        if (tempPriceInt > 1 && tempPriceInt <= 10) {
            priceTemp1 = BigDecimal.valueOf(random.nextInt(2000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 2位数 0.5以内
        if (tempPriceInt > 10 && tempPriceInt <= 100) {
            priceTemp1 = BigDecimal.valueOf(random.nextInt(5000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 3位数 1.5以内
        if (tempPriceInt > 100 && tempPriceInt <= 1000) {
            priceTemp1 = BigDecimal.valueOf(random.nextInt(15000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 4位数 2以内
        if (tempPriceInt > 1000 && tempPriceInt <= 10000) {
            priceTemp1 = BigDecimal.valueOf(random.nextInt(20000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 5位数 2.5以内
        if (tempPriceInt > 10000 && tempPriceInt <= 100000) {
            priceTemp1 = BigDecimal.valueOf(random.nextInt(25000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }

        // 关键数据
        if (isLastGen) {
            if (isUp) {
                return result.add(BigDecimal.valueOf(new Random().nextInt(150)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            } else {
                return result.subtract(BigDecimal.valueOf(new Random().nextInt(150)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            }
        }

        // 一定的随机增减
        int randNum = random.nextInt(20);
        if (randNum > 10) {
            // 再次随机 保证生成方向上的数据大于反方向
            int randNum2 = random.nextInt(2);
            if (randNum2 > 1) {
                if (randNum > 10) {
                    result = result.add(priceTemp1);
                } else {
                    result = result.subtract(priceTemp1);
                }
            } else {
                if (isUp) {
                    result = result.add(priceTemp1);
                } else {
                    result = result.subtract(priceTemp1);
                }
            }
        } else {
            if (isUp) {
                result = result.add(priceTemp1);
            } else {
                result = result.subtract(priceTemp1);
            }
        }

        return result;

    }

}
