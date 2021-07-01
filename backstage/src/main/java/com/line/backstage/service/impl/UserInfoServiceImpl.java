package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.line.backstage.dao.mapper.UserInfoMapper;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.utils.PasswordHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 用户信息(UserInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-06-24 10:48:03
 */
@Slf4j
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

    @Override
    public UserInfo login(UserInfo userInfo) {

        // XXX 加密密码
        String encryptPwd = PasswordHelper.encryptPassword(userInfo.getUserPhone(), userInfo.getUserPassword());
        userInfo.setUserPassword(encryptPwd);
        log.info("用户登录密码【{}】", encryptPwd);

        // 查找用户是否存在
        return userInfoMapper.selectOne(userInfo);
    }

    @Override
    public Map getUserInfo(String userId) {

        //FIXME 待优化，用户信息加入redis缓存
        UserInfo user = queryById(Integer.valueOf(userId));

        //获取用户的角色、权限字符串及菜单路由
        Set<String> roles = Sets.newHashSet();
        roles.add(user.getRole().getRoleName());
//        List<SysMenu> menus = menuMapper.findByRoleId(user.getRoleId());
        Set<String> permissions = Sets.newHashSet();
//        for (SysMenu menu : menus) {
//            if (StringUtils.isNotBlank(menu.getPermission())) {
//                permissions.add(menu.getPermission());
//            }
//        }
//        //菜单转换为vue router路由对象
//        List<MenuRouteVo> routes = queryMenuTree(menus);
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("roles", roles);
        resMap.put("perms", permissions);
//        resMap.put("menus", routes);
//        resMap.put("name", user.getNickname());
//        resMap.put("avatar", user.getPhoto());
        return resMap;
    }
}