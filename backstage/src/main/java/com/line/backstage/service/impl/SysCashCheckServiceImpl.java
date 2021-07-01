package com.line.backstage.service.impl;
 
import com.line.backstage.entity.SysCashCheck;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.SysCashCheckMapper;
import com.line.backstage.service.SysCashCheckService;
import org.springframework.stereotype.Service;
import java.util.Date;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
 
/**
 * 后台管理系统提现审核记录表(SysCashCheck)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:35:16
 */
@Service("sysCashCheckService")
public class SysCashCheckServiceImpl implements SysCashCheckService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysCashCheckMapper sysCashCheckMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysCashCheck sysCashCheck) {
        if(sysCashCheck.getId() == null){
            return insert(loginUserId, sysCashCheck);
        } else {
            return update(loginUserId,sysCashCheck);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysCashCheck sysCashCheck) {
        sysCashCheck.setAddUserId(loginUserId);
        return sysCashCheckMapper.insertSelective(sysCashCheck);
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
		SysCashCheck sysCashCheck = sysCashCheckMapper.selectByPrimaryKey(id);
        sysCashCheck.setEditUserId(loginUserId);
        sysCashCheck.setEditDate(new Date());
        sysCashCheck.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return sysCashCheckMapper.updateByPrimaryKeySelective(sysCashCheck);
    }
 
    /**
     * 修改数据
     *
     * @param sysCashCheck 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysCashCheck sysCashCheck){
		SysCashCheck s = sysCashCheckMapper.selectByPrimaryKey(sysCashCheck.getId());
		// FIXME 待完善
        return sysCashCheckMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysCashCheck queryById(Integer id){
		return sysCashCheckMapper.selectByPrimaryKey(id);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysCashCheck> list(Integer loginUserId, SysCashCheck sysCashCheck) {
        PageHelper.startPage(sysCashCheck.getPageNum(), sysCashCheck.getPageSize());
        sysCashCheck.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<SysCashCheck> page = new PageInfo<>(sysCashCheckMapper.select(sysCashCheck));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}