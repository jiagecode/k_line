package com.line.backstage.service.impl;

import com.line.backstage.dao.mapper.AccountInfoMapper;
import com.line.backstage.entity.CashOutIn;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.CashOutInMapper;
import com.line.backstage.service.CashOutInService;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

/**
 * 用户资金充值或提现记录(CashOutIn)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:34:38
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