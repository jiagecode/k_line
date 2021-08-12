package com.line.backstage.async;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.StrUtils;
import com.line.backstage.vo.SkuInfoOhlcvVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

@Slf4j
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
        log.info("预设：{}", isWin);
        log.info("投资方向：{}", investType);

        // 订单周期
        long cycle = Long.parseLong(orderCycle);
        // 订单半周期
        long forTimes = (long) Math.floor(cycle / 2);

        // 下单时间
        long orderTime = Long.parseLong(inDate) - 86400;
        // 结算时间
        long targetTime = orderTime + cycle;
        // 开始生成时间
        long startTime = targetTime - forTimes;
        // 结束生成时间
        long endTime = targetTime + forTimes;

        // 根据isWin(true/false)和investType(1up2down)判断生成方向
        boolean isGenUp = true;
        if (investType.equals("1")) {
            if ("true".equals(isWin)) {
                isGenUp = true;
            } else if ("2".equals(isWin)) {
                isGenUp = false;
            }
        } else if (investType.equals("2")) {
            if ("true".equals(isWin)) {
                isGenUp = false;
            } else if ("2".equals(isWin)) {
                isGenUp = true;
            }
        }

        // 生成关键点数据
        BigDecimal targetPrice = this.genTargetPrice(userId, skuCode, targetTime, inPoint, isGenUp);
        log.info("生成时间范围：{}-{}", startTime, endTime);
        log.info("结算时间：{}", targetTime);
        log.info("下单金额：{}", inPoint);
        log.info("结算金额：{}", targetPrice);
        log.info("结算前后生成数：{}", forTimes);
        // 生成关键点前的数据
        this.genOtherPriceA(userId, skuCode, targetTime, startTime, targetPrice, forTimes);
        // 生成关键点后的数据
        this.genOtherPriceB(userId, skuCode, targetTime, endTime, targetPrice, forTimes);
    }

    /**
     * 生成关键点前数据
     *
     * @param userId
     * @param skuCode
     * @param targetTime
     * @param startTime
     * @param targetPrice
     */
    private void genOtherPriceA(String userId, String skuCode, long targetTime, long startTime, BigDecimal targetPrice, long forTimes) {
        // 获取第一条原数据
        SkuInfoOhlcvVo tempFirstVo = JsonUtils.parse(StrUtils.objToStr(redisUtil.get(skuCode + "_ss_" + startTime)), SkuInfoOhlcvVo.class);
        BigDecimal firstPrice = BigDecimal.valueOf(tempFirstVo.getNowPrice());
        // 平均变化值 动态
        BigDecimal avgNumPrice = (targetPrice.subtract(firstPrice)).divide(BigDecimal.valueOf(forTimes), 4, BigDecimal.ROUND_HALF_UP);

        SkuInfoOhlcvVo tempVo = null;
        BigDecimal genNum = BigDecimal.ZERO;
        BigDecimal tempPrice = firstPrice;
        Random random = new Random();
        for (long i = startTime; i < targetTime; i++) {
            // 生成一个随机值加到平均变化值 0.01到0.1
            genNum = BigDecimal.valueOf(random.nextInt(900) + 101).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal nowAvgNumPrice;
            if (avgNumPrice.compareTo(BigDecimal.ZERO) > 0) {
                nowAvgNumPrice = avgNumPrice.add(genNum);
            } else {
                nowAvgNumPrice = avgNumPrice.subtract(genNum);
            }

            // 获取原数据
            tempVo = JsonUtils.parse(StrUtils.objToStr(redisUtil.get(skuCode + "_ss_" + i)), SkuInfoOhlcvVo.class);
            // 生成30%的反向
            if (random.nextInt(5) + 1 == 3) {
                tempPrice = tempPrice.subtract(nowAvgNumPrice);
            } else {
                tempPrice = tempPrice.add(nowAvgNumPrice);
            }

            tempVo.setNowPrice(tempPrice.doubleValue());

            if (i % 60 == 0) {
                // 整数分钟，需要同时生成一条分钟数据
                redisUtil.set(userId + "_" + skuCode + "_mm_" + i, JsonUtils.toJsonString(tempVo), redisUtil.getOverdue());
            }
            redisUtil.set(userId + "_" + skuCode + "_ss_" + i, JsonUtils.toJsonString(tempVo), redisUtil.getOverdue());

            // 剩余次数
            long times = targetTime-i-1;
            // 重新计算平均变化值
            if(times > 0){
                avgNumPrice = (targetPrice.subtract(tempPrice)).divide(BigDecimal.valueOf(times), 4, BigDecimal.ROUND_HALF_UP);
            }

            log.info("A-{}: 结算值({}) 对比值({}) 平均变化值({}) 随机值({}) 变化值({}) 生成结果({}) 剩余次数({})", i,targetPrice, firstPrice, avgNumPrice, genNum, nowAvgNumPrice, tempPrice, times);
        }
    }

    /**
     * 生成关键点前数据
     *
     * @param userId
     * @param skuCode
     * @param targetTime
     * @param endTime
     * @param targetPrice
     */
    private void genOtherPriceB(String userId, String skuCode, long targetTime, long endTime, BigDecimal targetPrice, long forTimes) {
        // 获取第一条原数据
        SkuInfoOhlcvVo tempEndVo = JsonUtils.parse(StrUtils.objToStr(redisUtil.get(skuCode + "_ss_" + endTime)), SkuInfoOhlcvVo.class);
        BigDecimal endPrice = BigDecimal.valueOf(tempEndVo.getNowPrice());
        // 平均变化值 动态
        BigDecimal avgNumPrice = (targetPrice.subtract(endPrice)).divide(BigDecimal.valueOf(forTimes), 4, BigDecimal.ROUND_HALF_UP);

        SkuInfoOhlcvVo tempVo = null;
        BigDecimal genNum = BigDecimal.ZERO;
        BigDecimal tempPrice = endPrice;
        Random random = new Random();
        // 从后往前生成
        for (long i = endTime; i > targetTime; i--) {
            // 生成一个随机值加到平均变化值 0.01到0.1
            genNum = BigDecimal.valueOf(random.nextInt(900) + 101).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal nowAvgNumPrice;
            if (avgNumPrice.compareTo(BigDecimal.ZERO) > 0) {
                nowAvgNumPrice = avgNumPrice.add(genNum);
            } else {
                nowAvgNumPrice = avgNumPrice.subtract(genNum);
            }

            // 获取原数据
            tempVo = JsonUtils.parse(StrUtils.objToStr(redisUtil.get(skuCode + "_ss_" + i)), SkuInfoOhlcvVo.class);
            // 生成20%的反向
            if (random.nextInt(5) + 1 == 3) {
                tempPrice = tempPrice.subtract(nowAvgNumPrice);
            } else {
                tempPrice = tempPrice.add(nowAvgNumPrice);
            }

            tempVo.setNowPrice(tempPrice.doubleValue());

            if (i % 60 == 0) {
                // 整数分钟，需要同时生成一条分钟数据
                redisUtil.set(userId + "_" + skuCode + "_mm_" + i, JsonUtils.toJsonString(tempVo), redisUtil.getOverdue());
            }
            redisUtil.set(userId + "_" + skuCode + "_ss_" + i, JsonUtils.toJsonString(tempVo), redisUtil.getOverdue());

            // 剩余次数
            long times = i - targetTime;
            // 重新计算平均变化值
            if(times > 0){
                avgNumPrice = (targetPrice.subtract(tempPrice)).divide(BigDecimal.valueOf(times), 4, BigDecimal.ROUND_HALF_UP);
            }

            log.info("B-{}: 结算值({}) 对比值({}) 平均变化值({}) 随机值({}) 变化值({}) 生成结果({}) 剩余次数({})", i,targetPrice, endPrice, avgNumPrice, genNum, nowAvgNumPrice, tempPrice, times);
        }
    }


    /**
     * 返回根据下单点位和输赢生成的结算点位金额
     *
     * @param inPoint
     * @param isGenUp
     * @return
     */
    public BigDecimal genTargetPrice(String userId, String skuCode, long targetTime, String inPoint, boolean isGenUp) {
        BigDecimal inPrice = new BigDecimal(inPoint);
        Random random = new Random();
        BigDecimal genNum = BigDecimal.ZERO;
        // 大于0 小于1  0.1
        if (inPrice.compareTo(BigDecimal.ZERO) >= 0 && inPrice.compareTo(BigDecimal.ONE) < 0) {
            genNum = BigDecimal.valueOf(random.nextInt(1000) + 1).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 1位数 0.2以内 大于0.1
        if (inPrice.compareTo(BigDecimal.ONE) >= 0 && inPrice.compareTo(BigDecimal.TEN) < 0) {
            genNum = BigDecimal.valueOf(random.nextInt(1000) + 1001).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 2位数 1以内 大于0.2
        if (inPrice.compareTo(BigDecimal.TEN) >= 0 && inPrice.compareTo(BigDecimal.valueOf(100L)) < 0) {
            genNum = BigDecimal.valueOf(random.nextInt(8000) + 2001).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 3位数 1.5以内 大于1
        if (inPrice.compareTo(BigDecimal.valueOf(100L)) >= 0 && inPrice.compareTo(BigDecimal.valueOf(1000L)) < 0) {
            genNum = BigDecimal.valueOf(random.nextInt(5000) + 10001).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 4位数 2以内 大于1.5
        if (inPrice.compareTo(BigDecimal.valueOf(1000L)) >= 0 && inPrice.compareTo(BigDecimal.valueOf(10000L)) < 0) {
            genNum = BigDecimal.valueOf(random.nextInt(5000) + 15001).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 5位数 2.5以内 大于2
        if (inPrice.compareTo(BigDecimal.valueOf(10000L)) >= 0 && inPrice.compareTo(BigDecimal.valueOf(100000L)) < 0) {
            genNum = BigDecimal.valueOf(random.nextInt(5000) + 20001).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 6位数 3.5以内 大于2.5
        if (inPrice.compareTo(BigDecimal.valueOf(100000L)) >= 0 && inPrice.compareTo(BigDecimal.valueOf(1000000L)) < 0) {
            genNum = BigDecimal.valueOf(random.nextInt(5000) + 25001).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }

        if (isGenUp) {
            // 涨 +
            inPrice = inPrice.add(genNum);
        } else {
            // 跌 -
            inPrice = inPrice.subtract(genNum);
        }

        // 保存
        Object targetNowData = redisUtil.get(skuCode + "_ss_" + targetTime);
        SkuInfoOhlcvVo targetVo = JsonUtils.parse(StrUtils.objToStr(targetNowData), SkuInfoOhlcvVo.class);
        targetVo.setNowPrice(inPrice.doubleValue());
        if (targetTime % 60 == 0) {
            // 整数分钟，需要同时生成一条分钟数据
            redisUtil.set(userId + "_" + skuCode + "_mm_" + targetTime, JsonUtils.toJsonString(targetVo), redisUtil.getOverdue());
        }
        redisUtil.set(userId + "_" + skuCode + "_ss_" + targetTime, JsonUtils.toJsonString(targetVo), redisUtil.getOverdue());
        return inPrice;
    }

}
