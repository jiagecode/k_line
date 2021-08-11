package com.line.backstage.service.impl;

import com.line.backstage.dao.mapper.AccountInfoMapper;
import com.line.backstage.dao.mapper.AccountRecordMapper;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.entity.CashOutIn;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.CashOutInMapper;
import com.line.backstage.service.CashOutInService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

/**
 * 用户资金充值或提现记录(CashOutIn)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:34:38
 */
@Service("cashOutInService")
public class CashOutInServiceImpl implements CashOutInService {

    /**
     * 服务对象
     */
    @Resource
    private CashOutInMapper cashOutInMapper;

    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private AccountRecordMapper accountRecordMapper;
    /**
     * 手续费率1%
     */
    private final double FEE_RATE = 0.01;
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn   实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, CashOutIn cashOutIn) {
        if (cashOutIn.getCashId() == null) {
            return insert(loginUserId, cashOutIn);
        } else {
            return update(loginUserId, cashOutIn);
        }
    }

    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn   实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, CashOutIn cashOutIn) {
        cashOutIn.setAddUserId(loginUserId);
        return cashOutInMapper.insertSelective(cashOutIn);
    }

    @Override
    public int insertForNew(Integer loginUserId, CashOutIn cashOutIn) {
        Integer uid = loginUserId;
        if(cashOutIn.getUserId() != null){
            uid = cashOutIn.getUserId();
        }
        if(cashOutIn.getAccountId() == null){
          cashOutIn.setAccountId(accountInfoMapper.queryMyAccountIdByUserId(uid));
        }
       if( cashOutIn.getCashType() == null){
           //参数错误
           return  -1;
       }
        Integer accId = cashOutIn.getAccountId();
        Integer cashType = cashOutIn.getCashType();
        Double money = cashOutIn.getCashMoney();
        AccountInfo account = new AccountInfo();
        account.setAccountId(accId);
        account = accountInfoMapper.selectByPrimaryKey(account);
        if(account.getAccountStatus() == 1 || account.getMoneyStatus() == 1){
            //账户被冻结
            return  -2;
        }
        Double accMoney = account.getAccountMoney();
        if(cashType == 1 && money > accMoney){
            //资金不足
            return -3 ;
        }
        BigDecimal inCash ;
        BigDecimal outCash ;
        Double fee = FEE_RATE * money;
        Double arrive = money - fee;
        Date date = new Date();
        cashOutIn.setAddDate(date);
        cashOutIn.setEditDate(date);
        cashOutIn.setAddUserId(uid);
        cashOutIn.setEditUserId(uid);
        cashOutIn.setDel(1);
        cashOutIn.setDiyId(0);
        if(cashType == 1){
            cashOutIn.setCheckStatus(1);
            cashOutIn.setArriveMoney(arrive);
            cashOutIn.setCashFee(fee);
            inCash = BigDecimal.ZERO;
            outCash = new BigDecimal(money);
        }else {
            cashOutIn.setArriveMoney(money);
            cashOutIn.setCashFee(0.0);
            inCash = new BigDecimal(money);
            outCash = BigDecimal.ZERO;
            cashOutIn.setCheckManUserId(loginUserId);
            cashOutIn.setCheckDate(date);
        }
        if(StringUtils.isEmpty(cashOutIn.getRemarks())){
            cashOutIn.setRemarks("用户"+(cashType == 1? "提现":"充值")+money);
        }
        if(cashOutIn.getExchangeRate() == null){
            cashOutIn.setExchangeRate(1.0);
        }
        Integer bid = cashOutInMapper.queryUserBankCardId(uid);
        if(bid !=null){
            cashOutIn.setBankCardId(bid);
        }
        int num =  cashOutInMapper.insertSelective(cashOutIn);
        dealForCashOut(account,money,cashOutIn.getCashId(),cashType,uid,date,fee);
        Integer agentId = accountInfoMapper.queryAgentIdByAccId(accId);
        if(agentId != null && agentId != 0){
            //处理代理业绩
            cashOutInMapper.updateAgentScore(agentId,inCash,outCash);
        }
        return num ;
    }


    private void dealForCashOut(AccountInfo account,Double money,Integer cashId,Integer cashType,Integer loginUserId,Date date,Double fee){
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
            info.setAllFee(fee);
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
        addOneRecord(account.getAccountId(),money,cashType,before,after,date,account.getUserId(),cashId,fee);
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
    private void  addOneRecord(int accId,Double changeMoney,Integer cashType,Double before ,Double after,Date date,Integer uid,Integer cashId,Double fee){
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
        record.setServiceCharge(fee);
        record.setRemarks("用户"+(cashType == 1? "提现":"充值")+changeMoney);
        accountRecordMapper.insertSelective(record);
    }

    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param cashId      主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer cashId) {
        CashOutIn cashOutIn = cashOutInMapper.selectByPrimaryKey(cashId);
        cashOutIn.setEditUserId(loginUserId);
        cashOutIn.setEditDate(new Date());
        cashOutIn.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return cashOutInMapper.updateByPrimaryKeySelective(cashOutIn);
    }

    /**
     * 修改数据
     *
     * @param cashOutIn 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, CashOutIn cashOutIn) {
        CashOutIn c = cashOutInMapper.selectByPrimaryKey(cashOutIn.getCashId());
        // FIXME 待完善
        return cashOutInMapper.updateByPrimaryKeySelective(c);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param cashId 主键
     * @return 实例对象
     */
    @Override
    public CashOutIn queryById(Integer cashId) {
        return cashOutInMapper.selectByPrimaryKey(cashId);
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn   查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<CashOutIn> list(Integer loginUserId, CashOutIn cashOutIn) {
        PageHelper.startPage(cashOutIn.getPageNum(), cashOutIn.getPageSize());
        cashOutIn.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        Integer accountId = accountInfoMapper.queryMyAccountIdByUserId(loginUserId);
        cashOutIn.setAccountId(accountId);
        PageInfo<CashOutIn> page = new PageInfo<>(cashOutInMapper.select(cashOutIn));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}