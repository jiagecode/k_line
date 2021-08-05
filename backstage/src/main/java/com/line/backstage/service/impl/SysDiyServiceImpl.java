package com.line.backstage.service.impl;

import com.line.backstage.dao.mapper.*;
import com.line.backstage.entity.*;
import com.line.backstage.entity.sysentity.ManOptionVo;
import com.line.backstage.service.SysDiyService;
import com.line.backstage.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Date 2000/7/7 10:02
 * @Version 1.0
 * @Desc
 */
@Service("sysDiyService")
public class SysDiyServiceImpl implements SysDiyService {
    @Resource
    private SysDiyInfoMapper sysDiyInfoMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private CashOutInMapper cashOutInMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private AccountRecordMapper accountRecordMapper;
    @Resource
    private PositionInfoMapper positionInfoMapper;

    @Override
    public Map<String, Object> querySpecialUserInfo(Integer loginUserId,String diyUserId) {
        Map<String, Object> result = new HashMap<>();
        String msg = "SUCCESS";
        if(StringUtils.isNotEmpty(diyUserId)){
            Integer userId =Integer.valueOf(diyUserId);
            UserInfo info = findOneUser(userId);
            if(info == null){
                msg = "该用户不存在";
            }else {
                AccountInfo acc = accountInfoMapper.queryByUserId(userId);
                //推广码
              result.put("userRefereeCode",info.getUserRefereeCode());
              //用户id
              result.put("diyUserId",info.getUserId());
              //用户名
              result.put("diyUserName",info.getUserRealName());
              result.put("tel",info.getUserPhone());
              result.put("userType",info.getUserType());
              result.put("trueMoney",acc.getDiyMoney() == null?0:acc.getDiyMoney());
              SysDiyInfo sysDiyInfo = findSysDiyInfo(loginUserId,userId);
              if(sysDiyInfo != null && sysDiyInfo.getDiyId() != null){
                  result.put("diyId",sysDiyInfo.getDiyId());
                  result.put("orders",queryOrders(userId,sysDiyInfo.getDiyId()));
              }
            }
        }
        result.put("msg",msg);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSpecialUserInfo(Integer loginUserId, Map<String, Object> map) {
        Integer paramUserId = (Integer)map.get("diyUserId");
        if(paramUserId == null){
            return 0;
        }
        UserInfo oldInfo = findOneUser(paramUserId);
        if(oldInfo == null){
            return 0 ;
        }
        Date date = new Date();
        oldInfo.setDiyFlag(1);
        SysDiyInfo diyInfo =  findSysDiyInfo(paramUserId,loginUserId);
        if(diyInfo == null){
            diyInfo = newOneSysDiyInfo(paramUserId,loginUserId);
        }
        String paramName = (String)map.get("diyUserName");
        if(StringUtils.isNotEmpty(paramName)){
            diyInfo.setBeforeName(oldInfo.getUserRealName());
            oldInfo.setDiyUserName(paramName);
            diyInfo.setAfterName(paramName);
        }
        Integer paramType = (Integer)map.get("diyUserType");
        if(paramType != null){
            diyInfo.setBeforeUserType(oldInfo.getUserType());
            oldInfo.setDiyUserType(paramType);
            diyInfo.setAfterUserType(paramType);
        }
        Object  paramMoney = map.get("diyUserMoney");
        if(paramMoney != null){
            AccountInfo oldAcc = accountInfoMapper.queryByUserId(paramUserId);
            if(oldAcc != null){
                BigDecimal diyMoney = new BigDecimal( paramMoney.toString());
                diyInfo.setBeforeMoney(oldAcc.getDiyMoney() == null?BigDecimal.ZERO:oldAcc.getDiyMoney());
                diyInfo.setAfterMoney(diyMoney);
                oldAcc.setDiyMoney(diyMoney);
                oldAcc.setEditUserId(loginUserId);
                oldAcc.setEditDate(date);
                accountInfoMapper.updateByPrimaryKey(oldAcc);
            }
        }
        diyInfo.setEditUserId(loginUserId);
        diyInfo.setEditDate(date);
        oldInfo.setEditUserId(loginUserId);
        oldInfo.setEditDate(date);
        sysDiyInfoMapper.updateByPrimaryKey(diyInfo);
        userInfoMapper.updateByPrimaryKey(oldInfo);
        return diyInfo.getDiyId();
    }

    @Override
    public List<Map<String, Object>> querySkuDataList(Integer loginUserId) {
        return sysDiyInfoMapper.querySkuDataList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createImitateData(Integer loginUserId, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        Integer dealType = (Integer)map.get("dealType");
        if(dealType == null ){
            return result;
        }
        //用户id
        Integer diyUserId = (Integer) map.get("diyUserId");
        //操作id
        Integer diyId = (Integer) map.get("diyId");
        SysDiyInfo diyInfo;
        if(diyId == null){
            diyInfo = findSysDiyInfo(diyUserId,loginUserId);
            diyId = diyInfo.getDiyId();
        }else {
            diyInfo = findById(diyId);
        }
        //起始时间或提现时间
        String dateStr = (String)map.get("dateStr");
        Date date = null ;
        if(StringUtils.isNotEmpty(dateStr)){
            try {
                date = DateUtil.parseStandardDateTime(dateStr);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(date == null){date = new Date();}
        /*生成交易记录*/
        if(dealType == 1){
            String skuName = (String)map.get("skuName");
            String skuCode = (String)map.get("skuCode");
            Integer skuId = (Integer) map.get("skuId");
            //起始金额
            String moneyStr = (String)map.get("moneyStr");
            //价格区间 最高价
            String maxPriceStr = (String)map.get("maxPriceStr");
            //价格区间最低价
            String minPriceStr = (String)map.get("minPriceStr");
            //赢率
            Integer winRate = (Integer) map.get("winRate");
            //投资方向 1买涨 2买跌
            Integer investType = (Integer) map.get("investType");
            //投资类型
            Integer orderCycle = (Integer) map.get("orderCycle");
            //购买局数
            Integer orderNum = (Integer) map.get("orderNum");
            BigDecimal orderMoney = new BigDecimal(moneyStr);
            BigDecimal maxMoney = new BigDecimal(maxPriceStr);
            BigDecimal minMoney = new BigDecimal(minPriceStr);
            if(maxMoney.compareTo(minMoney) < 1){
                maxMoney = minMoney.add(new BigDecimal(100));
            }
            BigDecimal endMoney =  createOrderData(diyUserId,loginUserId,diyId,skuName,skuCode,orderMoney,maxMoney,minMoney,winRate,investType,orderCycle,orderNum,date,skuId);
            //diyInfo.setDiyCashStatus(1);
            diyInfo.setDiyRecordStatus(1);
            diyInfo.setDiyOrderStatus(1);
            diyInfo.setAfterMoney(endMoney);
            diyInfo.setEditUserId(loginUserId);
            diyInfo.setEditDate(new Date());
            diyInfo.setDel(1);
            sysDiyInfoMapper.updateByPrimaryKey(diyInfo);
            result.put("endMoney",endMoney);
            result.put("orders",queryOrders(diyUserId,diyId));
        }else {
            /*生成提现记录*/
            //提现金额
            String cashStr = (String)map.get("cashStr");
            //通过-3或拒绝-4
            Integer checkStatus = (Integer) map.get("checkStatus");
            if(StringUtils.isNotEmpty(cashStr)&& checkStatus!= null){
              BigDecimal money = new BigDecimal(cashStr);
              money =  createDiyCashData(diyId,diyUserId,loginUserId,money,checkStatus,date);
              diyInfo.setDiyCashStatus(1);
              diyInfo.setDiyRecordStatus(1);
              diyInfo.setEditUserId(loginUserId);
              diyInfo.setEditDate(new Date());
              diyInfo.setDel(1);
              sysDiyInfoMapper.updateByPrimaryKey(diyInfo);
              result.put("endMoney",money);
            }
        }
        result.put("diyId",diyId);
        return result;
    }

    @Override
    public Integer editDiyRecordByType(Integer loginUserId, Map<String, Object> map) {
        //用户id
        Integer diyUserId = (Integer) map.get("diyUserId");
        //操作id
        Integer diyId = (Integer) map.get("diyId");
        //操作类型
        Integer dealType = (Integer)map.get("dealType");
        if(dealType == null || diyId == null || diyUserId == null){
            return 0;
        }
        if(dealType == 1){
            /*入库交易记录*/
            positionInfoMapper.updateForDiy(diyId,diyUserId,1,loginUserId);
            orderInfoMapper.updateForDiy(diyId,diyUserId,1,loginUserId);
            accountRecordMapper.updateForDiy(diyId,diyUserId,1,loginUserId);
        }else if(dealType == 2){
            /*删除交易记录*/
            positionInfoMapper.updateForDiy(diyId,diyUserId,0,loginUserId);
            orderInfoMapper.updateForDiy(diyId,diyUserId,0,loginUserId);
            accountRecordMapper.updateForDiy(diyId,diyUserId,0,loginUserId);
        }else if(dealType == 3){
            /*清除提现记录*/
            cashOutInMapper.updateForDiy(diyId,diyUserId,0,loginUserId);
        }else if(dealType ==4){
            /*清除资金流水记录*/
            accountRecordMapper.updateForDiy(diyId,diyUserId,0,loginUserId);
        }
        return 1;
    }

    @Override
    public Integer editUserMoney(Integer loginUserId, Map<String, Object> map) {
        //用户id
        Integer userId = (Integer) map.get("userId");
        String moneyStr = (String)map.get("money");
        //调整类型
        String type = (String) map.get("dealType");
        int investType = Integer.parseInt(type);
        AccountInfo accountInfo = accountInfoMapper.queryByUserId(userId);
        if(accountInfo != null ){
            BigDecimal beforeMoney = new BigDecimal(accountInfo.getAccountMoney());
            BigDecimal changeMoney = new BigDecimal(moneyStr);
            BigDecimal afterMoney ;
            if(investType>= 2){
                afterMoney = beforeMoney.subtract(changeMoney).setScale(10,BigDecimal.ROUND_UP);
            }else {
                afterMoney = beforeMoney.add(changeMoney).setScale(10,BigDecimal.ROUND_UP);
            }
            Date date = new Date();
            accountInfo.setAccountMoney(afterMoney.doubleValue());
            accountInfo.setEditDate(date);
            accountInfo.setEditUserId(loginUserId);
            accountInfoMapper.updateByPrimaryKey(accountInfo);
            createAccountRecord(accountInfo.getAccountId(),changeMoney.doubleValue(),beforeMoney.doubleValue(),afterMoney.doubleValue(),null,null,date,loginUserId,null,investType);
          return 1;
        }
        return 0;
    }

    @Override
    public Integer checkForCashOutIn(Integer loginUserId, CashOutIn cash) {
        CashOutIn outIn = cashOutInMapper.selectByPrimaryKey(cash);
        Integer cashType = outIn.getCashType();
        Integer cashStatus = cash.getCheckStatus();
        Date date = new Date();
        if(outIn !=null ){
            if(cash.getDel() != null){
                outIn.setDel(cash.getDel());
            }else {
                outIn.setCheckStatus(cashStatus);
                outIn.setCheckDate(date);
                outIn.setRemarks(cash.getRemarks());
            }
            outIn.setEditUserId(loginUserId);
            outIn.setEditDate(date);
            if(cashType == 2 && cashStatus == 3){
                // 变动类型为充值2 且审核成功3
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setAccountId(outIn.getAccountId());
                accountInfo = accountInfoMapper.selectByPrimaryKey(accountInfo);
                dealForCashOut(accountInfo,outIn.getCashMoney(),outIn.getCashId(),cashType,loginUserId,date);
            }
            return cashOutInMapper.updateByPrimaryKey(outIn);
        }
        return 0;
    }

    private void dealForCashOut(AccountInfo account,Double money,Integer cashId,Integer cashType,Integer loginUserId,Date date){
        AccountInfo info = new AccountInfo();
        info.setAccountId(account.getAccountId());
        info.setEditUserId(loginUserId);
        Double before = account.getAccountMoney();
        Double after ;
        if(cashType ==1){
            info.setAccountMoney(0 - money );
            info.setAllOutNum(1);
            info.setAllInNum(0);
            info.setAllInMoney(0.0);
            info.setAllOutMoney(money);
            info.setReallyInMoney(0 - money);
            after = before -money;
        }else if(cashType ==2){
            info.setAccountMoney(money );
            info.setAllOutNum(0);
            info.setAllInNum(1);
            info.setAllInMoney(money);
            info.setAllOutMoney(0.0);
            info.setReallyInMoney(money);
            info.setAllFee(0.0);
            after = before + money;
        }else {
            return;
        }
        accountInfoMapper.updateForCashOut(info);
        addOneRecord(account.getAccountId(),money,cashType,before,after,date,account.getUserId(),cashId);
    }

    /**
     * 记录资金变动
     * @param accId
     * @param changeMoney
     * @param cashType
     * @param before
     * @param after
     * @param date
     * @param uid
     * @param cashId
     */
    private void  addOneRecord(int accId,Double changeMoney,Integer cashType,Double before ,Double after,Date date,Integer uid,Integer cashId){
        AccountRecord record = new AccountRecord();
        record.setAccountId(accId);
        record.setRecordType(cashType == 1? 2 :1);
        record.setBeforeMoney(before);
        record.setAfterMoney(after);
        record.setChangeMoney(changeMoney);
        record.setAddDate(date);
        record.setEditDate(date);
        record.setAddUserId(uid);
        record.setEditUserId(uid);
        record.setCashId(cashId);
        record.setDel(1);
        record.setCommissionMoney(0.0);
        record.setServiceCharge(0.0);
        record.setRemarks("用户"+(cashType == 1? "提现":"充值")+changeMoney);
        accountRecordMapper.insertSelective(record);
    }

    @Override
    public Double queryAllCashMoney(Integer loginUserId, Integer cashType) {
        Map<String ,Object> map = new HashMap<>();
        map.put("cashType",cashType);
        return cashOutInMapper.sumAllCash(map);
    }

    @Override
    public List<ManOptionVo> queryOptionsForAgent(Integer loginUserId) {
        return sysDiyInfoMapper.queryOptionsForAgent();
    }

    @Override
    public List<ManOptionVo> queryOptionsForRole(Integer loginUserId) {
        return sysDiyInfoMapper.queryOptionsForSysRole();
    }

    @Override
    public OrderInfo queryDetailForOrder(Integer loginUserId,Integer orderId) {

        OrderInfo o = orderInfoMapper.queryOneById(orderId);
        if(o!= null){
            o.setOrderStatusDesc(o.getOrderStatus() == 1?"持仓":"平仓");
        }
        return  o;
    }

    private List<OrderInfo> queryOrders(Integer userId,Integer diyId){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setDiyId(diyId);
        orderInfo.setDel(-1);
        return orderInfoMapper.select(orderInfo);
    }
    /**
     * 生成订单数据
     * @param diyUserId
     * @param sysUserId
     * @param diyId
     * @param skuName
     * @param skuCode
     * @param orderMoney
     * @param maxMoney
     * @param minMoney
     * @param winRate
     * @param investType
     * @param orderCycle
     * @param orderNum
     * @param date
     */
    private BigDecimal createOrderData(Integer diyUserId,Integer sysUserId,Integer diyId,String skuName,String skuCode,
                                 BigDecimal orderMoney,BigDecimal maxMoney, BigDecimal minMoney,Integer winRate,
                                 Integer investType,Integer orderCycle, Integer orderNum,Date date,Integer skuId){
        //输的次数
        int failNum ;
        if(winRate == 0){
            failNum = orderNum;
        }else if(winRate == 10){
            failNum = 0;
        }else {
            failNum = ((10 -winRate) * orderNum * 10)/100;
        }
        Date beginDete = date ;
        AccountInfo acc  = accountInfoMapper.queryByUserId(diyUserId);
        Integer accId = acc.getAccountId();
        BigDecimal accBefore = acc.getDiyMoney();
        BigDecimal endMoney = accBefore ;
        BigDecimal outPonit = minMoney ;
        //是否加倍
        boolean douFlag = false;
        for (int i = 1 ;i<=orderNum;i++){
            if(i== 1){
                minMoney = minMoney.add(new BigDecimal(Math.random()+"")).setScale(10,BigDecimal.ROUND_UP);
            }else {
                minMoney = outPonit.add(new BigDecimal(Math.random()+"")).setScale(10,BigDecimal.ROUND_UP);
            }
            Date  endDate = new Date(beginDete.getTime()+orderCycle*1000);
            double rate = Math.random();
            //此单是否盈利
            boolean winFlag = rate > 0.5;
            //是否买涨
            boolean inestFlag = investType == 1 ;
            if(!winFlag){
                if(failNum >0){
                    failNum --;
                }else {
                    winFlag = !winFlag;
                }
            }
            //金额范围
            BigDecimal subMoney = maxMoney.subtract(minMoney).setScale(10,BigDecimal.ROUND_UP);
            //订单结果金额
            BigDecimal amount = subMoney.multiply( new BigDecimal(rate)).setScale(10,BigDecimal.ROUND_UP);
            if(winFlag){
                if(inestFlag){
                    outPonit = minMoney.add(amount).setScale(10,BigDecimal.ROUND_UP);
                }else {
                    outPonit = minMoney.subtract(amount).setScale(10,BigDecimal.ROUND_UP);
                }
            }else {
                if(inestFlag){
                    outPonit = minMoney.subtract(amount).setScale(10,BigDecimal.ROUND_UP);
                }else {
                    outPonit = minMoney.add(amount).setScale(10,BigDecimal.ROUND_UP);
                }
            }
            if(douFlag){
                orderMoney = orderMoney.multiply(new BigDecimal("2"));
            }
            BigDecimal retMoney = getReturnMoney(orderMoney,orderCycle);
            if(winFlag){
                endMoney = endMoney.add(retMoney).setScale(4,BigDecimal.ROUND_UP);
            }else {
                endMoney = endMoney.subtract(orderMoney).setScale(4,BigDecimal.ROUND_UP);
            }
            //持仓信息
            Integer posId = insertPositionInfo(beginDete,sysUserId,endDate,diyUserId,diyId,skuName,investType,winFlag,minMoney,outPonit,orderMoney,retMoney);
            //订单信息
            Integer orderId = insertOrder(beginDete,sysUserId,endDate,diyUserId,diyId,skuName,investType,orderCycle,amount,minMoney,outPonit,orderMoney,posId,skuCode,skuId,winFlag,retMoney);
            //资金记录
            createAccountRecord(accId,orderMoney.doubleValue(),accBefore.doubleValue(),(accBefore.subtract(orderMoney)).doubleValue(),null,diyId,endDate,sysUserId,orderId,3);
            beginDete = new Date(endDate.getTime()+6000);
            douFlag = !winFlag;
        }
        acc.setDiyMoney(endMoney);
        accountInfoMapper.updateByPrimaryKeySelective(acc);
        return endMoney;
    }

    private BigDecimal getReturnMoney(BigDecimal orderMoney,Integer orderCycle){
        BigDecimal rate = new BigDecimal("1.88");
        if(orderCycle == 180){
            rate = new BigDecimal("1.92");
        }else if(orderCycle == 60){
            rate = new BigDecimal("1.90");
        }
        return orderMoney.multiply(rate).setScale(10,BigDecimal.ROUND_UP);
    }

    /**
     * 创建订单
     * @param beginDete
     * @param sysUserId
     * @param endDate
     * @param diyUserId
     * @param diyId
     * @param skuName
     * @param investType
     * @param orderCycle
     * @param amount
     * @param minMoney
     * @param outPonit
     * @param orderMoney
     * @param posId
     * @param skuCode
     * @param skuId
     * @return
     */
    private Integer insertOrder(Date beginDete,Integer sysUserId,Date endDate,Integer diyUserId,
                                Integer diyId,String skuName,Integer investType,Integer orderCycle,BigDecimal amount,
                                BigDecimal minMoney,BigDecimal outPonit,BigDecimal orderMoney,Integer posId,String skuCode,Integer skuId,boolean win,BigDecimal retMoney){
        OrderInfo info = new OrderInfo();
        info.setPositionId(posId);
        info.setOrderType(1);
        info.setUserId(diyUserId);
        info.setOrderStatus(2);
        info.setSkuName(skuName);
        info.setSkuId(skuId);
        info.setSkuCode(skuCode);
        info.setSkuQty(1.0);
        info.setAddUserId(sysUserId);
        info.setEditDate(endDate);
        info.setAddDate(beginDete);
        info.setEditUserId(sysUserId);
        info.setOrderCycle(orderCycle);
        info.setSettlementDate(endDate);
        info.setDiyId(diyId);
        info.setInvestType(investType);
        info.setIntegral(0.0);
        info.setSubMoney((win?retMoney.subtract(orderMoney):BigDecimal.ZERO.subtract(orderMoney)).doubleValue());
        info.setResultMoney(win?retMoney.doubleValue():0.0);
        info.setInPoint(minMoney.doubleValue());
        info.setOrderAmount(orderMoney.doubleValue());
        info.setInvestAmount(orderMoney.doubleValue());
        info.setOutPoint(outPonit.doubleValue());
        info.setOrderCharge(0.0);
        info.setExpectedReturn(retMoney.doubleValue());
        //预生成 未入库
        info.setDel(-1);
        info.setWinFlag(win?1:2);
        return orderInfoMapper.insert(info);
    }

    /**
     * 持仓
     * @param beginDete
     * @param sysUserId
     * @param endDate
     * @param diyUserId
     * @param diyId
     * @param skuName
     * @param investType
     * @param winFlag
     * @param minMoney
     * @param outPonit
     * @param orderMoney
     * @return
     */
    private Integer insertPositionInfo(Date beginDete,Integer sysUserId,Date endDate,Integer diyUserId,
                                       Integer diyId,String skuName,Integer investType,boolean winFlag,
                                       BigDecimal minMoney,BigDecimal outPonit,BigDecimal orderMoney,BigDecimal retMoney){
        PositionInfo pos = new PositionInfo();
        pos.setAddDate(beginDete);
        pos.setAddUserId(sysUserId);
        pos.setEditDate(endDate);
        pos.setEditUserId(sysUserId);
        pos.setUserId(diyUserId);
        pos.setPositionStatus(2);
        pos.setSkuName(skuName);
        pos.setInvestType(investType);
        pos.setBeaginPrice(minMoney.doubleValue());
        pos.setEndPrice(outPonit.doubleValue());
        pos.setNowPrice(outPonit.doubleValue());
        pos.setInvestAmount(orderMoney.doubleValue());
        pos.setIncomeAmount(winFlag?retMoney.doubleValue():0.0);
        pos.setEndAmout(winFlag?retMoney.doubleValue():0.0);
        pos.setBeginDate(beginDete);
        pos.setEndDate(endDate);
        pos.setIncomeFlage(winFlag?1:0);
        //预生成 未入库
        pos.setDel(-1);
        pos.setDiyId(diyId);
        return  positionInfoMapper.insert(pos);
    }
    /**
     * 生成提现记录
     * @param diyId
     * @param diyUserId
     * @param sysUserId
     * @param cashMoney
     * @param checkStatus
     * @return 返回提现后的余额
     */
    private BigDecimal createDiyCashData(Integer diyId,Integer diyUserId,Integer sysUserId,BigDecimal cashMoney,Integer checkStatus,Date date){
        AccountInfo oldAcc = accountInfoMapper.queryByUserId(diyUserId);
        BigDecimal oldMoney = oldAcc.getDiyMoney() == null?BigDecimal.ZERO:oldAcc.getDiyMoney();
        if(oldMoney.compareTo(cashMoney) == -1){
            return oldMoney;
        }
        //提现后余额
        BigDecimal afterMoney = oldMoney.subtract(oldMoney);
        CashOutIn cash = new CashOutIn();
        cash.setCashType(1);
        cash.setCashMoney(cashMoney.doubleValue());
        cash.setCheckStatus(checkStatus);
        cash.setCheckDate(date);
        cash.setAddDate(date);
        cash.setEditDate(date);
        cash.setAccountId(oldAcc.getAccountId());
        cash.setCheckManUserId(sysUserId);
        cash.setAddUserId(sysUserId);
        cash.setEditUserId(sysUserId);
        cash.setDiyId(diyId);
        cash.setDel(1);
        int cashId = cashOutInMapper.insert(cash);
        createAccountRecord(oldAcc.getAccountId(),cashMoney.doubleValue(),oldMoney.doubleValue(),afterMoney.doubleValue(),cashId,diyId,date,sysUserId,null,2);
        return  afterMoney;
    }

    /**
     * 生成资金记录
     * @param accId
     * @param changeMoney
     * @param beforeMoney
     * @param afterMoney
     * @param cashId
     */
    private void createAccountRecord(Integer accId,Double changeMoney,Double beforeMoney,Double afterMoney,Integer cashId,Integer diyId,Date date,Integer sysUserId,Integer orderId,Integer type){
        AccountRecord record = new AccountRecord();
        record.setAccountId(accId);
        if(orderId != null){
            record.setOrderId(orderId);
            //预生成 未入库
            record.setDel(-1);
        }else {
            record.setDel(1);
        }
        /*type 管理员系统加款扣款对应充值和提现  此处描述待完善*/
        record.setRecordType(type);
        record.setChangeMoney(changeMoney);
        record.setBeforeMoney(beforeMoney);
        record.setAfterMoney(afterMoney);
        record.setServiceCharge(0.0);
        record.setCommissionMoney(0.0);
        record.setDiyId(diyId);
        record.setCashId(cashId);
        record.setAddDate(date);
        record.setEditDate(date);
        record.setAddUserId(sysUserId);
        record.setEditUserId(sysUserId);
        accountRecordMapper.insert(record);
    }

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    private UserInfo findOneUser(Integer userId){
        UserInfo info = new UserInfo();
        info.setUserId(Integer.valueOf(userId));
        return userInfoMapper.selectByPrimaryKey(info);
    }

    private SysDiyInfo findById(Integer diyId){
        SysDiyInfo info = new SysDiyInfo();
        info.setDiyId(diyId);
        info.setDel(1);
        List<SysDiyInfo> list = sysDiyInfoMapper.selectAsList(info);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    private SysDiyInfo findSysDiyInfo(Integer userId,Integer sysUserId){
        SysDiyInfo info = new SysDiyInfo();
        info.setSysUserId(sysUserId);
        info.setUserId(userId);
        info.setDiyStatus(0);
        info.setDel(1);
        List<SysDiyInfo> list = sysDiyInfoMapper.selectAsList(info);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return newOneSysDiyInfo(userId,sysUserId);
    }

    private SysDiyInfo newOneSysDiyInfo(Integer userId,Integer sysUserId){
        SysDiyInfo info = new SysDiyInfo();
        info.setSysUserId(sysUserId);
        info.setAddDate(new Date());
        //info.setEditUserId(sysUserId);
        info.setDel(1);
        info.setAddUserId(sysUserId);
        info.setUserId(userId);
        info.setDiyCashStatus(0);
        info.setDiyOrderStatus(0);
        info.setDiyRecordStatus(0);
        info.setDiyCashStatus(0);
        int diyId = sysDiyInfoMapper.insertSelective(info);
        info.setDiyId(diyId);
        return info;
    }
}
