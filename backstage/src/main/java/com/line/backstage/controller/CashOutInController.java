package com.line.backstage.controller;

import com.line.backstage.annotation.Log;
import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.CashOutIn;
import com.line.backstage.service.CashOutInService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 用户资金充值或提现记录(CashOutIn)表控制层
 *
 * @author jack
 * @since 2000-07-01 11:34:38
 */
@Api(tags = "用户资金充值或提现记录(CashOutIn)") 
@RestController
@RequestMapping("cashOutIn")
public class CashOutInController {
 
    /**
     * 服务对象
     */
    @Resource
    private CashOutInService cashOutInService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改用户资金充值或提现记录的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户资金充值或提现记录对象", required = true) @RequestBody @Validated CashOutIn cashOutIn) {
            return ResponseHelper.success(cashOutInService.save(Integer.valueOf(loginUserId), cashOutIn));
    }

    /**
     * 创建充值/提现记录
     *
     * @param loginUserId 用户ID
     * @param cashOutIn 实例对象
     * @return 是否成功
     */
    @PostMapping("insertForNew")
    @ApiOperation(value = "新增", notes = "新增用户资金充值或提现记录的一条数据")
    @Log(description = "创建充值/提现记录",userType = "0")
    public ResponseModel insertForNew(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户资金充值或提现记录对象", required = true) @RequestBody @Validated CashOutIn cashOutIn) {
        int re = cashOutInService.insertForNew(Integer.valueOf(loginUserId), cashOutIn) ;
        if(re == -1){
            return ResponseHelper.failedWith("参数错误");
        }else if(re == -2){
            return ResponseHelper.failedWith("账户被冻结 无法进行操作");
        }else if(re == -3){
            return ResponseHelper.failedWith("资金不足 无法提现");
        }
        return ResponseHelper.success(re);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param cashId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{cashId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键cashId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户资金充值或提现记录主键cashId", required = true) @PathVariable("cashId") Integer cashId) {
            return ResponseHelper.success(cashOutInService.delete(Integer.valueOf(loginUserId), cashId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param cashId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{cashId}")
    @ApiOperation(value = "查询单条数据", notes = "通过cashId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "用户资金充值或提现记录主键cashId", required = true) @PathVariable("cashId") Integer cashId){
        return ResponseHelper.success(cashOutInService.queryById(cashId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param cashOutIn 查询条数
     * @return 对象列表
     */
    @PostMapping("myCashOutList")
    @ApiOperation(value = "列表", notes = "查询用户资金充值或提现记录的多条数据")
    @Log(description = "查询创建充值/提现记录",userType = "0")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户资金充值或提现记录对象", required = true) @RequestBody CashOutIn cashOutIn) {
        return ResponseHelper.success(cashOutInService.list(Integer.valueOf(loginUserId), cashOutIn));
    }
 
}