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
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

@Service
public class AsyncGenOrderMinAndMilsDataService {

    @Autowired
    private RedisUtil redisUtil;

    @Async
    public void autoGenDataMain(Map<String, Object> paramMap) {
        // 订单id
        String orderId = StrUtils.objToStr(paramMap.get("orderId"));
        // 用户id
        String userId = StrUtils.objToStr(paramMap.get("userId"));
        // 是否胜利
        String isWin = StrUtils.objToStr(paramMap.get("isWin"));
        // 投资方向 涨跌
        String investType = StrUtils.objToStr(paramMap.get("investType"));
        // 下单单价
        String inPoint = StrUtils.objToStr(paramMap.get("inPoint"));
        // 结算单价
        String outPoint = StrUtils.objToStr(paramMap.get("outPoint"));
        // 下单时间
        String inDate = StrUtils.objToStr(paramMap.get("inDate"));
        // 结算时间
        String outDate = StrUtils.objToStr(paramMap.get("outDate"));
        // 周期
        String orderCycle = StrUtils.objToStr(paramMap.get("orderCycle"));
        // 货品Code
        String skuCode = StrUtils.objToStr(paramMap.get("skuCode"));

        // 生成秒数据
        this.genMilsData(userId, isWin, investType, inPoint, outPoint, inDate, outDate, orderCycle, skuCode);
    }

    /**
     * 生成秒数据
     */
    private void genMilsData(String userId, String isWin, String investType, String inPoint, String outPoint, String inDate, String outDate, String orderCycle, String skuCode) {
        // 获取开始时间 取当前分钟的起始时间
        long startTime = Long.parseLong(inDate) + 1;
        // 结束时间
        long endTime = startTime + Long.parseLong(orderCycle) - 1;

        // 获取当天的基础数据
        JsonNode maindata = JsonUtils.toJsonNode(StrUtils.objToStr(redisUtil.get(skuCode + "_dd_" + DateUtil.getYesterdayEightStamp())));

        // 根据isWin(1t2f)和investType(1up2down)判断生成方向
        boolean isGenUp = true;
        if (investType.equals("1")) {
            if ("1".equals(isWin)) {
                isGenUp = true;
            } else if ("2".equals(isWin)) {
                isGenUp = false;
            }
        } else if (investType.equals("2")) {
            if ("1".equals(isWin)) {
                isGenUp = false;
            } else if ("2".equals(isWin)) {
                isGenUp = true;
            }
        }

        // 上次生成的单价，第一次时获取上一秒的数据获取单价
        JsonNode beforTimeData = null;
        // 循环10次获取数据，如果都没查到，报错
        for (int i = 0; i < 10; i++) {
            Object data = redisUtil.get(skuCode + "_ss_" + (startTime - 1));
            if (ObjectUtils.isEmpty(data)) {
                continue;
            }
            beforTimeData = JsonUtils.toJsonNode(JsonUtils.toJsonString(data));
        }
        if (ObjectUtils.isEmpty(beforTimeData)) {
            throw new RuntimeException("生成用户数据时查询出错！");
        }
        BigDecimal genPrice = new BigDecimal(beforTimeData.get("nowPrice").asText(""));

        // 循环生成 每秒
        SkuInfoOhlcvVo skuInfoOhlcvVo;
        int orderNum = 0;
        boolean isLastGen = false;
        BigDecimal outPointPrice = new BigDecimal(outPoint);
        for (long i = startTime; i <= endTime; i++) {
            skuInfoOhlcvVo = new SkuInfoOhlcvVo();
            // 是否关键数据 最后3条
            if (endTime - i <= 3) {
                isLastGen = true;
            }
            // 生成价格
            genPrice = this.genNowPrice(genPrice, outPoint, isGenUp, isLastGen);

            // 最后一条关键数据时判断
            if (endTime - i == 0) {
                if (isGenUp) {
                    // 生成涨方向 genPrice必须>outPoint
                    while (genPrice.compareTo(outPointPrice) <= 0) {
                        genPrice = this.genNowPrice(genPrice, outPoint, true, true);
                    }
                } else {
                    // 生成跌方向 genPrice必须<outPoint
                    while (genPrice.compareTo(outPointPrice) >= 0) {
                        genPrice = this.genNowPrice(genPrice, outPoint, false, true);
                    }
                }
            }

            skuInfoOhlcvVo.setOrderNum(orderNum);
            skuInfoOhlcvVo.setSkuCode(skuCode);
            skuInfoOhlcvVo.setTimeStamp(String.valueOf(i));
            skuInfoOhlcvVo.setNowPrice(genPrice.doubleValue());
            skuInfoOhlcvVo.setOpenPrice(maindata.get("openPrice").asDouble());
            skuInfoOhlcvVo.setClose(maindata.get("close").asDouble());
            skuInfoOhlcvVo.setMaxPrice(maindata.get("maxPrice").asDouble());
            skuInfoOhlcvVo.setMinPrice(maindata.get("minPrice").asDouble());
            skuInfoOhlcvVo.setVolumeTo(maindata.get("volumeTo").asDouble());
            skuInfoOhlcvVo.setVolumeFrom(maindata.get("volumeFrom").asDouble());

            long tempTime = i % 60;
            if (tempTime == 0) {
                // 整数分钟，需要同时生成一条分钟数据
                redisUtil.set(userId + "_" + skuCode + "_mm_" + i, JsonUtils.toJsonString(skuInfoOhlcvVo), 0);
            }

            redisUtil.set(userId + "_" + skuCode + "_ss_" + i, JsonUtils.toJsonString(skuInfoOhlcvVo), 0);
            orderNum++;
        }

    }

    /**
     * 生成价格
     *
     * @param genPrice
     * @param isLastGen
     * @return
     */
    private BigDecimal genNowPrice(BigDecimal genPrice, String outPoint, boolean isGenUp, boolean isLastGen) {
        // 根据位数生成随机数
        int tempPriceInt = genPrice.intValue();
        BigDecimal result = genPrice;
        BigDecimal priceTemp1 = genPrice;
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
            if (isGenUp) {
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
                if (isGenUp) {
                    result = result.add(priceTemp1);
                } else {
                    result = result.subtract(priceTemp1);
                }
            }
        } else {
            if (isGenUp) {
                result = result.add(priceTemp1);
            } else {
                result = result.subtract(priceTemp1);
            }
        }

        return result;
    }

}
