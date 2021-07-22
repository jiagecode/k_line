package com.line.backstage.service.impl;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.line.backstage.dao.mapper.SysMenuInfoMapper;
import com.line.backstage.dao.mapper.SysPowerMapper;
import com.line.backstage.dao.mapper.SysUserInfoMapper;
import com.line.backstage.entity.SysMenuInfo;
import com.line.backstage.entity.SysPower;
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SysMenuInfoService;
import com.line.backstage.utils.PageWrapper;
import com.line.backstage.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 菜单表(SysMenuInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-03 10:27:30
 */
@Service("sysMenuInfoService")
public class SysMenuInfoServiceImpl implements SysMenuInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysMenuInfoMapper sysMenuInfoMapper;
    @Resource
    private SysUserInfoMapper sysUserInfoMapper;
    @Resource
    private SysPowerMapper sysPowerMapper;
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
        sysMenuInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        PageInfo<SysMenuInfo> page = new PageInfo<>(sysMenuInfoMapper.select(sysMenuInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
///////////////////////////add by bean///////////////////////////////////////////
    /**
     * 新增菜单信息
     * @param userId
     * @param menu
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertForOne(Integer userId, SysMenuInfo menu) {
        menu.setAddUserId(userId);
        if (menu.getParentId() == null || 0 == menu.getParentId()) {
            // 默认为根目录
            menu.setParentId(0);
            menu.setParentIds(SysMenuInfo.ROOT + ",");
            menu.setTreeSorts(menu.getTreeSort() + ",");
            menu.setTreeNames(menu.getMenuName());
            // 默认节点层级1级
            menu.setTreeLevel(1);
            // 新增则为叶子节点(最下级)
            menu.setTreeLeaf("1");
        } else {

            Integer parentId = menu.getParentId();
            SysMenuInfo parent = sysMenuInfoMapper.selectByPrimaryKey(parentId);
            // 上级节点追加
            menu.setParentIds(parent.getParentIds() + parentId + ",");
            // 排序追加
            menu.setTreeSorts(parent.getTreeSorts() + menu.getTreeSort() + ",");
            // 上级节点名称追加
            menu.setTreeNames(parent.getTreeNames() + "-" + menu.getMenuName());
            //  叶子节点追加（层级深度）
            menu.setTreeLevel(parent.getTreeLevel() + 1);
            // 新增则为叶子节点(最下级)
            menu.setTreeLeaf("1");
            // 因为添加了子节点，验证父级是否为叶子节点，是则修改状态
            if ("1".equals(parent.getTreeLeaf())) {
                // 0-不是叶子节点
                parent.setTreeLeaf("0");
                parent.setEditUserId(userId);
                parent.setEditTime(new Date());
                sysMenuInfoMapper.updateByPrimaryKeySelective(parent);
            }
        }

        int result = sysMenuInfoMapper.insertSelective(menu);

        if(1 == userId){
            SysUserInfo info = new SysUserInfo();
            info.setSysUserId(userId);
            SysUserInfo au = sysUserInfoMapper.selectByPrimaryKey(info);
            SysPower rm = new SysPower();
            rm.setMenuId(menu.getMenuId());
            rm.setRoleId(au.getSysRoleId());
            return sysPowerMapper.insertSelective(rm);
        }
        return result;
    }

    @Override
    public int deleteForOne(Integer userId, SysMenuInfo menu) {
         menu = sysMenuInfoMapper.selectByPrimaryKey(menu.getMenuId());
         if(menu != null){
             menu.setEditUserId(userId);
             menu.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
             menu.setEditTime(new Date());
         }
       return sysMenuInfoMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int updateForOne(Integer userId, SysMenuInfo menu) {
        SysMenuInfo old = sysMenuInfoMapper.selectByPrimaryKey(menu.getMenuId());
        old.setEditUserId(userId);
        old.setEditTime(new Date());
        // 名称更新
        old.setMenuName(menu.getMenuName());
        // 排序号更新
        old.setTreeSort(menu.getTreeSort());
        // 路劲更新
        old.setMenuHref(menu.getMenuHref());
        // 图标更新
        old.setMenuIcon(menu.getMenuIcon());
        // 组件更新
        old.setMenuComponent(menu.getMenuComponent());
        // 权限更新
        old.setPermission(menu.getPermission());

        Integer oldParentId = old.getParentId();
        String oldParentIds = old.getParentIds();
        String oldTreeSorts = old.getTreeSorts();
        String oldTreeNames = old.getTreeNames();
        int treeLevel = old.getTreeLevel();

        int changeLevel;
        // 没有上级目录的处理
        if (menu.getParentId() == null || menu.getParentId().intValue() == 0) {

            old.setParentId(0);
            old.setParentIds(SysMenuInfo.ROOT + ",");
            old.setTreeSorts(menu.getTreeSort() + ",");
            old.setTreeNames(menu.getMenuName());
            // 新增则为叶子节点(最下级)
            old.setTreeLevel(1);
            changeLevel = treeLevel - 1;
        } else {

            Integer parentId = menu.getParentId();
            SysMenuInfo parent = sysMenuInfoMapper.selectByPrimaryKey(parentId);
            // 上级节点追加
            old.setParentIds(parent.getParentIds() + parentId + ",");
            // 排序追加
            old.setTreeSorts(parent.getTreeSorts() + menu.getTreeSort() + ",");
            // 上级节点名称追加
            old.setTreeNames(parent.getTreeNames() + "-" + menu.getMenuName());
            //  叶子节点追加（层级深度）
            old.setTreeLevel(parent.getTreeLevel() + 1);
            changeLevel = treeLevel - old.getTreeLevel();
            //修改新节点状态
            if ("1".equals(parent.getTreeLeaf())) {
                // 0-不是叶子节点
                parent.setTreeLeaf("0");
                parent.setEditUserId(userId);
                parent.setEditTime(new Date());
                sysMenuInfoMapper.updateByPrimaryKeySelective(parent);
            }
        }
        // 更新当前节点
        int result = sysMenuInfoMapper.updateByPrimaryKeySelective(old);

        // 更新子节点
        List<SysMenuInfo> childList = sysMenuInfoMapper.findByParentIdsLike("," + old.getMenuId() + ",");

        for (SysMenuInfo item : childList) {
            item.setTreeLevel(item.getTreeLevel() - changeLevel);
            item.setMenuId(item.getMenuId());
            // replaceFirst 替换函数
            item.setParentIds(item.getParentIds().replaceFirst(oldParentIds, old.getParentIds()));
            item.setTreeSorts(item.getTreeSorts().replaceFirst(oldTreeSorts, old.getTreeSorts()));
            item.setTreeNames(item.getTreeNames().replaceFirst(oldTreeNames, old.getTreeNames()));
            sysMenuInfoMapper.updateByPrimaryKeySelective(item);
        }

        //最后处理，修改原父节点
        int childCount = sysMenuInfoMapper.getChildCountByMenuId("," + oldParentId + ",");
        if (childCount == 0) {
            old.setTreeLevel(1);
            sysMenuInfoMapper.updateByPrimaryKeySelective(old);
        }
        return result;
    }

    @Override
    public Map<String, Object> queryMyMenuList(Integer loginUserId, SysMenuInfo sysMenuInfo) {
        Integer roleId = sysMenuInfoMapper.queryRoleIdByUserId(loginUserId);
        List<SysMenuInfo> allMenus = sysMenuInfoMapper.queryMenuListByRoleId(roleId);
        List<SysMenuInfo> filter = Lists.newArrayList();
        for (SysMenuInfo menuInfo : allMenus){
            if(DataEnum.BUTTON.getCode().equals(menuInfo.getMenuType())){
                continue;
            }
            filter.add(menuInfo);
        }
        List<MenuVo> allList = queryMenuTree(allMenus);
        // 非按钮菜单
        List<MenuVo> menuTreeList = queryMenuTree(filter);

        Map<String, Object> map = Maps.newHashMap();
        map.put("menuList", allList);
        map.put("treeList", menuTreeList);
        return map;
    }

    /**
     * 获取树形结构数据
     *
     * @param menus
     * @return
     */
    private List<MenuVo> queryMenuTree(List<SysMenuInfo> menus) {

        // 查找所有父级ID = 0的数据，一级节点
        List<SysMenuInfo> oneList = Lists.newArrayList();
        for (SysMenuInfo menu : menus) {
            if (SysMenuInfo.ROOT.equals(menu.getParentId()) ){
                oneList.add(menu);
            }
        }

        return getMenuVos(menus, oneList);
    }

    /**
     * 获取树形表格展示数据接口
     *
     * @param menuId 一级菜单id
     * @param list   所有菜单集
     * @return
     */
    private List<MenuVo> getTreeData(Integer menuId, List<SysMenuInfo> list) {

        List<SysMenuInfo> childList = getChildren(list, menuId);
        return getMenuVos(list, childList);
    }

    /**
     * @param list      所有菜单集
     * @param childList 一级菜单集
     * @return
     */
    private List<MenuVo> getMenuVos(List<SysMenuInfo> list, List<SysMenuInfo> childList) {

        //一级菜单对应的下级集
        List<MenuVo> menuVoList = Lists.newArrayList();
        for (SysMenuInfo menu : childList) {
            MenuVo menuVo = new MenuVo();
            List<MenuVo> treeData = getTreeData(menu.getMenuId(), list);
            if (!treeData.isEmpty()) {

                menuVo.setChildren(treeData);
            }
            menuVo.setMenu(menu);
            menuVoList.add(menuVo);
        }
        return menuVoList;
    }

    /**
     * 获取当前节点的所有子节点
     *
     * @param menuList 所有菜单集
     * @param menuId   一级菜单id
     * @return 归属于一级菜单的信息
     */
    private List<SysMenuInfo> getChildren(List<SysMenuInfo> menuList, Integer menuId) {
        List<SysMenuInfo> children = Lists.newArrayList();
        for (SysMenuInfo menu : menuList) {
            if (menu.getParentId().equals(menuId)) {
                children.add(menu);
            }
        }
        return children;
    }
}