package com.line.backstage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkuInfoVo implements Serializable {

    /**
     * 临时字段 判断是上下半分钟
     */
    private Integer timeTag;
    /**
     * 临时字段 用于排序的字段
     */
    private Integer orderNum;

    /**
     * 临时字段 所属时间分钟
     */
    private long timeStamp;

    /**
     * 商品ID
     */
    private Integer skuId;

    /**
     * 商品代码
     */
    private String skuCode;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 现价
     */
    private Double nowPrice;

    /**
     * 最低价
     */
    private Double minPrice;

    /**
     * 最高价
     */
    private Double maxPrice;

    /**
     * 开盘价
     */
    private Double openPrice;

    /**
     * 修改时间
     */
    private Date editDate;

}
