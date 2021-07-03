package com.line.backstage.entity;

import com.line.backstage.bases.QueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
 
/**
 * 自选商品信息表(SkuCusInfo)实体类
 *
 * @author Zy
 * @since 2021-07-02 18:09:07
 */
@Data
@Table(name = "t_sku_cus_info")
@ApiModel("自选商品信息表")
public class SkuCusInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = -41017020365207917L;
    
    /**
    * 商品ID
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("商品ID")
    private Integer skuId;
    
    /**
    * 用户id
    */            
    @ApiModelProperty("用户id")
    private Integer userId;
    
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
    private Integer skuStatus;
    
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
    private Integer del;
    
}