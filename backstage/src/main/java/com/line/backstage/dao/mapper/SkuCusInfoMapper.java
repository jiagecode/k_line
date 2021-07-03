package com.line.backstage.dao.mapper;
 
import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.SkuCusInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自选商品信息表(SkuCusInfo)表数据库访问层
 *
 * @author Zy
 * @since 2021-07-02 18:09:07
 */
public interface SkuCusInfoMapper extends TkBaseMapper<SkuCusInfo> {

    List<String> queryMyCusCode(@Param("userId") Integer loginUserId);
}