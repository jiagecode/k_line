package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.UserInfo;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.service.UserInfoService;
import com.line.backstage.shiro.JwtUtil;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import com.line.backstage.vo.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户信息(UserInfo)表控制层
 *
 * @author jack
 * @since 2000-06-24 10:29:24
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
     * H5新增用户
     * @param manUserVo
     * @return
     */
    @PostMapping("createUserInfo")
    @ApiOperation(value = "列表", notes = "H5新增用户")
    public ResponseModel createUserInfo(@ApiParam(value = "用户表对象", required = true) @RequestBody UserInfo manUserVo) {

        String s = userInfoService.createUserInfo(manUserVo);
        if (Objects.equals(s, "40002")) {
            return ResponseHelper.failedWith(ResultCode.TEL_NULL.getMessage());
        } else if (Objects.equals(s, "40001")) {
            return ResponseHelper.failedWith(ResultCode.TEL_EXIST.getMessage());
        } else {
            return ResponseHelper.success(s);
        }
    }
 
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
     * 根据token的用户id
     *
     * @return 实例对象
     */
    @GetMapping("getUserInfo")
    @ApiOperation(value = "查询单条数据", notes = "根据token的用户id}")
    public ResponseModel getUserInfo(@ApiParam(value = "用户信息主键userId", required = true) @LoginUserId String loginUserId){
        return ResponseHelper.success(userInfoService.queryById(Integer.valueOf(loginUserId)));
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

    /**
     * 登陆
     * @param userInfo
     * @return
     */
    @PostMapping("login")
    public ResponseModel login(@ApiParam(value = "用户信息对象", required = true) @RequestBody UserInfo userInfo) {

        UserInfo user = userInfoService.login(userInfo);
        if (user != null) {

            if (Objects.equals(DataEnum.USER_DEL_STATUS.getCode(), user.getDelStatus())) {
                throw new LockedAccountException(DataEnum.USER_DEL_STATUS.getDesc());
            } else if (Objects.equals(DataEnum.USER_FORBID_FLAG.getCode(), user.getUserForbidFlag())) {
                throw new LockedAccountException(DataEnum.USER_FORBID_FLAG.getDesc());
            } else {
                String token = JwtUtil.sign(String.valueOf(user.getUserId()), userInfo.getUserPassword());
                return ResponseHelper.success(token);
            }
        } else {
            return ResponseHelper.failedWith(DataEnum.USER_ERROR.getDesc());
        }
    }
}