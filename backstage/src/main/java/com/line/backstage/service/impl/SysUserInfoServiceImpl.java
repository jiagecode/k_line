package com.line.backstage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.line.backstage.dao.mapper.*;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.entity.SysMenuInfo;
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.entity.sysentity.*;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SysUserInfoService;
import com.line.backstage.utils.DateUtil;
import com.line.backstage.utils.DateUtils;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.utils.PasswordHelper;
import com.line.backstage.vo.MenuRouteVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 后台管理系统用户表(SysUserInfo)表服务实现类
 *
 * @author Zy
 * @since 2021-07-01 11:36:19
 */
@Slf4j
@Service("sysUserInfoService")
public class SysUserInfoServiceImpl implements SysUserInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Resource
    private SysMenuInfoMapper sysMenuInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private CashOutInMapper cashOutInMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private AccountRecordMapper accountRecordMapper;
    @Resource
    private BankCardInfoMapper bankCardInfoMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;

    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysUserInfo sysUserInfo) {
        if(sysUserInfo.getSysUserId() == null){
            return insert(loginUserId, sysUserInfo);
        } else {
            return update(loginUserId,sysUserInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysUserInfo sysUserInfo) {
        sysUserInfo.setAddUserId(loginUserId);
        return sysUserInfoMapper.insertSelective(sysUserInfo);
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
		SysUserInfo sysUserInfo = sysUserInfoMapper.selectByPrimaryKey(userId);
        sysUserInfo.setEditUserId(loginUserId);
        sysUserInfo.setEditDate(new Date());
        sysUserInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        return sysUserInfoMapper.updateByPrimaryKeySelective(sysUserInfo);
    }
 
    /**
     * 修改数据
     *
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysUserInfo sysUserInfo){
		SysUserInfo s = sysUserInfoMapper.selectByPrimaryKey(sysUserInfo.getSysUserId());
		// FIXME 待完善
        return sysUserInfoMapper.updateByPrimaryKeySelective(s);
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUserInfo queryById(Integer userId){
		return sysUserInfoMapper.selectByPrimaryKey(userId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysUserInfo> list(Integer loginUserId, SysUserInfo sysUserInfo) {
        PageHelper.startPage(sysUserInfo.getPageNum(), sysUserInfo.getPageSize());
        sysUserInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        PageInfo<SysUserInfo> page = new PageInfo<>(sysUserInfoMapper.select(sysUserInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
    /**
     * 分页查询用户列表
     * @param loginUserId
     * @param userVo
     * @return
     */
    @Override
    public PageWrapper<ManUserVo> queryManUserVoForPage(Integer loginUserId, ManUserVo userVo) {
        PageHelper.startPage(userVo.getPageNum(), userVo.getPageSize());
        if(userVo.getDel() == null){
            //可查无效用户 默认查有效
            userVo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        }
        if(userVo.getQueryDateFlag() != null){
            if(userVo.getBeginDate() == null){
                userVo.setBeginDate(DateUtil.getStartTimeOfToday());
            }
            if(userVo.getEndDate() == null){
                userVo.setEndDate(DateUtil.getEndTimeOfDay(new Date()));
            }
        }
        PageInfo<ManUserVo> page = new PageInfo<>(userInfoMapper.queryManUserVoList(userVo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public PageWrapper<ManCashVo> queryManCashVoForPage(Integer loginUserId, ManCashVo manCashVo) {
        PageHelper.startPage(manCashVo.getPageNum(), manCashVo.getPageSize());
        if(manCashVo.getDel() == null){
            manCashVo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        }
        PageInfo<ManCashVo> page = new PageInfo<>(cashOutInMapper.queryManCashVoList(manCashVo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public PageWrapper<ManOrderVo> queryManOrderVoForPage(Integer loginUserId, ManOrderVo manOrderVo) {
        PageHelper.startPage(manOrderVo.getPageNum(), manOrderVo.getPageSize());
        if(manOrderVo.getDel() == null){
            manOrderVo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        }
        Integer userType = manOrderVo.getUserType();
        Integer investType = manOrderVo.getInvestType();
        Integer orderStatus = manOrderVo.getOrderStatus();
        Integer queryDataFlag = manOrderVo.getQueryDataFlag();
        if(userType!= null && userType!= 1 && userType!=2){
            manOrderVo.setUserType(null);
        }
        if(investType!= null && investType!= 1 && investType!=2){
            manOrderVo.setInvestType(null);
        }
        if(orderStatus!= null && orderStatus!= 1 && orderStatus!=2){
            manOrderVo.setOrderStatus(null);
        }
        if(queryDataFlag!= null && queryDataFlag!= 1 && queryDataFlag!=2){
            manOrderVo.setQueryDataFlag(null);
        }
        if(manOrderVo.getBeginDate() == null && manOrderVo.getEndDate() == null){
            manOrderVo.setBeginDate(DateUtil.getStartTimeOfToday());
            manOrderVo.setEndDate(DateUtil.getEndTimeOfDay(new Date()));
        }
        manOrderVo.setTodayNum(DateUtil.getTodayIntNum());
        PageInfo<ManOrderVo> page = new PageInfo<>(orderInfoMapper.queryManOrderVoForPage(manOrderVo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public PageWrapper<ManRecordVo> queryManRecordVoForPage(Integer loginUserId, ManRecordVo recordVo) {
        PageHelper.startPage(recordVo.getPageNum(), recordVo.getPageSize());
        if(recordVo.getDel() == null){
            recordVo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        }
        Integer queryType = recordVo.getQueryType();
        if(queryType!=null && queryType ==1 &&recordVo.getBeginDate() == null && recordVo.getEndDate() == null){
            //查询平仓记录
            recordVo.setBeginDate(DateUtil.getStartTimeOfToday());
            recordVo.setEndDate(DateUtil.getEndTimeOfDay(new Date()));
        }
        recordVo.setRecordType(DataEnum.RECORD_TYPE4.getCode());
        PageInfo<ManRecordVo> page = new PageInfo<>(accountRecordMapper.queryManRecordVoForPage(recordVo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public PageWrapper<ManBankVo> queryManBankVoForPage(Integer loginUserId, ManBankVo recordVo) {
        PageHelper.startPage(recordVo.getPageNum(), recordVo.getPageSize());
        if(recordVo.getDel() == null){
            recordVo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        }
        PageInfo<ManBankVo> page = new PageInfo<>(bankCardInfoMapper.queryManBankVoForPage(recordVo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public PageWrapper<AccountInfo> queryAccountInfoForPage(Integer loginUserId, AccountInfo accountInfo) {
        PageHelper.startPage(accountInfo.getPageNum(), accountInfo.getPageSize());
        if(accountInfo.getDel() == null){
            accountInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        }
        accountInfo.setTodayNum(DateUtil.getTodayIntNum());
        PageInfo<AccountInfo> page = new PageInfo<>(accountInfoMapper.queryForPage(accountInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }

    @Override
    public ManMoneyVo queryManMoneyVo(Integer loginUserId, ManMoneyVo manMoneyVo) {
       if(manMoneyVo.getDel() == null){
           manMoneyVo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
       }
        manMoneyVo.setTodayNum(DateUtil.getTodayIntNum());
        ManMoneyVo  vo =  accountInfoMapper.queryManMoneyVo(manMoneyVo);
        if(vo == null){
            vo = new ManMoneyVo();
        }
        if(vo.getAllInMoney() == null){
            vo.setAllInMoney(0.0);
        }
        if(vo.getAllMoney() == null){
            vo.setAllMoney(0.0);
        }
        if(vo.getAllOutMoney() == null){
            vo.setAllOutMoney(0.0);
        }
        if(vo.getTodayMoney() == null){
            vo.setTodayMoney(0.0);
        }
        if(vo.getAllCommission() == null){
            vo.setAllCommission(0.0);
        }
        if(vo.getAllBonus() == null){
            vo.setAllBonus(0.0);
        }
        return vo;
    }

    @Override
    public SysUserInfo login(SysUserInfo sysUserInfo) {

        // XXX 加密密码
        String encryptPwd = PasswordHelper.encryptPassword(sysUserInfo.getSysUserCode(), sysUserInfo.getSysUserPassword());
        sysUserInfo.setSysUserPassword(encryptPwd);
        log.info("系统用户登录密码【{}】", encryptPwd);

        // 查找用户是否存在
        return sysUserInfoMapper.selectOne(sysUserInfo);
    }

    @Override
    public Map getUserInfo(Integer userId) {

        //FIXME 待优化，用户信息加入redis缓存红利BONUS 佣金
        SysUserInfo user = sysUserInfoMapper.selectByPrimaryKey(userId);

        //获取用户的角色、权限字符串及菜单路由
        Set<String> roles = Sets.newHashSet();
        roles.add(user.getSysUserName());
        List<SysMenuInfo> menus = sysMenuInfoMapper.queryMenuListByRoleId(user.getSysRoleId());
        Set<String> permissions = Sets.newHashSet();

        // 权限暂时不要
        for (SysMenuInfo menu : menus) {
            if (StringUtils.isNotBlank(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        // 菜单转换为vue router路由对象
        List<MenuRouteVo> routes = queryMenuTree(menus);
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("account", user.getSysUserId());
        resMap.put("avatar", user.getSysUserImg());
        resMap.put("roles", roles);
        resMap.put("perms", permissions);
        resMap.put("menus", routes);
        resMap.put("name", user.getSysUserName());
        return resMap;
    }

    /**
     * 获取树形结构数据
     *
     * @param menus
     * @return
     */
    public List<MenuRouteVo> queryMenuTree(List<SysMenuInfo> menus) {

        //查找所有父级ID = 0的数据，一级节点
        List<SysMenuInfo> oneList = Lists.newArrayList();

        for (SysMenuInfo sysMenu : menus) {
            if (SysMenuInfo.ROOT.equals(sysMenu.getParentId())) {
                oneList.add(sysMenu);
            }
        }
        List<MenuRouteVo> resList = Lists.newArrayList();
        for (SysMenuInfo one : oneList) {
            MenuRouteVo menuRouteVo = new MenuRouteVo();
            menuRouteVo.setName(one.getMenuName());
            menuRouteVo.setPath(one.getMenuHref());
            MenuRouteVo.Meta meta = new MenuRouteVo.Meta();
            meta.setTitle(one.getMenuName());
            meta.setIcon(one.getMenuIcon());
            menuRouteVo.setMeta(meta);

            List<MenuRouteVo> treeData = getMapTree(one.getMenuId(), menus);
            if (!treeData.isEmpty()) {
                menuRouteVo.setChildren(treeData);
            }
            resList.add(menuRouteVo);
        }
        return resList;
    }

    /**
     * 递归生成树形结构数据
     *
     * @param menuId
     * @param list
     * @return {"id": ,"label": ,"children":[]}格式数据
     */
    public List<MenuRouteVo> getMapTree(Integer menuId, List<SysMenuInfo> list) {

        List<SysMenuInfo> childList = getChildren(list, menuId);
        List<MenuRouteVo> treeList = Lists.newArrayList();
        for (SysMenuInfo sysMenu : childList) {
            MenuRouteVo menuRouteVo = new MenuRouteVo();
            menuRouteVo.setName(sysMenu.getMenuName());
            menuRouteVo.setPath(sysMenu.getMenuHref());
            MenuRouteVo.Meta meta = new MenuRouteVo.Meta();
            meta.setTitle(sysMenu.getMenuName());
            meta.setIcon(sysMenu.getMenuIcon());
            menuRouteVo.setMeta(meta);
            List<MenuRouteVo> treeData = getMapTree(sysMenu.getMenuId(), list);
            if (!treeData.isEmpty()) {
                menuRouteVo.setChildren(treeData);
            }
            treeList.add(menuRouteVo);
        }
        return treeList;
    }

    /**
     * 获取当前节点的所有子节点
     *
     * @param menuList
     * @param menuId
     * @return
     */
    public List<SysMenuInfo> getChildren(List<SysMenuInfo> menuList, Integer menuId) {
        List<SysMenuInfo> children = Lists.newArrayList();
        for (SysMenuInfo menu : menuList) {
            if (menu.getParentId().equals(menuId)) {
                children.add(menu);
            }
        }
        return children;
    }
}