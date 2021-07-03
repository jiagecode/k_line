package com.line.backstage.service.impl;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.SysMenuInfoMapper;
import com.line.backstage.entity.SysMenuInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SysMenuInfoService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
 
/**
 * 菜单表(SysMenuInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-07-03 10:27:30
 */
@Service("sysMenuInfoService")
public class SysMenuInfoServiceImpl implements SysMenuInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysMenuInfoMapper sysMenuInfoMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysMenuInfo sysMenuInfo) {
        if(sysMenuInfo.getMenuId() == null){
            return insert(loginUserId, sysMenuInfo);
        } else {
            return update(loginUserId,sysMenuInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysMenuInfo sysMenuInfo) {
        sysMenuInfo.setAddUserId(loginUserId);
        return sysMenuInfoMapper.insertSelective(sysMenuInfo);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param menuId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer menuId) {
		SysMenuInfo sysMenuInfo = sysMenuInfoMapper.selectByPrimaryKey(menuId);
        sysMenuInfo.setEditUserId(loginUserId);
        sysMenuInfo.setEditTime(new Date());
        sysMenuInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return sysMenuInfoMapper.updateByPrimaryKeySelective(sysMenuInfo);
    }
 
    /**
     * 修改数据
     *
     * @param sysMenuInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysMenuInfo sysMenuInfo){
		SysMenuInfo s = sysMenuInfoMapper.selectByPrimaryKey(sysMenuInfo.getMenuId());
		// FIXME 待完善
        return sysMenuInfoMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @Override
    public SysMenuInfo queryById(Integer menuId){
		return sysMenuInfoMapper.selectByPrimaryKey(menuId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysMenuInfo> list(Integer loginUserId, SysMenuInfo sysMenuInfo) {
        PageHelper.startPage(sysMenuInfo.getPageNum(), sysMenuInfo.getPageSize());
        sysMenuInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<SysMenuInfo> page = new PageInfo<>(sysMenuInfoMapper.select(sysMenuInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}