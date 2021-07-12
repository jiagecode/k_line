package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.BankCardInfo;
import com.line.backstage.service.BankCardInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 银行卡信息(BankCardInfo)表控制层
 *
 * @author jack
 * @since 2000-06-24 10:49:55
 */
@Api(tags = "银行卡信息(BankCardInfo)") 
@RestController
@RequestMapping("bankCardInfo")
public class BankCardInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private BankCardInfoService bankCardInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param bankCardInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改银行卡信息的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "银行卡信息对象", required = true) @RequestBody @Validated BankCardInfo bankCardInfo) {
            return ResponseHelper.success(bankCardInfoService.save(Integer.valueOf(loginUserId), bankCardInfo));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param bankCardId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{bankCardId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键bankCardId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "银行卡信息主键bankCardId", required = true) @PathVariable("bankCardId") Integer bankCardId) {
            return ResponseHelper.success(bankCardInfoService.delete(Integer.valueOf(loginUserId), bankCardId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param bankCardId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{bankCardId}")
    @ApiOperation(value = "查询单条数据", notes = "通过bankCardId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "银行卡信息主键bankCardId", required = true) @PathVariable("bankCardId") Integer bankCardId){
        return ResponseHelper.success(bankCardInfoService.queryById(bankCardId));
    }
 
    /**
     * 消费者用户查询多条数据
     * 截取卡号
     *
     * @param loginUserId 用户ID
     * @param bankCardInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("queryMyBankCard")
    @ApiOperation(value = "列表", notes = "查询银行卡信息的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "银行卡信息对象", required = true) @RequestBody BankCardInfo bankCardInfo) {
        return ResponseHelper.success(bankCardInfoService.list(Integer.valueOf(loginUserId), bankCardInfo));
    }
 
}