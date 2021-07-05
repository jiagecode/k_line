package com.line.backstage.service.impl;
 
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.AccountInfoMapper;
import com.line.backstage.service.AccountInfoService;
import org.springframework.stereotype.Service;
import java.util.Date;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
 
/**
 * 用户账户(AccountInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:34:17
 */
@Service("accountInfoService")
public class AccountInfoServiceImpl implements AccountInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private AccountInfoMapper accountInfoMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, AccountInfo accountInfo) {
        if(accountInfo.getAccountId() == null){
            return insert(loginUserId, accountInfo);
        } else {
            return update(loginUserId,accountInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, AccountInfo accountInfo) {
        accountInfo.setAddUserId(loginUserId);
        return accountInfoMapper.insertSelective(accountInfo);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param accountId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer accountId) {
		AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(accountId);
        accountInfo.setEditUserId(loginUserId);
        accountInfo.setEditDate(new Date());
        accountInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return accountInfoMapper.updateByPrimaryKeySelective(accountInfo);
    }
 
    /**
     * 修改数据
     *
     * @param accountInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, AccountInfo accountInfo){
		AccountInfo a = accountInfoMapper.selectByPrimaryKey(accountInfo.getAccountId());
		// FIXME 待完善
        return accountInfoMapper.updateByPrimaryKeySelective(a);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param accountId 主键
     * @return 实例对象
     */
    @Override
    public AccountInfo queryById(Integer accountId){
		return accountInfoMapper.selectByPrimaryKey(accountId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<AccountInfo> list(Integer loginUserId, AccountInfo accountInfo) {
        PageHelper.startPage(accountInfo.getPageNum(), accountInfo.getPageSize());
        accountInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<AccountInfo> page = new PageInfo<>(accountInfoMapper.select(accountInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    /**
     * 通过loginUserId查询单条数据
     *
     * @param loginUserId
     * @return 实例对象
     */
    @Override
    public AccountInfo queryByLoginUserId(Integer loginUserId){
        return accountInfoMapper.queryByUserId(loginUserId);
    }

}