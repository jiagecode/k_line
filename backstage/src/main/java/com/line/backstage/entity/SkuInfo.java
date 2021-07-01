package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.*;
import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
 
/**
 * 商品信息表(SkuInfo)实体类
 *
 * @author Zy
 * @since 2021-07-01 11:35:00
 */
@Data
@Table(name = "t_sku_info")
@ApiModel("商品信息表")
public class SkuInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -49678931819763860L;
    
    /**
    * 商品ID
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("商品ID")
    private Integer skuId;
    
    /**
    * 商品代码
    */            
    @ApiModelProperty("商品代码")
    private String skuCode;
    
    /**
    * 商品名称
    */            
    @ApiModelProperty("商品名称")
    private String skuName;
    
    /**
    * 商品状态 1-在售 0-停售
    */            
    @ApiModelProperty("商品状态 1-在售 0-停售")
    private Object skuStatus;
    
    /**
    * 现价
    */            
    @ApiModelProperty("现价")
    private Double nowPrice;
    
    /**
    * 最低价
    */            
    @ApiModelProperty("最低价")
    private Double minPrice;
    
    /**
    * 最高价
    */            
    @ApiModelProperty("最高价")
    private Double maxPrice;
    
    /**
    * 开盘价
    */            
    @ApiModelProperty("开盘价")
    private Double openPrice;
    
    /**
    * 创建时间
    */            
    @ApiModelProperty("创建时间")
    private Date addDate;
    
    /**
    * 修改时间
    */            
    @ApiModelProperty("修改时间")
    private Date editDate;
    
    /**
    * 订单规格
    */            
    @ApiModelProperty("订单规格")
    private Integer skuType;
    
    /**
    * 周期(单位：秒)
    */            
    @ApiModelProperty("周期(单位：秒)")
    private Integer skuCycle;
    
    /**
    * 收益利率
    */            
    @ApiModelProperty("收益利率")
    private Double skuIncomeRate;
    
    /**
    * 创建人
    */            
    @ApiModelProperty("创建人")
    private Integer addUserId;
    
    /**
    * 修改人
    */            
    @ApiModelProperty("修改人")
    private Integer editUserId;
    
    /**
    * 数据状态
    */            
    @ApiModelProperty("数据状态")
    private Object del;
    
}