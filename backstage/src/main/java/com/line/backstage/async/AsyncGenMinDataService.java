package com.line.backstage.async;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
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
    private final double CON = 0.00001157;
    @Async
    public void autoGenDataMain(List<SkuInfoOhlcvVo> listOhlcv, String skuCode) {
        // 获取当天的基础数据
        JsonNode maindata = JsonUtils.toJsonNode(StrUtils.objToStr(redisUtil.get(skuCode + "_dd_" + DateUtil.getYesterdayEightStamp())));
        BigDecimal beforNowPrice;
        BigDecimal afterNowPrice;
        BigDecimal tempPiceNowPrice;
        SkuInfoOhlcvVo temp;
        // 临时字段
//        Double vf = maindata.get("volumeFrom").asDouble();
//        if (vf == null) {
//            vf = 86400000.0;
//        }
        Double vf = 86400000.0;
//        List<BigDecimal> bd = Lists.newArrayList();
        // 获取当前价格10%
        BigDecimal nowPrice = BigDecimal.valueOf(listOhlcv.get(0).getOpenPrice()).setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal nowPrice_10 = BigDecimal.valueOf(listOhlcv.get(0).getOpenPrice()).multiply(BigDecimal.valueOf(0.1)).setScale(4, BigDecimal.ROUND_HALF_UP);

        for (int i = 1; i < listOhlcv.size(); i++) {
            System.out.println("coin: " + listOhlcv.get(0).getSkuCode() + " All:" + listOhlcv.size() + " Now:" + i);
            SkuInfoOhlcvVo beforOne = listOhlcv.get(i - 1);
            SkuInfoOhlcvVo afterOne = listOhlcv.get(i);

            // 金额
            beforNowPrice = BigDecimal.valueOf(beforOne.getOpenPrice()).setScale(4, BigDecimal.ROUND_HALF_UP);
            afterNowPrice = BigDecimal.valueOf(afterOne.getOpenPrice()).setScale(4, BigDecimal.ROUND_HALF_UP);
            tempPiceNowPrice = afterNowPrice.subtract(beforNowPrice).setScale(4, BigDecimal.ROUND_HALF_UP);

//            if(i == 1){
//                min = tempPiceNowPrice;
//            }

//            System.out.println("第" + (i - 1) + "分钟，金额：" + beforNowPrice);
//            System.out.println("第" + i + "分钟，金额：" + afterNowPrice);
//            System.out.println("前后两分钟金额差值：" + tempPiceNowPrice);
//            bd.add(tempPiceNowPrice);

//            // 生成每秒一条数据
//            boolean isLastGen = false;
            for (int j = 1; j <= 60; j++) {
                // 权重比2 看涨
                int s = new Random().nextInt(3);
                // ==========================================================================

//                BigDecimal result = beforNowPrice;
//                BigDecimal bigDecimal = afterNowPrice;
//                BigDecimal subtract = result.subtract(bigDecimal);
//        List<BigDecimal> re = Lists.newArrayList();
//                for (int i = 0; i < 10; i++) {
//                    System.out.println("第" + i + "次subtract：" + tempPiceNowPrice);
//                    System.out.println("第" + i + "次随机数：" + s);
                    BigDecimal divide = tempPiceNowPrice.divide(BigDecimal.valueOf(new Random().nextInt(16) + 10), 5,BigDecimal.ROUND_HALF_UP);
//                    BigDecimal divide = tempPiceNowPrice.divide(BigDecimal.valueOf(60 -j + 1), 5,BigDecimal.ROUND_HALF_UP);

//            System.out.println("第" + i + "次计算结果：" + divide);
//            result = result.add(divide);
//                    System.out.println("abs前：" + divide);
//            divide = BigDecimal.valueOf(Math.abs(divide.doubleValue()));
//                    System.out.println("abs后：" + divide);
//                System.out.println();
//                System.out.println("==========================================================================");
//                System.out.println("第" + (i - 1) + "分，第" + j + "秒，差值：" + tempPiceNowPrice);
//                System.out.println("第" + (i - 1) + "分，第" + j + "秒，计算前金额：" + beforNowPrice);
                    if (tempPiceNowPrice.compareTo(BigDecimal.ZERO) < 0) {
//                        System.out.println("第" + (i - 1) + "分，第" + j + "秒，看涨金额：" + divide);
//                        BigDecimal tempAdd = BigDecimal.valueOf(Math.abs(divide.doubleValue()));
                        if(s == 2 || s == 0){
//                            System.out.println("结果：看涨" + divide);
                            beforNowPrice = beforNowPrice.add(divide);
                            tempPiceNowPrice = tempPiceNowPrice.add(divide);
                        } else {
//                            System.out.println("结果：看跌" + divide);
                            beforNowPrice = beforNowPrice.subtract(divide);
                            tempPiceNowPrice = tempPiceNowPrice.subtract(divide);
                        }
                    } else {
//                        System.out.println("第" + (i - 1) + "分，第" + j + "秒，看跌金额：" + divide);
                        if(s == 2 || s == 0){
//                            System.out.println("结果：看涨" + divide);
                            beforNowPrice = beforNowPrice.subtract(divide);
                            tempPiceNowPrice = tempPiceNowPrice.subtract(divide);
                        } else {
//                            System.out.println("结果：看跌" + divide);
                            beforNowPrice = beforNowPrice.add(divide);
                            tempPiceNowPrice = tempPiceNowPrice.add(divide);
                        }
                    }
//                System.out.println("第" + (i - 1) + "分，第" + j + "秒，计算后金额：" + beforNowPrice);
////                }
//                System.out.println("第" + (i - 1) + "分，第" + j + "秒金额：" + beforNowPrice + "，与下一分金额：" + afterNowPrice + "差值：" + afterNowPrice.subtract(beforNowPrice).setScale(4, BigDecimal.ROUND_HALF_UP));
//                System.out.println("==========================================================================");
//                System.out.println();

                // ==========================================================================
//                price = this.genPrice(beforNowPrice, afterNowPrice, tempPiceNowPrice, isLastGen).setScale(4);
                Long timeStamp = Long.parseLong(beforOne.getTimeStamp()) + j;
                temp = new SkuInfoOhlcvVo();
                temp.setOrderNum(j);
                double f = getFrom(vf);
                double p = beforNowPrice.doubleValue();
                if (p < f) {
                    p = f + (f - p) * CON * 100;
                }
                temp.setTimeStamp(String.valueOf(timeStamp));
                temp.setSkuCode(beforOne.getSkuCode());
                temp.setNowPrice(p);

                temp.setOpenPrice(maindata.get("openPrice").asDouble());
                temp.setClose(maindata.get("close").asDouble());
                temp.setMaxPrice(maindata.get("maxPrice").asDouble());
                temp.setMinPrice(maindata.get("minPrice").asDouble());
                temp.setVolumeTo(maindata.get("volumeTo").asDouble());
//                temp.setVolumeFrom(maindata.get("volumeFrom").asDouble());

                temp.setVolumeFrom(f);
                redisUtil.set(beforOne.getSkuCode() + "_ss_" + timeStamp, JsonUtils.toJsonString(temp), 0);
            }
        }
//        BigDecimal bigDec = null;
//        for (BigDecimal bigDecimal : bd) {
//            bigDec = bigDecimal.add(bigDecimal);
//        }
//        System.out.println("金额差值和：" + bigDec);
//        System.out.println("移动平均金额差值：" + bigDec.divide(BigDecimal.valueOf(bd.size())).setScale(4, BigDecimal.ROUND_HALF_UP));
    }

    private Double getFrom(double vf) {
//        1、根据格式
        double value = vf * CON;
        double rom = Math.random();
        if (value > 100.0 && value < 1000.0) {
            rom = rom * 100;
        } else if (value < 100.0) {
            rom = rom * 210;
        }
//        2、范围
        return value * (1 + rom);
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

    public static void main(String[] args) {
        BigDecimal result = BigDecimal.valueOf(1.5909);
        BigDecimal bigDecimal = BigDecimal.valueOf(1.5929);
        BigDecimal subtract = result.subtract(bigDecimal);
//        List<BigDecimal> re = Lists.newArrayList();
        for (int i = 0; i < 60; i++) {
            // 权重比2 看涨
            int s = new Random().nextInt(3);
//            System.out.println(BigDecimal.valueOf(-10).subtract(BigDecimal.valueOf(-5)));
            System.out.println("第" + i + "次subtract：" + subtract);
//            System.out.println("第" + i + "次随机数：" + s);
            int x = new Random().nextInt(16) + 10;
//            int x = (60 - i) / 4 + 1;
            System.out.println("第" + i + "次商：" + x);
            BigDecimal divide = subtract.divide(BigDecimal.valueOf(x), 4, BigDecimal.ROUND_HALF_UP);

            System.out.println("随机" + s + "第" + i + "差值倍数：" + divide);
            if (subtract.compareTo(BigDecimal.ZERO) < 0) {
                if(s == 2 || s == 0){
                    result = result.subtract(divide);
                    subtract = subtract.subtract(divide);
                } else {
                    result = result.add(divide);
                    subtract = subtract.add(divide);
                }
            } else {
                if(s == 2 || s == 0){
                    result = result.subtract(divide);
                    subtract = subtract.subtract(divide);
                } else {
                    result = result.add(divide);
                    subtract = subtract.add(divide);
                }
            }
        }
        // 权重比2 看涨
//        int s = new Random().nextInt(3);
//        for (int i = 10; i > 0; i--) {
//            System.out.println(subtract);
//            System.out.println("第" + i + "次计算结果：" + result);
//            subtract = result.subtract(bigDecimal);
//            BigDecimal divide = subtract.divide(BigDecimal.valueOf(new Random().nextInt(2) + 1), 4, BigDecimal.ROUND_HALF_UP);
//            if (subtract.compareTo(BigDecimal.ZERO) < 0) {
//                if(s == 2 || s == 0){
//                    result = result.add(divide);
//                } else {
//                    result = result.subtract(divide);
//                }
//            } else {
//                if(s == 2 || s == 0){
//                    result = result.subtract(divide);
//                } else {
//                    result = result.add(divide);
//                }
//
//            }
//        }
        System.out.println("最终：" + result);
    }

}
