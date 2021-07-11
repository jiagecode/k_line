package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.CashOutIn;
import com.line.backstage.service.SysDiyService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author DWanBean
 * @Date 2021/7/7 9:42
 * @Version 1.0
 * @Desc 后台系系统 数据生成接口
 */
@RestController
@RequestMapping("sysDiy")
public class SysDIYController {
    @Resource
    private SysDiyService sysDiyService;

    /**
     * 查询操作用户信息
     * @param loginUserId
     * @param diyUserId 操作用户
     * @return
     */
    @GetMapping("diyUserInfo")
    @ApiOperation(value = "查询操作用户信息", notes = "根据token的用户id}")
    public ResponseModel querySpecialUserInfo(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId,@RequestParam String diyUserId){
        return ResponseHelper.success(sysDiyService.querySpecialUserInfo(Integer.valueOf(loginUserId),diyUserId));
    }
    /**
     * 查询SKU下拉列表
     *
     * @return 实例对象
     */
    @GetMapping("skuList")
    @ApiOperation(value = "查询SKU下拉列表", notes = "根据token的用户id}")
    public ResponseModel querySkuDataList(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId){
        return ResponseHelper.success(sysDiyService.querySkuDataList(Integer.valueOf(loginUserId)));
    }
    /**
     * 查询SKU下拉列表
     *
     * @return 实例对象
     */
    @GetMapping("optionList1")
    @ApiOperation(value = "查询代理商下拉列表", notes = "根据token的用户id}")
    public ResponseModel queryOptionList1(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId){
        return ResponseHelper.success(sysDiyService.queryOptionsForAgent(Integer.valueOf(loginUserId)));
    }

    @PostMapping("changeInfo")
    @ApiOperation(value = "修改操作用户信息", notes = "修改操作用户信息")
    public ResponseModel updateSpecialUserInfo(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @RequestBody Map<String,Object> paramMap){
        return ResponseHelper.success(sysDiyService.updateSpecialUserInfo(Integer.valueOf(loginUserId),paramMap));
    }

    @PostMapping("createData")
    @ApiOperation(value = "生成模拟数据并返回", notes = "生成模拟数据并返回")
    public ResponseModel createImitateData(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @RequestBody Map<String,Object> paramMap){
        return ResponseHelper.success(sysDiyService.createImitateData(Integer.valueOf(loginUserId),paramMap));
    }

    @PostMapping("editRecord")
    @ApiOperation(value = "编辑操作记录", notes = "修改操作用户信息")
    public ResponseModel editDiyRecordByType(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @RequestBody Map<String,Object> paramMap){
        return ResponseHelper.success(sysDiyService.editDiyRecordByType(Integer.valueOf(loginUserId),paramMap));
    }

    @PostMapping("editAccount")
    @ApiOperation(value = "编辑用户资金", notes = "修改资金")
    public ResponseModel editUserMoney(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @RequestBody Map<String,Object> paramMap){
        return ResponseHelper.success(sysDiyService.editUserMoney(Integer.valueOf(loginUserId),paramMap));
    }

    @PostMapping("checkForCash")
    @ApiOperation(value = "审核、删除提现/充值", notes = "修改资金")
    public ResponseModel checkForCash(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @RequestBody CashOutIn cash){
        return ResponseHelper.success(sysDiyService.checkForCashOutIn(Integer.valueOf(loginUserId),cash));
    }

}
