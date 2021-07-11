package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.SysRoleInfo;
import com.line.backstage.service.SysRoleInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 后台管理系统角色表(SysRoleInfo)表控制层
 *
 * @author Zy
 * @since 2021-07-01 11:36:06
 */
@Api(tags = "后台管理系统角色表(SysRoleInfo)") 
@RestController
@RequestMapping("sysRoleInfo")
public class SysRoleInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private SysRoleInfoService sysRoleInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改后台管理系统角色表的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统角色表对象", required = true) @RequestBody @Validated SysRoleInfo sysRoleInfo) {
            return ResponseHelper.success(sysRoleInfoService.save(Integer.valueOf(loginUserId), sysRoleInfo));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param
     * @return 是否成功
     */
    @PostMapping("delete")
    @ApiOperation(value = "删除单条数据", notes = "删除主键roleId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统角色表主键roleId", required = true) @RequestBody Map<String ,Object> map) {
        Integer roleId = (Integer) map.get("roleId");
           if(roleId != null){
               return ResponseHelper.success(sysRoleInfoService.delete(Integer.valueOf(loginUserId), roleId));
           }
        return ResponseHelper.failedWith("查询失败");
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{roleId}")
    @ApiOperation(value = "查询单条数据", notes = "通过roleId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "后台管理系统角色表主键roleId", required = true) @PathVariable("roleId") Integer roleId){
        return ResponseHelper.success(sysRoleInfoService.queryById(roleId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysRoleInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询后台管理系统角色表的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统角色表对象", required = true) @RequestBody SysRoleInfo sysRoleInfo) {
        return ResponseHelper.success(sysRoleInfoService.list(Integer.valueOf(loginUserId), sysRoleInfo));
    }
 
}