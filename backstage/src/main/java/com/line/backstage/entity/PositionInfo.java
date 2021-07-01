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
 * 用户持仓信息(PositionInfo)实体类
 *
 * @author Zy
 * @since 2021-07-01 11:34:50
 */
@Data
@Table(name = "t_position_info")
@ApiModel("用户持仓信息")
public class PositionInfo extends QueryRequest implements Serializable {
 
    private static final long serialVersionUID = 116077597675446322L;
    
    /**
    * 持仓ID
    */         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @ApiModelProperty("持仓ID")
    private Integer positionId;
    
    /**
    * 用户ID
    */            
    @ApiModelProperty("用户ID")
    private Integer userId;
    
    /**
    * 持仓状态 1-持有 2-已卖出
    */            
    @ApiModelProperty("持仓状态 1-持有 2-已卖出")
    private Object positionStatus;
    
    /**
    * 商品名称
    */            
    @ApiModelProperty("商品名称")
    private String skuName;
    
    /**
    * 投资方向 1-买涨 2-买亏
    */            
    @ApiModelProperty("投资方向 1-买涨 2-买亏")
    private Object investType;
    
    /**
    * 买入价格
    */            
    @ApiModelProperty("买入价格")
    private Double beaginPrice;
    
    /**
    * 当前价格
    */            
    @ApiModelProperty("当前价格")
    private Double nowPrice;
    
    /**
    * 卖出价格
    */            
    @ApiModelProperty("卖出价格")
    private Double endPrice;
    
    /**
    * 投资金额
    */            
    @ApiModelProperty("投资金额")
    private Double investAmount;
    
    /**
    * 买入时间
    */            
    @ApiModelProperty("买入时间")
    private Date beginDate;
    
    /**
    * 卖出时间
    */            
    @ApiModelProperty("卖出时间")
    private Date endDate;
    
    /**
    * 收益金额
    */            
    @ApiModelProperty("收益金额")
    private Double incomeAmount;
    
    /**
    * 是否盈利 1-是 0-否
    */            
    @ApiModelProperty("是否盈利 1-是 0-否")
    private Object incomeFlage;
    
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
    * 结算金额
    */            
    @ApiModelProperty("结算金额")
    private Double endAmout;
    
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