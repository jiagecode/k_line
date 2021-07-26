package com.line.backstage.service.impl;

import com.line.backstage.redis.RedisUtil;
import com.line.backstage.service.KLineDataService;
import com.line.backstage.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KLineDataServiceImpl implements KLineDataService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 30天
     *
     * @param loginUserId
     * @param code
     * @return
     */
    @Override
    public List<Object> dayList(Integer loginUserId, String code) {
        // 开始时间
        long nowStamp = Long.parseLong(DateUtil.getYesterdayEightStamp());
        // 开始时间
        long startStamp = Long.parseLong(DateUtil.getYesterdayEightStamp()) - (86400 * 24);

        List<Object> result = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        for (long i = startStamp; i < nowStamp; i += 86400) {
            sbKey.append(code).append("_dd_").append(i);
            result.add(redisUtil.get(sbKey.toString()));
            sbKey.delete(0, sbKey.length());
        }
        return result;
    }

    /**
     * 24小时
     *
     * @param loginUserId
     * @param code
     * @return
     */
    @Override
    public List<Object> hourList(Integer loginUserId, String code) {
        // 结束时间 当前
        long nowStamp = Long.parseLong(DateUtil.getYesterdayHourStamp());
        // 开始时间
        long startStamp = nowStamp - (3600 * 24);

        List<Object> result = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        for (long i = startStamp; i <= nowStamp; i += 3600) {
            sbKey.append(code).append("_hh_").append(i);
            result.add(redisUtil.get(sbKey.toString()));
            sbKey.delete(0, sbKey.length());
        }

        return result;

    }

    /**
     * 60分钟
     *
     * @param loginUserId
     * @param code
     * @return
     */
    @Override
    public List<Object> minList(Integer loginUserId, String code) {
        // 结束时间 当前
        long nowStamp = Long.parseLong(DateUtil.getYesterdayMinuteStamp());
        // 开始时间
        long startStamp = nowStamp - 3600;

        List<Object> result = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        for (long i = startStamp; i <= nowStamp; i += 60) {
            sbKey.append(code).append("_mm_").append(i);
            result.add(redisUtil.get(sbKey.toString()));
            sbKey.delete(0, sbKey.length());
        }

        return result;
    }

    /**
     * 600秒
     *
     * @param loginUserId
     * @param code
     * @return
     */
    @Override
    public List<Object> ssList(Integer loginUserId, String code) {
        // 结束时间 当前
        long nowStamp = Long.parseLong(DateUtil.getYesterdayStamp());
        // 开始时间
        long startStamp = nowStamp - 600;

        List<Object> result = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        for (long i = startStamp; i <= nowStamp; i++) {
            sbKey.append(code).append("_ss_").append(i);
            result.add(redisUtil.get(sbKey.toString()));
            sbKey.delete(0, sbKey.length());
        }

        return result;
    }
}
