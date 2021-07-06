package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.entity.SysUserInfo;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.entity.sysentity.*;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.SysUserInfoService;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.shiro.JwtUtil;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

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
    @Resource
    private UserInfoService userInfoService;
 
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
     * @param ManageUserId 系统管理用户id
     * @param sysUserInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询后台管理系统用户表的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "后台管理系统用户表对象", required = true) @RequestBody SysUserInfo sysUserInfo) {
        return ResponseHelper.success(sysUserInfoService.list(Integer.valueOf(ManageUserId), sysUserInfo));
    }

    /**
     * 管理端-查询用户列表
     * @param ManageUserId
     * @param manUserVo
     * @return
     */
    @PostMapping("queryInfoList")
    @ApiOperation(value = "列表", notes = "查询后台管理系统-用户表-的多条数据")
    public ResponseModel queryInfoList(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody ManUserVo manUserVo) {
        return ResponseHelper.success(sysUserInfoService.queryManUserVoForPage(Integer.valueOf(ManageUserId), manUserVo));
    }
    /**
     * 管理端-查询提现记录
     * @param ManageUserId
     * @param manCashVo
     * @return
     */
    @PostMapping("queryCashDetail")
    @ApiOperation(value = "列表", notes = "查询后台管理系统-提现记录-的多条数据")
    public ResponseModel queryCashDetail(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody ManCashVo manCashVo) {
        return ResponseHelper.success(sysUserInfoService.queryManCashVoForPage(Integer.valueOf(ManageUserId), manCashVo));
    }
    /**
     * 管理端-查询提现记录
     * @param ManageUserId
     * @param manOrderVo
     * @return
     */
    @PostMapping("queryOrderDataList")
    @ApiOperation(value = "列表", notes = "查询后台管理系统-订单表-多条数据")
    public ResponseModel queryOrderDataList(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody ManOrderVo manOrderVo) {
        return ResponseHelper.success(sysUserInfoService.queryManOrderVoForPage(Integer.valueOf(ManageUserId),manOrderVo));
    }
    /**
     * 管理端-查询平仓记录
     * @param ManageUserId
     * @param manOrderVo
     * @return
     */
    @PostMapping("queryManRecordVoForPage")
    @ApiOperation(value = "列表", notes = "查询后台管理系统-资金记录表-多条数据")
    public ResponseModel queryManRecordVoForPage(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody ManRecordVo manOrderVo) {
        return ResponseHelper.success(sysUserInfoService.queryManRecordVoForPage(Integer.valueOf(ManageUserId),manOrderVo));
    }
    /**
     * 管理端-查询银行卡
     * @param ManageUserId
     * @param manOrderVo
     * @return
     */
    @PostMapping("queryBankVoForPage")
    @ApiOperation(value = "列表", notes = "查询后台管理系统-资金记录表-多条数据")
    public ResponseModel queryBankVoForPage(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody ManBankVo manOrderVo) {
        return ResponseHelper.success(sysUserInfoService.queryManBankVoForPage(Integer.valueOf(ManageUserId),manOrderVo));
    }
    /**
     * 管理端-资金报表
     * @param ManageUserId
     * @param accountInfo
     * @return
     */
    @PostMapping("moneyTableData")
    @ApiOperation(value = "列表", notes = "查询后台管理系统-资金记录表-多条数据")
    public ResponseModel queryMoneyTableData(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody AccountInfo accountInfo) {
        return ResponseHelper.success(sysUserInfoService.queryAccountInfoForPage(Integer.valueOf(ManageUserId),accountInfo));
    }
    /**
     * 管理端-资金汇总
     * @param ManageUserId
     * @param manMoneyVo
     * @return
     */
    @PostMapping("moneyTableTotal")
    @ApiOperation(value = "列表", notes = "查询后台管理系统-资金记录表-汇总数据")
    public ResponseModel moneyTableTotal(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody ManMoneyVo manMoneyVo) {
        return ResponseHelper.success(sysUserInfoService.queryManMoneyVo(Integer.valueOf(ManageUserId),manMoneyVo));
    }

    /**
     * 管理端-创建新用户
     * @param ManageUserId
     * @param manUserVo
     * @return
     */
    @PostMapping("addNewOne")
    @ApiOperation(value = "列表", notes = "后台系统添加用户")
    public ResponseModel addNewUser(@ApiParam(value = "用户ID", required = false)@LoginUserId String ManageUserId, @ApiParam(value = "用户表对象", required = true) @RequestBody UserInfo manUserVo) {
        return ResponseHelper.success(userInfoService.addNewUser(Integer.valueOf(ManageUserId), manUserVo));
    }

    /**
     * 系统管理员登陆
     * @param sysUserInfo
     * @return
     */
    @PostMapping("login")
    public ResponseModel login(@ApiParam(value = "系统用户信息对象", required = true) @RequestBody SysUserInfo sysUserInfo) {

        SysUserInfo sysUser = sysUserInfoService.login(sysUserInfo);
        if (sysUser != null) {

            if (Objects.equals(DataEnum.FLAG_STATUS_VALID.getCode(), sysUser.getDel())) {
                throw new LockedAccountException(DataEnum.FLAG_STATUS_VALID.getDesc());
            } else {

                String token = JwtUtil.sign(String.valueOf(sysUser.getSysUserId()), sysUserInfo.getSysUserPassword());
                return ResponseHelper.success(token);
            }
        } else {
            return ResponseHelper.failedWith(DataEnum.USER_ERROR.getDesc());
        }
    }

    /**
     * 根据token的用户id
     *
     * @return 实例对象
     */
    @GetMapping("getUserInfo")
    @ApiOperation(value = "查询单条数据", notes = "根据token的用户id}")
    public ResponseModel getUserInfo(@ApiParam(value = "用户信息主键userId", required = true)@LoginUserId  String loginUserId){
        return ResponseHelper.success(sysUserInfoService.getUserInfo(Integer.valueOf(loginUserId)));
    }
    /**
     * 查询首页统计数据
     *
     * @return 实例对象
     */
    @GetMapping("queryHomeData")
    @ApiOperation(value = "查询首页统计数据", notes = "根据token的用户id}")
    public ResponseModel queryHomeData(@ApiParam(value = "用户信息主键userId", required = true)@LoginUserId  String loginUserId,@RequestParam String endTime){
//        return ResponseHelper.success(sysUserInfoService.queryHomePageData());
        return ResponseHelper.success(sysUserInfoService.queryHomePageDataForDate(endTime));
    }

    /**
     * logout
     *
     * @return 实例对象
     */
    @GetMapping("logout")
    @ApiOperation(value = "查询单条数据", notes = "根据token的用户id}")
    public ResponseModel logout(@ApiParam(value = "用户信息主键userId", required = true)@LoginUserId  String loginUserId){
        return ResponseHelper.success("success");
    }
 
}