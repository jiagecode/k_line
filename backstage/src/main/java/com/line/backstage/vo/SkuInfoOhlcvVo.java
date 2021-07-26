package com.line.backstage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkuInfoOhlcvVo implements Serializable {

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 商品代码
     */
    private String skuCode;

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
     * volumefrom
     */
    private Double volumeFrom;

    /**
     * volumeto
     */
    private Double volumeTo;

    /**
     * close
     */
    private Double close;

    /**
     * orderNum
     */
    private int orderNum;


}
