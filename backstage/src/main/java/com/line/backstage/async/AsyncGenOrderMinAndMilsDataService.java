package com.line.backstage.async;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

        // 生成分数据
        this.genMinData(userId, isWin, investType, inPoint, outPoint, inDate, outDate, orderCycle, skuCode);

        // 生成秒数据
        this.genMilsData(userId, isWin, investType, inPoint, outPoint, inDate, outDate, orderCycle, skuCode);
    }

    /**
     * 生成分数据
     */
    private void genMinData(String userId, String isWin, String investType, String inPoint, String outPoint, String inDate, String outDate, String orderCycle, String skuCode) {
        // todo
    }

    /**
     * 生成秒数据
     */
    private void genMilsData(String userId, String isWin, String investType, String inPoint, String outPoint, String inDate, String outDate, String orderCycle, String skuCode) {
        // todo

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

    /**
     * 根据差距金额返回生成金额
     *
     * @return
     */
    private BigDecimal genOtherPrice(BigDecimal beforNowPrice, BigDecimal afterNowPrice, BigDecimal price) {
        BigDecimal result;
        BigDecimal temp = BigDecimal.ZERO;
        int tempPrice = price.abs().intValue();
        if (tempPrice <= 0) {
            tempPrice = new Random().nextInt(10) + 1;
        }

        // 差距太小时，根据数据大小补偿
        int tempPriceInt = beforNowPrice.intValue();
        // 1位数 补偿0.2以内
        if (tempPriceInt > 1 && tempPriceInt <= 10) {
            temp = BigDecimal.valueOf(new Random().nextInt(2000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 2位数 补偿0.5以内
        if (tempPriceInt > 10 && tempPriceInt <= 100) {
            temp = BigDecimal.valueOf(new Random().nextInt(5000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 3位数 补偿1.5以内
        if (tempPriceInt > 100 && tempPriceInt <= 1000) {
            temp = BigDecimal.valueOf(new Random().nextInt(15000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 4位数 补偿2以内
        if (tempPriceInt > 1000 && tempPriceInt <= 10000) {
            temp = BigDecimal.valueOf(new Random().nextInt(20000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }
        // 5位数 补偿2.5以内
        if (tempPriceInt > 10000 && tempPriceInt <= 100000) {
            temp = BigDecimal.valueOf(new Random().nextInt(25000)).divide(BigDecimal.valueOf(10000), 4, BigDecimal.ROUND_HALF_UP);
        }

        // 随机数
        int random = new Random().nextInt(tempPrice);
        int random2 = new Random().nextInt(2);
        if (random2 == 1) {
            result = beforNowPrice.add(BigDecimal.valueOf(random));
        } else {
            result = beforNowPrice.subtract(BigDecimal.valueOf(random));
        }
        if (random2 == 0) {
            return result.add(temp);
        } else {
            return result.subtract(temp);
        }
    }

}
