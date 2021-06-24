package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.backstage.dao.mapper.UserInfoMapper;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
 
/**
 * 用户信息(UserInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-06-24 10:48:03
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private UserInfoMapper userInfoMapper;
 
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, UserInfo userInfo) {
        if(userInfo.getUserId() == null){
            return insert(loginUserId, userInfo);
        } else {
            return update(loginUserId,userInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, UserInfo userInfo) {
        userInfo.setAddUserId(loginUserId);
        return userInfoMapper.insertSelective(userInfo);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer userId) {
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        userInfo.setEditUserId(loginUserId);
        userInfo.setEditDate(new Date());
        userInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }
 
    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, UserInfo userInfo){
		UserInfo u = userInfoMapper.selectByPrimaryKey(userInfo.getUserId());
		// FIXME 待完善
        return userInfoMapper.updateByPrimaryKeySelective(u);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public UserInfo queryById(Integer userId){
		return userInfoMapper.selectByPrimaryKey(userId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<UserInfo> list(Integer loginUserId, UserInfo userInfo) {
        PageHelper.startPage(userInfo.getPageNum(), userInfo.getPageSize());
        userInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<UserInfo> page = new PageInfo<>(userInfoMapper.select(userInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
}