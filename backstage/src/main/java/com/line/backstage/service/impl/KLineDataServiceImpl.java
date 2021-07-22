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

    @Override
    public List<Object> dayList(Integer loginUserId, String code) {
        // 开始时间
        long startStamp = Long.parseLong(DateUtil.getYesterdayZeroStamp());

        List<Object> result = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        for (int i = 0; i <= 7; i++) {
            sbKey.append(code).append("_dd_").append(startStamp - (i * 86400));
            result.add(redisUtil.get(sbKey.toString()));
            sbKey.delete(0, sbKey.length());
        }
        return result;
    }

    @Override
    public List<Object> minList(Integer loginUserId, String code) {
        // 结束时间 当前
        long nowStamp = Long.parseLong(DateUtil.getYesterdayMinuteStamp());
        // 开始时间
        long startStamp = nowStamp-3600;

        List<Object> result = new ArrayList<>();
        StringBuilder sbKey = new StringBuilder();
        for (long i = startStamp; i <= nowStamp; i += 60) {
            sbKey.append(code).append("_mm_").append(i);
            result.add(redisUtil.get(sbKey.toString()));
            sbKey.delete(0, sbKey.length());
        }

        return result;
    }

    @Override
    public List<Object> ssList(Integer loginUserId, String code) {
        // 结束时间 当前
        long nowStamp = Long.parseLong(DateUtil.getYesterdayStamp());
        // 开始时间
        long startStamp = nowStamp-3600;

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
