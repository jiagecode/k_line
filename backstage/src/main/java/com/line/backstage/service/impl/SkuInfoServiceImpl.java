package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.SkuInfoMapper;
import com.line.backstage.entity.SkuInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SkuInfoService;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.vo.SkuInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品信息表(SkuInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:35:00
 */
@Service("skuInfoService")
public class SkuInfoServiceImpl implements SkuInfoService {

    /**
     * 服务对象
     */
    @Resource
    private SkuInfoMapper skuInfoMapper;

    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo     实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SkuInfo skuInfo) {
        if (skuInfo.getSkuId() == null) {
            return insert(loginUserId, skuInfo);
        } else {
            return update(loginUserId, skuInfo);
        }
    }

    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo     实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SkuInfo skuInfo) {
        skuInfo.setAddUserId(loginUserId);
        return skuInfoMapper.insertSelective(skuInfo);
    }

    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param skuId       主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        skuInfo.setEditUserId(loginUserId);
        skuInfo.setEditDate(new Date());
        skuInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
    }

    /**
     * 修改数据
     *
     * @param skuInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SkuInfo skuInfo) {
        SkuInfo s = skuInfoMapper.selectByPrimaryKey(skuInfo.getSkuId());
        // FIXME 待完善
        return skuInfoMapper.updateByPrimaryKeySelective(s);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param skuId 主键
     * @return 实例对象
     */
    @Override
    public SkuInfo queryById(Integer skuId) {
        return skuInfoMapper.selectByPrimaryKey(skuId);
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo     查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SkuInfo> list(Integer loginUserId, SkuInfo skuInfo) {
        PageHelper.startPage(skuInfo.getPageNum(), skuInfo.getPageSize());
        skuInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<SkuInfo> page = new PageInfo<>(skuInfoMapper.select(skuInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public List<SkuInfoVo> listAll() {
        return skuInfoMapper.queryListAll();
    }

    @Override
    public void updateAll(List<SkuInfoVo> skuList) {
        for (SkuInfoVo sku : skuList) {
            skuInfoMapper.updetaBySkuInfoVo(sku);
        }
    }

}