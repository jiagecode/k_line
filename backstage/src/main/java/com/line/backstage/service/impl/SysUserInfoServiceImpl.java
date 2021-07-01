package com.line.backstage.service.impl;
 
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.SysUserInfoMapper;
import com.line.backstage.service.SysUserInfoService;
import org.springframework.stereotype.Service;
import java.util.Date;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
 
/**
 * 后台管理系统用户表(SysUserInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:36:19
 */
@Service("sysUserInfoService")
public class SysUserInfoServiceImpl implements SysUserInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysUserInfoMapper sysUserInfoMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysUserInfo sysUserInfo) {
        if(sysUserInfo.getSysUserId() == null){
            return insert(loginUserId, sysUserInfo);
        } else {
            return update(loginUserId,sysUserInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysUserInfo sysUserInfo) {
        sysUserInfo.setAddUserId(loginUserId);
        return sysUserInfoMapper.insertSelective(sysUserInfo);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param sysUserId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer sysUserId) {
		SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(sysUserId);
        sysUserInfo.setEditUserId(loginUserId);
        sysUserInfo.setEditDate(new Date());
        sysUserInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return sysUserInfoMapper.updateByPrimaryKeySelective(sysUserInfo);
    }
 
    /**
     * 修改数据
     *
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysUserInfo sysUserInfo){
		SysUserInfo s = sysUserInfoMapper.selectByPrimaryKey(sysUserInfo.getSysUserId());
		// FIXME 待完善
        return sysUserInfoMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param sysUserId 主键
     * @return 实例对象
     */
    @Override
    public SysUserInfo queryById(Integer sysUserId){
		return sysUserInfoMapper.selectByPrimaryKey(sysUserId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysUserInfo> list(Integer loginUserId, SysUserInfo sysUserInfo) {
        PageHelper.startPage(sysUserInfo.getPageNum(), sysUserInfo.getPageSize());
        sysUserInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<SysUserInfo> page = new PageInfo<>(sysUserInfoMapper.select(sysUserInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}