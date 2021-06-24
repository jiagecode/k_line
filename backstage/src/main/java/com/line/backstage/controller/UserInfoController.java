package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 用户信息(UserInfo)表控制层
 *
 * @author Zy
 * @since 2021-06-24 10:29:24
 */
@Api(tags = "用户信息(UserInfo)") 
@RestController
@RequestMapping("userInfo")
public class UserInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改用户信息的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户信息对象", required = true) @RequestBody @Validated UserInfo userInfo) {
            return ResponseHelper.success(userInfoService.save(Integer.valueOf(loginUserId), userInfo));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param userId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{userId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键userId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户信息主键userId", required = true) @PathVariable("userId") Integer userId) {
            return ResponseHelper.success(userInfoService.delete(Integer.valueOf(loginUserId), userId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{userId}")
    @ApiOperation(value = "查询单条数据", notes = "通过userId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "用户信息主键userId", required = true) @PathVariable("userId") Integer userId){
        return ResponseHelper.success(userInfoService.queryById(userId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param userInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询用户信息的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户信息对象", required = true) @RequestBody UserInfo userInfo) {
        return ResponseHelper.success(userInfoService.list(Integer.valueOf(loginUserId), userInfo));
    }
 
}