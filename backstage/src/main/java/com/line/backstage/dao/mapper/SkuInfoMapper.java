package com.line.backstage.dao.mapper;

import com.line.backstage.bases.TkBaseMapper;
import com.line.backstage.entity.SkuInfo;
import com.line.backstage.vo.SkuInfoVo;

import java.util.List;

/**
 * 商品信息表(SkuInfo)表数据库访问层
 *
 * @author jack
 * @since 2000-07-01 11:35:00
 */
public interface SkuInfoMapper extends TkBaseMapper<SkuInfo> {

    List<SkuInfoVo> queryListAll();

    void updetaBySkuInfoVo(SkuInfoVo skuInfoVo);

}