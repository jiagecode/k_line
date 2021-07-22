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
            for (int j = 1; j <= 60; j++) {
                Long timeStamp = Long.parseLong(beforOne.getTimeStamp()) + j;
                price = this.genPrice(beforNowPrice, afterNowPrice, tempPiceNowPrice).setScale(4);
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
    private BigDecimal genPrice(BigDecimal beforNowPrice, BigDecimal afterNowPrice, BigDecimal price) {
        // isUp 如果true 说明涨，生成时可以浮动到afterNowPrice上
        // isUp 如果false 说明跌，生成时可以浮动到beforNowPrice下
        boolean isUp = price.compareTo(BigDecimal.ZERO) >= 0;
        BigDecimal result = beforNowPrice;
        BigDecimal priceAbs = price.abs();
        int baseNum = 0, randomNum = 0;
        // 如果金额差异低于1，随机以befor或者after 的金额返回
        int random = new Random().nextInt(2);
        if (priceAbs.compareTo(BigDecimal.ONE) <= 0) {
            // 差距太小时，根据数据大小补偿
            int tempPriceInt = beforNowPrice.intValue();
            // 1位数 补偿0.2以内
            if (tempPriceInt > 1 && tempPriceInt <= 10) {
                result = result.add(BigDecimal.valueOf(new Random().nextInt(2000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            }
            // 2位数 补偿0.5以内
            if (tempPriceInt > 10 && tempPriceInt <= 100) {
                result = result.add(BigDecimal.valueOf(new Random().nextInt(5000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            }
            // 3位数 补偿1.5以内
            if (tempPriceInt > 100 && tempPriceInt <= 1000) {
                result = result.add(BigDecimal.valueOf(new Random().nextInt(15000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            }
            // 4位数 补偿2以内
            if (tempPriceInt > 1000 && tempPriceInt <= 10000) {
                result = result.add(BigDecimal.valueOf(new Random().nextInt(20000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            }
            // 5位数 补偿2.5以内
            if (tempPriceInt > 10000 && tempPriceInt <= 100000) {
                result = result.add(BigDecimal.valueOf(new Random().nextInt(25000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            }
            if (random == 0) {
                return result.add(BigDecimal.valueOf(new Random().nextInt(150)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            } else {
                return result.subtract(BigDecimal.valueOf(new Random().nextInt(150)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
            }
        }
        // 如果金额差异低于10，随机金额上下浮动不超过1
        else if (priceAbs.compareTo(BigDecimal.TEN) <= 0) {
            // 生成浮动值
            randomNum = new Random().nextInt(10000);
        }
        // 如果金额差异低于20,随机金额上下浮动不超过2
        else if (priceAbs.compareTo(BigDecimal.TEN) > 0 && priceAbs.compareTo(BigDecimal.valueOf(20)) <= 0) {
            randomNum = new Random().nextInt(20000);
        }
        // 如果金额差异低于30,随机金额上下浮动不超过4
        else if (priceAbs.compareTo(BigDecimal.valueOf(20)) > 0 && priceAbs.compareTo(BigDecimal.valueOf(30)) <= 0) {
            randomNum = new Random().nextInt(40000);
        }
        // 如果金额差异低于50,随机金额上下浮动不超过5
        else if (priceAbs.compareTo(BigDecimal.valueOf(30)) > 0 && priceAbs.compareTo(BigDecimal.valueOf(50)) <= 0) {
            randomNum = new Random().nextInt(50000);
        }
        // 如果金额差异大于50,随机金额上下浮动不超过5
        else if (priceAbs.compareTo(BigDecimal.valueOf(50)) > 0) {
            randomNum = new Random().nextInt(50000);
        }

        // 生成随机值
        int baseRandow = priceAbs.divide(BigDecimal.valueOf(2), 4, BigDecimal.ROUND_HALF_UP).intValue();
//        System.out.println("随机数基数：" + baseRandow);
        baseNum = new Random().nextInt(baseRandow > 0 ? baseRandow : 1);
        if (isUp) {
            result = beforNowPrice.add(BigDecimal.valueOf(baseNum)).add(BigDecimal.valueOf(randomNum).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
        } else {
            result = beforNowPrice.add(BigDecimal.valueOf(baseNum)).subtract(BigDecimal.valueOf(randomNum).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP));
        }
        return result;
    }

}
