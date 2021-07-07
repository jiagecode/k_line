package com.line.backstage.service.impl;

import com.line.backstage.dao.mapper.*;
import com.line.backstage.entity.*;
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
 * @Author DWanBean
 * @Date 2021/7/7 10:02
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
                //推广码
              result.put("userRefereeCode",info.getUserRefereeCode());
              //用户id
              result.put("diyUserId",info.getUserId());
              //用户名
              result.put("diyUserName",info.getUserRealName());
              result.put("tel",info.getUserPhone());
              result.put("userType",info.getUserType());
              result.put("trueMoney",info.getUserMoney());
              SysDiyInfo sysDiyInfo = findSysDiyInfo(loginUserId,userId);
              if(sysDiyInfo != null){
                  result.put("diyId",sysDiyInfo.getDiyId());
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
        if(diyId == null){
            diyId = findSysDiyInfo(diyUserId,loginUserId).getDiyId();
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
        }else {
            /*生成提现记录*/
            //提现金额
            String cashStr = (String)map.get("cashStr");
            //通过-3或拒绝-4
            Integer checkStatus = (Integer) map.get("checkStatus");
            if(StringUtils.isNotEmpty(cashStr)&& checkStatus!= null){
                BigDecimal money = new BigDecimal(cashStr);
              money =  createDiyCashData(diyId,diyUserId,loginUserId,money,checkStatus,date);
              result.put("endMoney",money);
            }
        }
        result.put("diyId",diyId);
        return result;
    }

    @Override
    public Integer editDiyRecordByType(Integer loginUserId, Map<String, Object> map) {
        return null;
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
        createAccountRecord(oldAcc.getAccountId(),cashMoney.doubleValue(),oldMoney.doubleValue(),afterMoney.doubleValue(),cashId,diyId,date,sysUserId);
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
    private void createAccountRecord(Integer accId,Double changeMoney,Double beforeMoney,Double afterMoney,Integer cashId,Integer diyId,Date date,Integer sysUserId){
        AccountRecord record = new AccountRecord();
        record.setAccountId(accId);
        record.setRecordType(2);
        record.setChangeMoney(changeMoney);
        record.setBeforeMoney(beforeMoney);
        record.setAfterMoney(afterMoney);
        record.setServiceCharge(0.0);
        record.setCommissionMoney(0.0);
        record.setDiyId(diyId);
        record.setCashId(cashId);
        record.setDel(1);
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

    private SysDiyInfo findSysDiyInfo(Integer userId,Integer sysUserId){
        SysDiyInfo info = new SysDiyInfo();
        info.setSysUserId(sysUserId);
        info.setUserId(userId);
        info.setDiyStatus(0);
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
