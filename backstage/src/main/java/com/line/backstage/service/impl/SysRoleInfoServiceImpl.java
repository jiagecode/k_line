package com.line.backstage.service.impl;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.line.backstage.dao.mapper.SysMenuInfoMapper;
import com.line.backstage.dao.mapper.SysRoleInfoMapper;
import com.line.backstage.entity.SysMenuInfo;
import com.line.backstage.entity.SysRoleInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SysRoleInfoService;
import com.line.backstage.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台管理系统角色表(SysRoleInfo)表服务实现类
 *
 * @author jack
 * @since 2000-07-01 11:36:06
 */
@Service("sysRoleInfoService")
public class SysRoleInfoServiceImpl implements SysRoleInfoService {
 
    /**
     * 服务对象
     */
    @Resource
    private SysRoleInfoMapper sysRoleInfoMapper;
    @Resource
    private SysMenuInfoMapper menuInfoMapper;
    /**
     * 保存数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int save(Integer loginUserId, SysRoleInfo sysRoleInfo) {
        if(sysRoleInfo.getRoleId() == null){
            return insert(loginUserId, sysRoleInfo);
        } else {
            return update(loginUserId,sysRoleInfo);
        }
    }
 
    /**
     * 新增数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int insert(Integer loginUserId, SysRoleInfo sysRoleInfo) {
        sysRoleInfo.setAddUserId(loginUserId);
        sysRoleInfo.setDel(1);
        sysRoleInfo.setEditUserId(loginUserId);
        sysRoleInfo.setEditTime(new Date());
        sysRoleInfo.setAddTime(new Date());
        if(sysRoleInfo.getRoleType() == null){
            sysRoleInfo.setRoleType(1);
        }
        sysRoleInfo.setLocked(1);
        return sysRoleInfoMapper.insertSelective(sysRoleInfo);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public int delete(Integer loginUserId, Integer roleId) {
		SysRoleInfo sysRoleInfo = sysRoleInfoMapper.selectByPrimaryKey(roleId);
        sysRoleInfo.setEditUserId(loginUserId);
        sysRoleInfo.setEditTime(new Date());
        sysRoleInfo.setDel(DataEnum.FLAG_STATUS_VALID.getCode());
        return sysRoleInfoMapper.updateByPrimaryKeySelective(sysRoleInfo);
    }
 
    /**
     * 修改数据
     *
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    @Override
    public int update(Integer loginUserId, SysRoleInfo sysRoleInfo){
		SysRoleInfo s = sysRoleInfoMapper.selectByPrimaryKey(sysRoleInfo.getRoleId());
		// FIXME 待完善
        if(s != null){
            s.setEditTime(new Date());
            s.setEditUserId(loginUserId);
            return sysRoleInfoMapper.updateByPrimaryKeySelective(sysRoleInfo);
        }
        return 0;
	}
 
    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public SysRoleInfo queryById(Integer roleId){
		return sysRoleInfoMapper.selectByPrimaryKey(roleId);
	}
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 查询条数
     * @return 对象列表
     */
    @Override
    public PageWrapper<SysRoleInfo> list(Integer loginUserId, SysRoleInfo sysRoleInfo) {
        PageHelper.startPage(sysRoleInfo.getPageNum(), sysRoleInfo.getPageSize());
        sysRoleInfo.setDel(DataEnum.FLAG_STATUS_INVALID.getCode());
        PageInfo<SysRoleInfo> page = new PageInfo<>(sysRoleInfoMapper.select(sysRoleInfo));
        PageHelper.clearPage();
        return new PageWrapper<>(page);
    }
    @Override
    public List<Map> authorize(Integer roleId) {
        // 获取角色菜单
        List<SysMenuInfo> menus = menuInfoMapper.findByRoleId(roleId);

        //查找所有父级ID = 0的数据，一级节点
        List<SysMenuInfo> oneList = Lists.newArrayList();
        for (SysMenuInfo menu : menus) {
            if (SysMenuInfo.ROOT.equals(Integer.valueOf(menu.getParentId()))) {
                oneList.add(menu);
            }
        }
        return getMaps(menus, oneList);
    }

    @Override
    public List<Integer> findMenuIdsByRoleId(Integer roleId) {
        return menuInfoMapper.findMenuIdsByRoleId(roleId);
    }

    /**
     * 所有菜单 & 一级菜单
     * 所有菜单 & 二级菜单
     * @param menus
     * @param oneList
     * @return
     */
    public List<Map> getMaps(List<SysMenuInfo> menus, List<SysMenuInfo> oneList) {
        List<Map> resList = Lists.newArrayList();
        for (SysMenuInfo one : oneList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", one.getMenuId());
            map.put("label", one.getMenuName());
            List<Map> treeData = getMapTree(one.getMenuId(), menus);
            if (!treeData.isEmpty()) {
                map.put("children", treeData);
            }
            resList.add(map);
        }
        return resList;
    }

    /**
     * 递归生成树形结构数据
     * 一级菜单ID & 所有菜单
     *
     * @param menuId
     * @param list
     * @return {"id": ,"label": ,"children":[]}格式数据
     */
    public List<Map> getMapTree(Integer menuId, List<SysMenuInfo> list) {
        List<SysMenuInfo> childList = getChildren(list, menuId);
        return getMaps(list, childList);
    }

    /**
     * 获取当前节点的所有子节点
     * 所有菜单 & 一级菜单ID
     *
     * @param menuList
     * @param menuId
     * @return 所有二级菜单
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