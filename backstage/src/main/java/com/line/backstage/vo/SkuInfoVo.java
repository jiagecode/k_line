package com.line.backstage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkuInfoVo {
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
