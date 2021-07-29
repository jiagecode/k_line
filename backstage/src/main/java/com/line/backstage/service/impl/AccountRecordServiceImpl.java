package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.AccountInfoMapper;
import com.line.backstage.dao.mapper.AccountRecordMapper;
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.AccountRecordService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户资金账户变动记录(AccountRecord)表服务实现类
 *
 * @author jack
 * @since 2000-06-29 14:46:07
 */
@Service("accountRecordService")
public class AccountRecordServiceImpl implements AccountRecordService {

    /**
     * 服务对象
     */
    @Resource
    private AccountRecordMapper accountRecordMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;

    /**
     * 保存数据
     *
     * @param loginUserId   用户ID
     * @param accountRecord 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, AccountRecord accountRecord) {
        if (accountRecord.getRecordId() == null) {
            return insert(loginUserId, accountRecord);
        } else {
            return update(loginUserId, accountRecord);
        }
    }

    /**
     * 新增数据
     *
     * @param loginUserId   用户ID
     * @param accountRecord 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, AccountRecord accountRecord) {
        accountRecord.setAddUserId(loginUserId);
        return accountRecordMapper.insertSelective(accountRecord);
    }

    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param recordId    主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer recordId) {
        AccountRecord accountRecord = accountRecordMapper.selectByPrimaryKey(recordId);
        accountRecord.setEditUserId(loginUserId);
        accountRecord.setEditDate(new Date());
        accountRecord.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return accountRecordMapper.updateByPrimaryKeySelective(accountRecord);
    }

    /**
     * 修改数据
     *
     * @param accountRecord 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, AccountRecord accountRecord) {
        AccountRecord a = accountRecordMapper.selectByPrimaryKey(accountRecord.getRecordId());
        // FIXME 待完善
        return accountRecordMapper.updateByPrimaryKeySelective(a);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    @Override
    public AccountRecord queryById(Integer recordId) {
        return accountRecordMapper.selectByPrimaryKey(recordId);
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId   用户ID
     * @param accountRecord 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<AccountRecord> list(Integer loginUserId, AccountRecord accountRecord) {
        PageHelper.startPage(accountRecord.getPageNum(), accountRecord.getPageSize());
      //  accountRecord.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        Integer accountId = accountInfoMapper.queryMyAccountIdByUserId(loginUserId);
       // accountRecord.setAccountId(accountId);
        PageInfo<AccountRecord> page = new PageInfo<>(accountRecordMapper.queryH5Page(accountId));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}