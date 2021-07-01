package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.service.SysUserInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 后台管理系统用户表(SysUserInfo)表控制层
 *
 * @author Zy
 * @since 2021-07-01 11:36:19
 */
@Api(tags = "后台管理系统用户表(SysUserInfo)") 
@RestController
@RequestMapping("sysUserInfo")
public class SysUserInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private SysUserInfoService sysUserInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改后台管理系统用户表的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统用户表对象", required = true) @RequestBody @Validated SysUserInfo sysUserInfo) {
            return ResponseHelper.success(sysUserInfoService.save(Integer.valueOf(loginUserId), sysUserInfo));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param sysUserId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{sysUserId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键sysUserId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统用户表主键sysUserId", required = true) @PathVariable("sysUserId") Integer sysUserId) {
            return ResponseHelper.success(sysUserInfoService.delete(Integer.valueOf(loginUserId), sysUserId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param sysUserId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{sysUserId}")
    @ApiOperation(value = "查询单条数据", notes = "通过sysUserId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "后台管理系统用户表主键sysUserId", required = true) @PathVariable("sysUserId") Integer sysUserId){
        return ResponseHelper.success(sysUserInfoService.queryById(sysUserId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysUserInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询后台管理系统用户表的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统用户表对象", required = true) @RequestBody SysUserInfo sysUserInfo) {
        return ResponseHelper.success(sysUserInfoService.list(Integer.valueOf(loginUserId), sysUserInfo));
    }
 
}