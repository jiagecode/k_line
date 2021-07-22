package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.SkuCusInfoMapper;
import com.line.backstage.entity.SkuCusInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SkuCusInfoService;
import com.line.backstage.utils.PageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 自选商品信息表(SkuCusInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-02 18:09:07
 */
@Slf4j
@Service("skuCusInfoService")
public class SkuCusInfoServiceImpl implements SkuCusInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private SkuCusInfoMapper skuCusInfoMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SkuCusInfo skuCusInfo) {
        if(skuCusInfo.getSkuId() == null){
            return insert(loginUserId, skuCusInfo);
        } else {
            return update(loginUserId,skuCusInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 实例对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(Integer loginUserId, SkuCusInfo skuCusInfo) {
        if (StringUtils.isEmpty(skuCusInfo.getSkuCode())) {
            return -1;
        }
        skuCusInfo.setUserId(loginUserId);
        SkuCusInfo s = skuCusInfoMapper.selectOne(skuCusInfo);
        if(s == null){
            skuCusInfo.setAddUserId(loginUserId);
            skuCusInfo.setAddDate(new Date());;
            skuCusInfo.setDel(1);
            skuCusInfoMapper.insertSelective(skuCusInfo);
            return 1;
        } else {
            s.setEditUserId(loginUserId);
            s.setEditDate(new Date());
            int del = s.getDel() == 0 ? 1 : 0;
            s.setDel(del);
            skuCusInfoMapper.updateByPrimaryKey(s);
            return del;
        }
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param skuId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer skuId) {
		SkuCusInfo skuCusInfo = skuCusInfoMapper.selectByPrimaryKey(skuId);
        skuCusInfo.setEditUserId(loginUserId);
        skuCusInfo.setEditDate(new Date());
        skuCusInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return skuCusInfoMapper.updateByPrimaryKeySelective(skuCusInfo);
    }
 
    /**
     * 修改数据
     *
     * @param skuCusInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SkuCusInfo skuCusInfo){
		SkuCusInfo s = skuCusInfoMapper.selectByPrimaryKey(skuCusInfo.getSkuId());
		// FIXME 待完善
        return skuCusInfoMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param skuId 主键
     * @return 实例对象
     */
    @Override
    public SkuCusInfo queryById(Integer skuId){
		return skuCusInfoMapper.selectByPrimaryKey(skuId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SkuCusInfo> list(Integer loginUserId, SkuCusInfo skuCusInfo) {
        PageHelper.startPage(skuCusInfo.getPageNum(), skuCusInfo.getPageSize());
        skuCusInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        skuCusInfo.setUserId(loginUserId);
        PageInfo<SkuCusInfo> page = new PageInfo<>(skuCusInfoMapper.select(skuCusInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public String queryMyCusCode(Integer loginUserId) {
        List<String> list =  skuCusInfoMapper.queryMyCusCode(loginUserId);
        String result = StringUtils.join(list,"%2C");
        return result;
    }
}