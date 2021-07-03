package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.SysPowerMapper;
import com.line.backstage.entity.SysPower;
import com.line.backstage.service.SysPowerService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
 
/**
 * 角色菜单表  (SysPower)表服务实现类
 *
 * @author Zy
 * @since 2021-07-03 10:24:46
 */
@Service("sysPowerService")
public class SysPowerServiceImpl implements SysPowerService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysPowerMapper sysPowerMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysPower 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysPower sysPower) {
        if(sysPower.getRmId() == null){
            return insert(loginUserId, sysPower);
        } else {
            return update(loginUserId,sysPower);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysPower 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysPower sysPower) {
        return sysPowerMapper.insertSelective(sysPower);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param rmId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer rmId) {
        return sysPowerMapper.deleteByPrimaryKey(rmId);
    }
 
    /**
     * 修改数据
     *
     * @param sysPower 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysPower sysPower){
		SysPower s = sysPowerMapper.selectByPrimaryKey(sysPower.getRmId());
		// FIXME 待完善
        return sysPowerMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param rmId 主键
     * @return 实例对象
     */
    @Override
    public SysPower queryById(Integer rmId){
		return sysPowerMapper.selectByPrimaryKey(rmId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysPower 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysPower> list(Integer loginUserId, SysPower sysPower) {
        PageHelper.startPage(sysPower.getPageNum(), sysPower.getPageSize());
        PageInfo<SysPower> page = new PageInfo<>(sysPowerMapper.select(sysPower));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}