package com.line.backstage.service.impl;
 
import com.line.backstage.entity.SysRechageConfig;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.SysRechageConfigMapper;
import com.line.backstage.service.SysRechageConfigService;
import org.springframework.stereotype.Service;
import java.util.Date;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
 
/**
 * 后台管理系统充值路径配置表(SysRechageConfig)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:35:55
 */
@Service("sysRechageConfigService")
public class SysRechageConfigServiceImpl implements SysRechageConfigService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysRechageConfigMapper sysRechageConfigMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysRechageConfig 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysRechageConfig sysRechageConfig) {
        if(sysRechageConfig.getId() == null){
            return insert(loginUserId, sysRechageConfig);
        } else {
            return update(loginUserId,sysRechageConfig);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysRechageConfig 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysRechageConfig sysRechageConfig) {
        sysRechageConfig.setAddUserId(loginUserId);
        return sysRechageConfigMapper.insertSelective(sysRechageConfig);
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
		SysRechageConfig sysRechageConfig = sysRechageConfigMapper.selectByPrimaryKey(id);
        sysRechageConfig.setEditUserId(loginUserId);
        sysRechageConfig.setEditDate(new Date());
        sysRechageConfig.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return sysRechageConfigMapper.updateByPrimaryKeySelective(sysRechageConfig);
    }
 
    /**
     * 修改数据
     *
     * @param sysRechageConfig 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysRechageConfig sysRechageConfig){
		SysRechageConfig s = sysRechageConfigMapper.selectByPrimaryKey(sysRechageConfig.getId());
		// FIXME 待完善
        return sysRechageConfigMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRechageConfig queryById(Integer id){
		return sysRechageConfigMapper.selectByPrimaryKey(id);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysRechageConfig 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysRechageConfig> list(Integer loginUserId, SysRechageConfig sysRechageConfig) {
        PageHelper.startPage(sysRechageConfig.getPageNum(), sysRechageConfig.getPageSize());
        sysRechageConfig.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<SysRechageConfig> page = new PageInfo<>(sysRechageConfigMapper.select(sysRechageConfig));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}