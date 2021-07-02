package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.BankCardInfoMapper;
import com.line.backstage.entity.BankCardInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.BankCardInfoService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 银行卡信息(BankCardInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-06-24 10:49:55
 */
@Service("bankCardInfoService")
public class BankCardInfoServiceImpl implements BankCardInfoService {

    /**
     * 服务对象
     */
    @Resource
    private BankCardInfoMapper bankCardInfoMapper;

    /**
     * 保存数据
     *
     * @param loginUserId  用户ID
     * @param bankCardInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, BankCardInfo bankCardInfo) {
        if (bankCardInfo.getBankCardId() == null) {
            return insert(loginUserId, bankCardInfo);
        } else {
            return update(loginUserId, bankCardInfo);
        }
    }

    /**
     * 新增数据
     *
     * @param loginUserId  用户ID
     * @param bankCardInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, BankCardInfo bankCardInfo) {
        bankCardInfo.setAddUserId(loginUserId);
        return bankCardInfoMapper.insertSelective(bankCardInfo);
    }

    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param bankCardId  主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer bankCardId) {
        BankCardInfo bankCardInfo = bankCardInfoMapper.selectByPrimaryKey(bankCardId);
        bankCardInfo.setEditUserId(loginUserId);
        bankCardInfo.setEditDate(new Date());
        bankCardInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return bankCardInfoMapper.updateByPrimaryKeySelective(bankCardInfo);
    }

    /**
     * 修改数据
     *
     * @param bankCardInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, BankCardInfo bankCardInfo) {
        BankCardInfo b = bankCardInfoMapper.selectByPrimaryKey(bankCardInfo.getBankCardId());
        // FIXME 待完善
        return bankCardInfoMapper.updateByPrimaryKeySelective(b);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param bankCardId 主键
     * @return 实例对象
     */
    @Override
    public BankCardInfo queryById(Integer bankCardId) {
        return bankCardInfoMapper.selectByPrimaryKey(bankCardId);
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId  用户ID
     * @param bankCardInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<BankCardInfo> list(Integer loginUserId, BankCardInfo bankCardInfo) {
        PageHelper.startPage(bankCardInfo.getPageNum(), bankCardInfo.getPageSize());
        bankCardInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        bankCardInfo.setUserId(loginUserId);
        PageInfo<BankCardInfo> page = new PageInfo<>(bankCardInfoMapper.select(bankCardInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}