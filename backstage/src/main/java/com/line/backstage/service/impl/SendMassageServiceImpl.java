package com.line.backstage.service.impl;

import com.line.backstage.entity.SendMassage;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.dao.mapper.SendMassageMapper;
import com.line.backstage.service.SendMassageService;
import org.springframework.stereotype.Service;
import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;

/**
 * 聊天记录(SendMassage)表服务实现类
 *
 * @author fimex
 * @since 2021-08-12 14:29:14
 */
@Service("sendMassageService")
public class SendMassageServiceImpl implements SendMassageService {

    /**
     * 服务对象
     */
    @Resource
    private SendMassageMapper sendMassageMapper;

    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SendMassage sendMassage) {
        if(sendMassage.getId() == null){
            return insert(loginUserId, sendMassage);
        } else {
            return update(loginUserId,sendMassage);
        }
    }

    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SendMassage sendMassage) {
        sendMassage.setAddUserId(loginUserId);
        return sendMassageMapper.insertSelective(sendMassage);
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
		SendMassage sendMassage = sendMassageMapper.selectByPrimaryKey(id);
        sendMassage.setEditUserId(loginUserId);
        sendMassage.setEditTime(new Date());
        sendMassage.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return sendMassageMapper.updateByPrimaryKeySelective(sendMassage);
    }

    /**
     * 修改数据
     *
     * @param sendMassage 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SendMassage sendMassage){
		SendMassage s = sendMassageMapper.selectByPrimaryKey(sendMassage.getId());
		// FIXME 待完善
        return sendMassageMapper.updateByPrimaryKeySelective(s);
	}

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SendMassage queryById(Integer id){
		return sendMassageMapper.selectByPrimaryKey(id);
	}

    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SendMassage> list(Integer loginUserId, SendMassage sendMassage) {
        Condition condition = new Condition(SendMassage.class);
        condition.createCriteria()
                .andEqualTo("del", DataEnum.FLAG_STATUS_INVALID.getCode());
        condition.setOrderByClause("ADD_TIME desc");
        return new PageWrapper<>(new PageInfo<>(sendMassageMapper.selectByExample(condition)));
    }
}
