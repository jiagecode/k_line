package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.AccountRecord;
import com.line.backstage.service.AccountRecordService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 用户资金账户变动记录(AccountRecord)表控制层
 *
 * @author Zy
 * @since 2021-06-29 14:46:07
 */
@Api(tags = "用户资金账户变动记录(AccountRecord)") 
@RestController
@RequestMapping("accountRecord")
public class AccountRecordController {
 
    /**
     * 服务对象
     */
    @Resource
    private AccountRecordService accountRecordService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param accountRecord 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改用户资金账户变动记录的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户资金账户变动记录对象", required = true) @RequestBody @Validated AccountRecord accountRecord) {
            return ResponseHelper.success(accountRecordService.save(Integer.valueOf(loginUserId), accountRecord));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param recordId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{recordId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键recordId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户资金账户变动记录主键recordId", required = true) @PathVariable("recordId") Integer recordId) {
            return ResponseHelper.success(accountRecordService.delete(Integer.valueOf(loginUserId), recordId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{recordId}")
    @ApiOperation(value = "查询单条数据", notes = "通过recordId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "用户资金账户变动记录主键recordId", required = true) @PathVariable("recordId") Integer recordId){
        return ResponseHelper.success(accountRecordService.queryById(recordId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param accountRecord 查询条数
     * @return 对象列表
     */
    @PostMapping("querylist")
    @ApiOperation(value = "列表", notes = "查询用户资金账户变动记录的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户资金账户变动记录对象", required = true) @RequestBody AccountRecord accountRecord) {
        return ResponseHelper.success(accountRecordService.list(Integer.valueOf(loginUserId), accountRecord));
    }
 
}