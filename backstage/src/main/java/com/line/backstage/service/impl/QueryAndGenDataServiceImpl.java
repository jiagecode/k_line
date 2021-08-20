package com.line.backstage.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.line.backstage.async.AsyncGenMinDataService;
import com.line.backstage.redis.RedisUtil;
import com.line.backstage.service.QueryAndGenDataService;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.HttpClientUtil;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.utils.StrUtils;
import com.line.backstage.vo.SkuInfoOhlcvVo;
import com.line.backstage.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class QueryAndGenDataServiceImpl implements QueryAndGenDataService {

    private String API_KEY = "b55818528ec171d67d5aeee0f0ecb624a3a37e06e5491e9cd9bbb7af92ed8a37";
    private String URL = "https://min-api.cryptocompare.com/data/v2/histoday";
    private String URL2 = "https://min-api.cryptocompare.com/data/v2/histominute";
    private String URL3 = "https://min-api.cryptocompare.com/data/v2/histohour";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AsyncGenMinDataService asyncGenMinDataService;

    @Override
    public void queryAndGen() {
        // 时间戳
        String stamp = DateUtil.getTodayZeroStamp();
        log.info("windows时间戳【1627574400】，centos时间戳【{}】", stamp);
        if (redisUtil.setIsMember("toTs", stamp)) {
            log.info("时间戳：【{}】已处理...", stamp);
            return;
        }
        // 记录正在处理的查询时间戳
        redisUtil.setAdd("toTs", stamp);
        try {
            String coinObjList = StrUtils.objToStr(redisUtil.get("CoinObjList"));
            // 校验列表，转成对象
            List<SkuInfoVo> skuList = JsonUtils.toList(coinObjList, SkuInfoVo.class);
            if (CollectionUtils.isEmpty(skuList)) {
                throw new RuntimeException("商品列表为空！");
            }
            // 查询日线
            this.getDayLineData(skuList);
            // 查询小时线
            this.getHourData(skuList);
            // 查询分钟线
            this.getMinLinData(skuList, stamp);
        } catch (Exception e) {
            // 异常时将此次时间戳记录删除
            redisUtil.setRemove("toTs", stamp);
            throw new RuntimeException("查询数据失败：" + e.getMessage());
        }
    }

    /**
     * 每小时生成币种秒线
     */
    @Override
    public void generateCurrencySeconds() {
        log.info("每小时生成币种秒线:【{}】", DateUtil.getYesterdayFullMinute());
        // 时间戳
        String stamp = DateUtil.getTodayZeroStamp();
        String coinObjList = StrUtils.objToStr(redisUtil.get("CoinObjList"));
        // 校验列表，转成对象
        List<SkuInfoVo> skuList = JsonUtils.toList(coinObjList, SkuInfoVo.class);
        if (CollectionUtils.isEmpty(skuList)) {
            throw new RuntimeException("商品列表为空！");
        }
        // 查询分钟线
        try {
            this.getMinLinData(skuList, stamp);
        } catch (Exception e) {
            log.error("每小时生成币种秒线-查询数据失败:【{}】",  e.getMessage());
            throw new RuntimeException("每小时生成币种秒线-查询数据失败：" + e.getMessage());
        }
    }

    /**
     * 查询日线
     *
     * @param skuList
     */
    private void getDayLineData(List<SkuInfoVo> skuList) throws Exception {
        Map<String, Object> params = new HashMap<>();
        for (SkuInfoVo sku : skuList) {
            params.put("fsym", sku.getSkuCode());
            params.put("tsym", "USD");
            // 30 * 6 = 180
            params.put("limit", "180");
            params.put("toTs", DateUtil.getTodayZeroStamp());
            params.put("api_key", API_KEY);
            // 查询数据
            String coinsDataFull = HttpClientUtil.getInstance().doGet(URL, params, null);
            // 解析
            List<SkuInfoOhlcvVo> list = getSkuInfoList(sku.getSkuCode(), coinsDataFull);
            // 缓存
            for (SkuInfoOhlcvVo ohlcv : list) {
                redisUtil.set(ohlcv.getSkuCode() + "_dd_" + ohlcv.getTimeStamp(), JsonUtils.toJsonString(ohlcv), redisUtil.getOverdue());
                log.info("Day:{} Price: {}" ,ohlcv.getTimeStamp(), ohlcv.getOpenPrice());
            }
            log.info("Day数据：{} 条" ,list.size());
            params.clear();
        }
    }

    /**
     * 查询小时数据
     *
     * @param skuList
     */
    private void getHourData(List<SkuInfoVo> skuList) throws Exception {
        Map<String, Object> params = new HashMap<>();
        for (SkuInfoVo sku : skuList) {
            params.put("fsym", sku.getSkuCode());
            params.put("tsym", "USD");
            // 24 * 6 = 144
            params.put("limit", "144");
            params.put("toTs", DateUtil.getYesterdayHourStamp());
            params.put("api_key", API_KEY);
            // 查询数据
            String coinsDataFull = HttpClientUtil.getInstance().doGet(URL3, params, null);
            // 解析
            List<SkuInfoOhlcvVo> list = getSkuInfoList(sku.getSkuCode(), coinsDataFull);
            // 缓存
            for (SkuInfoOhlcvVo ohlcv : list) {
                redisUtil.set(ohlcv.getSkuCode() + "_hh_" + ohlcv.getTimeStamp(), JsonUtils.toJsonString(ohlcv), redisUtil.getOverdue());
            }
            params.clear();
        }
    }

    /**
     * 查询分钟线
     *
     * @param skuList
     */
    private void getMinLinData(List<SkuInfoVo> skuList, String stamp) throws Exception {
        Map<String, Object> params = new HashMap<>();
        for (SkuInfoVo sku : skuList) {
            params.put("fsym", sku.getSkuCode());
            params.put("tsym", "USD");
            params.put("limit", "1440");
//            params.put("limit", "3");
            params.put("toTs", stamp);
            params.put("api_key", API_KEY);
            // 查询数据
            String coinsDataFull = HttpClientUtil.getInstance().doGet(URL2, params, null);
            // 解析
            List<SkuInfoOhlcvVo> list = getSkuInfoList(sku.getSkuCode(), coinsDataFull);
            // 缓存
            for (SkuInfoOhlcvVo ohlcv : list) {
                redisUtil.set(ohlcv.getSkuCode() + "_mm_" + ohlcv.getTimeStamp(), JsonUtils.toJsonString(ohlcv), redisUtil.getOverdue());
            }

            // 删除昨天当前时间3分钟前的数据
            String beforeTime = DateUtil.getYesterdayFullMinute();
            System.err.println(beforeTime);
            ArrayList<SkuInfoOhlcvVo> removeList = Lists.newArrayList();
            for (SkuInfoOhlcvVo skuInfoOhlcvVo : list) {
                if (Objects.equals(skuInfoOhlcvVo.getTimeStamp(), beforeTime)) {
                    // 找到-昨天当前3分钟的数据
                    break;
                } else {
                    removeList.add(skuInfoOhlcvVo);
                }
            }

            // 删除-昨天当前3分钟的数据
            list.removeAll(removeList);

            // 定义63分钟的分钟线
            ArrayList<SkuInfoOhlcvVo> list_63 = Lists.newArrayList();
            // 取出前63分钟的数据
            int hour_1 = 63;
            if(list.size() >= hour_1){
                for (int i = 0; i < hour_1; i++) {
                    list_63.add(list.get(i));
                }
            } else {
                list_63.addAll(list);
            }
            // 调用异步线程生成分钟数据
            asyncGenMinDataService.autoGenDataMain(list_63, sku.getSkuCode());
            params.clear();
        }
    }

    /**
     * 解析
     *
     * @param code
     * @param data
     * @return
     */
    private List<SkuInfoOhlcvVo> getSkuInfoList(String code, String data) {
//        log.info("币种分钟线数据【{}】", data);
        if (StringUtils.isEmpty(data)) {
            throw new RuntimeException("数据不存在");
        }
        List<SkuInfoOhlcvVo> result = new ArrayList<>();
        SkuInfoOhlcvVo temp;
        JsonNode coinNode = JsonUtils.toJsonNode(data);
        String response = coinNode.get("Response").asText("");
        if (!"Success".equals(response)) {
            throw new RuntimeException(coinNode.get("Message").asText(""));
        }
        if (coinNode.hasNonNull("Data")) {
            JsonNode data1 = coinNode.get("Data");
            JsonNode data2 = data1.get("Data");
            // 循环获取
            if (data2.isArray()) {
                Iterator<JsonNode> it = data2.iterator();
                while (it.hasNext()) {
                    temp = new SkuInfoOhlcvVo();
                    JsonNode node = it.next();
                    String time = node.get("time").asText("");
                    String high = node.get("high").asText("");
                    String low = node.get("low").asText("");
                    String open = node.get("open").asText("");
                    String volumefrom = node.get("volumefrom").asText("");
                    String volumeto = node.get("volumeto").asText("");
                    String close = node.get("close").asText("");

                    temp.setSkuCode(code);
                    temp.setTimeStamp(time);
                    temp.setMaxPrice(Double.valueOf(high));
                    temp.setMinPrice(Double.valueOf(low));
                    temp.setOpenPrice(Double.valueOf(open));
                    temp.setVolumeFrom(Double.valueOf(volumefrom));
                    temp.setVolumeTo(Double.valueOf(volumeto));
                    temp.setClose(Double.valueOf(close));
                    result.add(temp);
                }
            } else {
                throw new RuntimeException("解析出现错误1");
            }
        } else {
            throw new RuntimeException("解析出现错误2");
        }
        return result;
    }
}
