package com.line.backstage.service.impl;
 
import com.line.backstage.entity.SysPower;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.SysPowerMapper;
import com.line.backstage.service.SysPowerService;
import org.springframework.stereotype.Service;
import java.util.Date;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
 
/**
 * 后台管理系统角色与菜单对应关系(SysPower)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:35:45
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
        if(sysPower.getId() == null){
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
        sysPower.setAddUserId(loginUserId);
        return sysPowerMapper.insertSelective(sysPower);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer id) {
		SysPower sysPower = sysPowerMapper.selectByPrimaryKey(id);
        sysPower.setEditUserId(loginUserId);
        sysPower.setEditDate(new Date());
        sysPower.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return sysPowerMapper.updateByPrimaryKeySelective(sysPower);
    }
 
    /**
     * 修改数据
     *
     * @param sysPower 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysPower sysPower){
		SysPower s = sysPowerMapper.selectByPrimaryKey(sysPower.getId());
		// FIXME 待完善
        return sysPowerMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysPower queryById(Integer id){
		return sysPowerMapper.selectByPrimaryKey(id);
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
        sysPower.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<SysPower> page = new PageInfo<>(sysPowerMapper.select(sysPower));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}