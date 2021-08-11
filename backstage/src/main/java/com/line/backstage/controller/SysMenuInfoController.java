package com.line.backstage.controller;

import com.line.backstage.annotation.Log;
import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.SysMenuInfo;
import com.line.backstage.service.SysMenuInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 后台管理系统菜单表(SysMenuInfo)表控制层
 *
 * @author jack
 * @since 2000-07-01 11:35:35
 */
@Api(tags = "后台管理系统菜单表(SysMenuInfo)") 
@RestController
@RequestMapping("sysMenuInfo")
public class SysMenuInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private SysMenuInfoService sysMenuInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("addOrUpdateOne")
    @ApiOperation(value = "新增", notes = "新增/修改后台管理系统菜单表的一条数据")
    @Log(description = "管理员编辑菜单",userType = "1")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统菜单表对象", required = true) @RequestBody @Validated SysMenuInfo sysMenuInfo) {
           if(sysMenuInfo != null && sysMenuInfo.getMenuId() != null){
               return ResponseHelper.success(sysMenuInfoService.updateForOne(Integer.valueOf(loginUserId), sysMenuInfo));
           }else {
               return ResponseHelper.success(sysMenuInfoService.insertForOne(Integer.valueOf(loginUserId), sysMenuInfo));
           }
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @return 是否成功
     */
    @PostMapping("delOne")
    @ApiOperation(value = "删除单条数据", notes = "删除主键menuId的单条数据")
    @Log(description = "管理员删除单条菜单数据",userType = "1")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId,@ApiParam(value = "后台管理系统菜单表对象", required = true) @RequestBody @Validated SysMenuInfo sysMenuInfo) {
            return ResponseHelper.success(sysMenuInfoService.deleteForOne(Integer.valueOf(loginUserId),sysMenuInfo));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{menuId}")
    @ApiOperation(value = "查询单条数据", notes = "通过menuId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "后台管理系统菜单表主键menuId", required = true) @PathVariable("menuId") Integer menuId){
        return ResponseHelper.success(sysMenuInfoService.queryById(menuId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysMenuInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("queryList")
    @ApiOperation(value = "列表", notes = "查询后台管理系统菜单表的多条数据")
    @Log(description = "查询后台管理系统菜单列表",userType = "1")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统菜单表对象", required = true) @RequestBody SysMenuInfo sysMenuInfo) {
        return ResponseHelper.success(sysMenuInfoService.queryMyMenuList(Integer.valueOf(loginUserId), sysMenuInfo));
    }
 
}