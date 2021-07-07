package com.line.backstage.service.impl;

import com.line.backstage.service.SysDiyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DWanBean
 * @Date 2021/7/7 10:02
 * @Version 1.0
 * @Desc
 */
@Service("sysDiyService")
public class SysDiyServiceImpl implements SysDiyService {

    @Override
    public Map<String, Object> querySpecialUserInfo(Integer loginUserId,String diyUserId) {
        return null;
    }

    @Override
    public Integer updateSpecialUserInfo(Integer loginUserId, Map<String, Object> map) {
        return null;
    }

    @Override
    public List<Map<String, Object>> querySkuDataList(Integer loginUserId) {
        return null;
    }

    @Override
    public Map<String, Object> createImitateData(Integer loginUserId, Map<String, Object> map) {
        return null;
    }

    @Override
    public Integer editDiyRecordByType(Integer loginUserId, Map<String, Object> map) {
        return null;
    }
}
