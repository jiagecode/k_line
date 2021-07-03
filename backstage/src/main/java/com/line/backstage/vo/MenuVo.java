package com.line.backstage.vo;



import com.line.backstage.entity.SysMenuInfo;

import java.util.List;

/**
 * 菜单管理
 * @author pc
 */
public class MenuVo {

    private SysMenuInfo menu;
    private List<MenuVo> children;

    public SysMenuInfo getMenu() {
        return menu;
    }

    public void setMenu(SysMenuInfo menu) {
        this.menu = menu;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }
}
