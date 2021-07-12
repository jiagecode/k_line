package com.line.backstage.service.impl;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.SysRoleInfoMapper;
import com.line.backstage.entity.SysRoleInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SysRoleInfoService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
 
/**
 * 后台管理系统角色表(SysRoleInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:36:06
 */
@Service("sysRoleInfoService")
public class SysRoleInfoServiceImpl implements SysRoleInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysRoleInfoMapper sysRoleInfoMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysRoleInfo sysRoleInfo) {
        if(sysRoleInfo.getRoleId() == null){
            return insert(loginUserId, sysRoleInfo);
        } else {
            return update(loginUserId,sysRoleInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysRoleInfo sysRoleInfo) {
        sysRoleInfo.setAddUserId(loginUserId);
        sysRoleInfo.setDel(1);
        sysRoleInfo.setEditUserId(loginUserId);
        sysRoleInfo.setEditTime(new Date());
        sysRoleInfo.setAddTime(new Date());
        if(sysRoleInfo.getRoleType() == null){
            sysRoleInfo.setRoleType(1);
        }
        sysRoleInfo.setLocked(1);
        return sysRoleInfoMapper.insertSelective(sysRoleInfo);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer roleId) {
		SysRoleInfo sysRoleInfo = sysRoleInfoMapper.selectByPrimaryKey(roleId);
        sysRoleInfo.setEditUserId(loginUserId);
        sysRoleInfo.setEditTime(new Date());
        sysRoleInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        return sysRoleInfoMapper.updateByPrimaryKeySelective(sysRoleInfo);
    }
 
    /**
     * 修改数据
     *
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysRoleInfo sysRoleInfo){
		SysRoleInfo s = sysRoleInfoMapper.selectByPrimaryKey(sysRoleInfo.getRoleId());
		// FIXME 待完善
        if(s != null){
            s.setEditTime(new Date());
            s.setEditUserId(loginUserId);
            return sysRoleInfoMapper.updateByPrimaryKeySelective(sysRoleInfo);
        }
        return 0;
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRoleInfo queryById(Integer roleId){
		return sysRoleInfoMapper.selectByPrimaryKey(roleId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysRoleInfo> list(Integer loginUserId, SysRoleInfo sysRoleInfo) {
        PageHelper.startPage(sysRoleInfo.getPageNum(), sysRoleInfo.getPageSize());
        sysRoleInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        PageInfo<SysRoleInfo> page = new PageInfo<>(sysRoleInfoMapper.select(sysRoleInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}