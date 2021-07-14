package com.line.backstage.service.impl;

import com.line.backstage.annotation.Log;
import com.line.backstage.dao.mapper.*;
import com.line.backstage.entity.*;
import com.line.backstage.service.TaskOrderSettlementService;
import com.line.backstage.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author DWanBean
 * @Date 2021/7/14 16:30
 * @Version 1.0
 * @Desc
 */
@Service
public class OrderSettlementServiceImpl implements TaskOrderSettlementService {
    private Logger logger = LoggerFactory.getLogger(OrderSettlementServiceImpl.class);
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private AccountRecordMapper accountRecordMapper;
    @Resource
    private PositionInfoMapper positionInfoMapper;
    /**
     * 单次任务最大结算单数
     */
    private final int MAX_NUM = 10000;
    /**
     * 处理订单结算
     */
    @Override
    @Log
    public void dealOrderSettlement() {
        //已当前时间为结算时间
        Date settlementDate = new Date();
        logger.error("结算订单任务开始时间为："+settlementDate);
        int num = 0;
        //是否进行结算
        boolean settFlag = true;
        Integer todayNum = DateUtil.getTodayIntNum();
        while (settFlag && num <MAX_NUM){
            List<OrderInfo> orders = queryAllNeedSettlementOrders(settlementDate);
            if(CollectionUtils.isEmpty(orders)){
                settFlag = false;
                continue;
            }
           for (OrderInfo orderInfo:orders){
               num += dealOneOrder(orderInfo,settlementDate,todayNum);
           }
        }
        logger.error("结算订单条数为："+num);
    }

    /**
     * 处理单条订单结算
     * @param orderInfo
     * @return
     */
    private int dealOneOrder(OrderInfo orderInfo,Date settlementDate,Integer todayNum){
        try {
            settlementOne(orderInfo,settlementDate,todayNum);
            return  1;
        }catch (Exception e){
            logger.error("结算订单出错 订单id:"+orderInfo.getOrderId()+"错误信息为：\n"+e.getMessage());
        }
        return 0;
    }

    /**
     * 查询待结算订单
     * @param settlementDate
     * @return
     */
    private List<OrderInfo> queryAllNeedSettlementOrders(Date settlementDate){
        return orderInfoMapper.queryAllNeedSettlementOrders(settlementDate);
    }
    /**
     * 订单结算
     * @param orderInfo
     * @param settlementTime 结算时间（参数）
     */
    private void settlementOne(OrderInfo orderInfo,Date settlementTime,Integer todayNum){
        //查询到的结算时间
        Date settlementDate = orderInfo.getSettlementDate();
        if(settlementDate == null){
            settlementDate = settlementTime;
        }
        Integer winFlag = orderInfo.getWinFlag();
        winFlag = winFlag == null ? 0 : winFlag;
        Integer userId = orderInfo.getUserId();
        //投资方向 1-买涨 2-买亏
        Integer investType = orderInfo.getInvestType();
        //买入点位
        Double inPoint = orderInfo.getInPoint();
        //投资金额
        Double investAmount = orderInfo.getInvestAmount();
        //获取卖出点位
        double outPoint = getUserOutPoint(inPoint,userId,investType,winFlag);
        //是否赢
        boolean isWin = orderIsWin(outPoint,inPoint,investType,winFlag);
        //结算金额
        double changeMoney = getUserEndMoney(isWin,userId,investAmount);
        /* 步骤1 ==》 跟新订单*/
        orderInfo.setEditDate(settlementDate);
        //订单已完成
        orderInfo.setOrderStatus(2);
        orderInfo.setOrderCharge(0.0);
        orderInfo.setOutPoint(outPoint);
        orderInfo.setResultMoney(changeMoney);
        orderInfo.setSubMoney(changeMoney - investAmount);
        orderInfoMapper.updateByPrimaryKey(orderInfo);
        AccountInfo accountInfo = accountInfoMapper.queryByUserId(userId);
        Double beforeMoney = accountInfo.getAccountMoney();
        Double endMoney = beforeMoney + changeMoney;
        Double allMoney = accountInfo.getAllMoney() ==null? 0.0 : accountInfo.getAllMoney();
        if(changeMoney > 0){
            /* 步骤2 ==》 跟新账户信息*/
            accountInfo.setAccountMoney(endMoney);
            accountInfo.setEditDate(settlementDate);
            accountInfo.setAllInMoney(allMoney + endMoney - investAmount);
            Double inMoney = accountInfo.getAllInMoney() == null? 0.0 : accountInfo.getAllInMoney();
            accountInfo.setAllInMoney(inMoney + changeMoney);
            Integer inNum = accountInfo.getAllInNum() == null ? 0: accountInfo.getAllInNum();
            accountInfo.setAllInNum(++inNum);
            Double allComm = accountInfo.getAllCommission() == null? 0.0:accountInfo.getAllCommission();
            Double reIn = accountInfo.getReallyInMoney() == null ? 0.0 : accountInfo.getReallyInMoney();
            accountInfo.setAllCommission(allComm +changeMoney);
            accountInfo.setReallyInMoney(reIn +endMoney - investAmount );
            accountInfoMapper.updateByPrimaryKey(accountInfo);
        }
        /* 步骤3 ==》 跟新持仓信息*/
        PositionInfo positionInfo = new PositionInfo();
        positionInfo.setPositionId(orderInfo.getPositionId());
        positionInfo = positionInfoMapper.selectByPrimaryKey(positionInfo);
        if(positionInfo !=null){
            positionInfo.setPositionStatus(2);
            positionInfo.setNowPrice(outPoint);
            positionInfo.setEndPrice(outPoint);
            positionInfo.setIncomeAmount(changeMoney);
            positionInfo.setIncomeFlage(isWin?1:0);
            positionInfo.setEndDate(settlementDate);
            positionInfo.setEndAmout(changeMoney);
            positionInfo.setEditDate(settlementDate);
            positionInfoMapper.selectByPrimaryKey(positionInfo);
        }
        /* 步骤4 ==》 记录资金变动*/
        addOneRecord(accountInfo.getAccountId(),changeMoney,beforeMoney,endMoney,settlementDate,userId,orderInfo.getOrderId());
        /* 步骤5 ==》 记录每日盈亏*/
        addOneOrderRecordExp(userId,orderInfo.getOrderId(),changeMoney - investAmount,todayNum,settlementDate);
    }

    /**
     * 记录当日盈亏
     * @param uid
     * @param oid
     * @param money
     * @param todayNum
     * @param date
     */
    private void addOneOrderRecordExp(Integer uid,Integer oid,Double money,Integer todayNum,Date date){
        OrderRecordExp exp = new OrderRecordExp();
        exp.setExpMoney(money);
        exp.setOrderId(oid);
        exp.setUserId(uid);
        exp.setAddDate(date);
        exp.setEditDate(date);
        exp.setTodayNum(todayNum);
        exp.setDel(1);
        accountRecordMapper.InsertOneExp(exp);
    }
    /**
     * 记录资金变动
     * @param accId
     * @param changeMoney
     * @param before
     * @param after
     * @param date
     * @param uid
     * @param oid
     */
   private void  addOneRecord(int accId,Double changeMoney,Double before ,Double after,Date date,Integer uid,Integer oid){
       AccountRecord record = new AccountRecord();
       record.setAccountId(accId);
       record.setRecordType(4);
       record.setBeforeMoney(before);
       record.setAfterMoney(after);
       record.setChangeMoney(changeMoney);
       record.setAddDate(date);
       record.setEditDate(date);
       record.setAddUserId(uid);
       record.setEditUserId(uid);
       record.setOrderId(oid);
       record.setDel(1);
       record.setCommissionMoney(changeMoney);
       record.setServiceCharge(0.0);
       record.setRemarks("用户订单结算");
       accountRecordMapper.insertSelective(record);
   }
    /**
     * 用户结算金额
     * @param isWin
     * @param userId
     * @param investAmount
     * @return
     */
    private double getUserEndMoney(boolean isWin,Integer userId,Double investAmount){
        if(isWin){
            Double commission = userInfoMapper.queryCommissionRate(userId);
            if(commission == null){
                commission = 1.0;
            }
            return investAmount * (1 + commission);
        }
        return 0.0;
    }

    /**
     * 判断此单是否赢
     * @param outPoint
     * @param inPoint
     * @param investType
     * @param winFlag
     * @return
     */
    private boolean orderIsWin(double outPoint,Double inPoint,Integer investType,Integer winFlag){
        if(winFlag == 0){
            if(investType == 1){
                return outPoint > inPoint ;
            }else {
                return outPoint < inPoint;
            }
        }else {
            return winFlag == 1;
        }
    }

    /**
     * 获取用户卖出点位
     * @param inPoint
     * @param userId
     * @param investType 投资方向 1-买涨 2-买跌
     * @param winFlag 盈亏管控 0-未管控 1-控赢 2-控输
     * @return
     */
    private double getUserOutPoint(Double inPoint,Integer userId,Integer investType,Integer winFlag){
        double romRate = Math.random();
        if(winFlag == 1){
            if(investType == 1){
                return  inPoint * (1 + romRate * 0.5) ;
            }else {
                return inPoint * (1 - romRate * 0.5) ;
            }
        }else if(winFlag == 2){
            if(investType == 1){
                return  inPoint * (1 - romRate * 0.6) ;
            }else {
                return inPoint * (1 + romRate * 0.6) ;
            }
        }else {
            //查询用户赢率
            int winRate = userInfoMapper.queryWinRate(userId);
            double winReteDou = winRate * 1.0 / 100;
            boolean isWin = winReteDou >= romRate;
            logger.error("用户未控输赢 用户id："+userId +" 赢率为："+winRate+ " 转小数后为："+winReteDou+" 随机数为："+romRate +" 赢否："+isWin);
            if(isWin){
                if(investType == 1){
                    return  inPoint * (1 + romRate * 0.4) ;
                }else {
                    return inPoint * (1 - romRate * 0.4) ;
                }
            }else {
                if(investType == 1){
                    return  inPoint * (1 - romRate * 0.4) ;
                }else {
                    return inPoint * (1 + romRate * 0.4) ;
                }
            }
        }
    }



}
