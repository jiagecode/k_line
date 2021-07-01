package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.AccountInfo;
import com.line.backstage.service.AccountInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 用户账户(AccountInfo)表控制层
 *
 * @author Zy
 * @since 2021-07-01 11:34:17
 */
@Api(tags = "用户账户(AccountInfo)") 
@RestController
@RequestMapping("accountInfo")
public class AccountInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private AccountInfoService accountInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改用户账户的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户账户对象", required = true) @RequestBody @Validated AccountInfo accountInfo) {
            return ResponseHelper.success(accountInfoService.save(Integer.valueOf(loginUserId), accountInfo));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param accountId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{accountId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键accountId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户账户主键accountId", required = true) @PathVariable("accountId") Integer accountId) {
            return ResponseHelper.success(accountInfoService.delete(Integer.valueOf(loginUserId), accountId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param accountId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{accountId}")
    @ApiOperation(value = "查询单条数据", notes = "通过accountId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "用户账户主键accountId", required = true) @PathVariable("accountId") Integer accountId){
        return ResponseHelper.success(accountInfoService.queryById(accountId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param accountInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询用户账户的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户账户对象", required = true) @RequestBody AccountInfo accountInfo) {
        return ResponseHelper.success(accountInfoService.list(Integer.valueOf(loginUserId), accountInfo));
    }
 
}