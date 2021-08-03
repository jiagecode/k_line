package com.line.backstage.service.impl;

import com.line.backstage.entity.PositionInfo;
import com.line.backstage.entity.sysentity.ManPosiVo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.PositionInfoMapper;
import com.line.backstage.service.PositionInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

/**
 * 用户持仓信息(PositionInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:34:50
 */
@Service("positionInfoService")
public class PositionInfoServiceImpl implements PositionInfoService {

    /**
     * 服务对象
     */
    @Resource
    private PositionInfoMapper positionInfoMapper;

    /**
     * 保存数据
     *
     * @param loginUserId  用户ID
     * @param positionInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, PositionInfo positionInfo) {
        if (positionInfo.getPositionId() == null) {
            return insert(loginUserId, positionInfo);
        } else {
            return update(loginUserId, positionInfo);
        }
    }

    /**
     * 新增数据
     *
     * @param loginUserId  用户ID
     * @param positionInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, PositionInfo positionInfo) {
        positionInfo.setAddUserId(loginUserId);
        int result = positionInfoMapper.insertSelective(positionInfo);
        if(result > 0){
            // 增加持仓数据同时开启定时结算任务
            // todo
        }
        return result;
    }

    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param positionId  主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer positionId) {
        PositionInfo positionInfo = positionInfoMapper.selectByPrimaryKey(positionId);
        positionInfo.setEditUserId(loginUserId);
        positionInfo.setEditDate(new Date());
        positionInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return positionInfoMapper.updateByPrimaryKeySelective(positionInfo);
    }

    /**
     * 修改数据
     *
     * @param positionInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, PositionInfo positionInfo) {
        PositionInfo p = positionInfoMapper.selectByPrimaryKey(positionInfo.getPositionId());
        // FIXME 待完善
        return positionInfoMapper.updateByPrimaryKeySelective(p);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param positionId 主键
     * @return 实例对象
     */
    @Override
    public PositionInfo queryById(Integer positionId) {
        return positionInfoMapper.selectByPrimaryKey(positionId);
    }

    @Override
    public PageWrapper<ManPosiVo>  queryManPosiVo(Integer loginUserId, ManPosiVo vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        PageInfo<ManPosiVo> page = new PageInfo<>(positionInfoMapper.queryManPosiVo(vo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId  用户ID
     * @param positionInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<ManPosiVo> list(Integer loginUserId, PositionInfo positionInfo) {
        PageHelper.startPage(positionInfo.getPageNum(), positionInfo.getPageSize());
        positionInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        positionInfo.setUserId(loginUserId);
        Integer pst = positionInfo.getPositionStatus() == null ? 2:positionInfo.getPositionStatus();
        PageInfo<ManPosiVo> page = new PageInfo<>(positionInfoMapper.selectForPage(loginUserId,DataEnum.FLAG_STATUS_INVALID.getCode(),pst));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}