package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.line.backstage.dao.mapper.AccountInfoMapper;
import com.line.backstage.dao.mapper.UserInfoMapper;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.shiro.JwtUtil;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.utils.PasswordHelper;
import com.line.backstage.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 用户信息(UserInfo)表服务实现类
 *
 * @author jack
 * @since 2000-06-24 10:48:03
 */
@Slf4j
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    /**
     * 服务对象
     */
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;

    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param userInfo    实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, UserInfo userInfo) {
        if (userInfo.getUserId() == null) {
            return insert(loginUserId, userInfo);
        } else {
            return update(loginUserId, userInfo);
        }
    }

    /**
     * 创建新用户并创建账户
     * @param loginUserId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addNewUser(Integer loginUserId, UserInfo userInfo) {

        String tel = userInfo.getUserPhone();
        if(StringUtils.isEmpty(tel)){
            return ResultCode.TEL_NULL.getCode();
        }
        if(userInfoMapper.queryUserIdForPhone(tel) != null ){
            return ResultCode.TEL_EXIST.getCode();
        }
        if(userInfo.getUserType() == null){
            userInfo.setUserType(1);
        }
        if(userInfo.getUserType() != 1 && userInfo.getUserType() != 2){
            userInfo.setUserType(1);
        }
        Date date = new Date();
        userInfo.setAddDate(date);
        userInfo.setUserRegisterDate(date);
        userInfo.setEditDate(date);
        // XXX 设置加密密码
        String encryptPwd = PasswordHelper.encryptPassword(userInfo.getUserPhone(), userInfo.getUserPassword());
        userInfo.setUserPassword(encryptPwd);
        if (loginUserId != -1) {

            userInfo.setEditUserId(loginUserId);
            userInfo.setAddUserId(loginUserId);
        }
        userInfo.setUserGender(1);
        userInfo.setUserLevel(1);
        userInfo.setUserForbidFlag(0);
        userInfo.setUserForbidFlag(0);
        userInfo.setDelStatus(0);
        userInfo.setUserRefereeAble(1);
        userInfo.setDel(1);
        userInfo.setDiyFlag(0);
        if(userInfo.getAgentId() != null){
            userInfo.setAgentName(userInfoMapper.queryAgentNameByAgentId(userInfo.getAgentId()));
        }
        int newId  = userInfoMapper.insertSelective(userInfo);
        if(newId == 1){
            newId = userInfoMapper.queryUserIdForPhone(tel);
            userInfo.setUserId(newId);
        }
       // System.out.println("创建用户得新id：" + newId);
        if(2 == userInfo.getUserType()){
            //用户为代理
            userInfo.setAgentId(newId);
            userInfo.setUserId(newId);
            userInfo.setAgentName(userInfo.getUserNickName());
            userInfo.setAgentTime(date);
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setDel(1);
        accountInfo.setUserId(newId);
        accountInfo.setAddDate(date);
        accountInfo.setEditDate(date);
        if (loginUserId != -1) {
            accountInfo.setEditUserId(loginUserId);
            accountInfo.setAddUserId(loginUserId);
        }
        accountInfo.setOrderNum(0);
        accountInfo.setAccountMoney(0.0);
        accountInfo.setAccountStatus(0);
        int r = accountInfoMapper.insert(accountInfo);
        return loginUserId == -1 ? -1 : r;
    }

    /**
     * H5新增用户
     * 注册没有 userLoginId -1来区分
     * @param userInfo
     * @return
     */
    @Override
    public String createUserInfo(UserInfo userInfo) {
        userInfo.setUserNickName(userInfo.getUserRealName());
        int result = addNewUser(-1, userInfo);
        return result == -1 ? JwtUtil.sign(String.valueOf(userInfo.getUserId()), userInfo.getUserPassword()) : result + "";
    }

    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param userInfo    实例对象
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
     * @param userId      主键
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
    public int update(Integer loginUserId, UserInfo userInfo) {
        UserInfo u = userInfoMapper.selectByPrimaryKey(userInfo.getUserId());
        if(u != null){
            if(userInfo.getAgentId() != null){
                userInfo.setAgentName(userInfoMapper.queryAgentNameByAgentId(userInfo.getAgentId()));
            }
            return userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
        // FIXME 待完善
       return 0;
    }

    @Override
    public int updateUserType(Integer loginUserId, UserInfo userInfo) {
        UserInfo u = userInfoMapper.selectByPrimaryKey(userInfo.getUserId());
        if(u != null){
            userInfo.setAgentId(u.getUserId());
            Date date = new Date();
            userInfo.setAgentTime(date);
            userInfo.setEditUserId(loginUserId);
            userInfo.setAgentName(StringUtils.isEmpty(u.getUserNickName())?u.getUserRealName():u.getUserNickName());
            userInfo.setEditDate(date);
            userInfo.setUserType(2);
            return userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }
        return 0;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public UserInfo queryById(Integer userId) {
        UserInfo dto = userInfoMapper.selectByPrimaryKey(userId);
        if (dto != null) {
//            UserInfo info = new UserInfo();
//            info.setUserHeadImg(dto.getUserHeadImg());
//            info.setUserNickName(dto.getUserNickName());
            Double money = accountInfoMapper.queryMyMoneyByUserId(userId);
            if (money == null) {
                money = 0.0;
            }
            dto.setUserMoney(money);
            return dto;
        }
        return null;
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param userInfo    查询条数
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

    @Override
    public void updateLastLoginDate(Integer loginUserId) {
        userInfoMapper.updateLastLoginDate(loginUserId);
    }
}