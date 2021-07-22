package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.SysCashCheck;
import com.line.backstage.service.SysCashCheckService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 后台管理系统提现审核记录表(SysCashCheck)表控制层
 *
 * @author jack
 * @since 2000-07-01 11:35:16
 */
@Api(tags = "后台管理系统提现审核记录表(SysCashCheck)") 
@RestController
@RequestMapping("sysCashCheck")
public class SysCashCheckController {
 
    /**
     * 服务对象
     */
    @Resource
    private SysCashCheckService sysCashCheckService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改后台管理系统提现审核记录表的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统提现审核记录表对象", required = true) @RequestBody @Validated SysCashCheck sysCashCheck) {
            return ResponseHelper.success(sysCashCheckService.save(Integer.valueOf(loginUserId), sysCashCheck));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param id 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键id的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统提现审核记录表主键id", required = true) @PathVariable("id") Integer id) {
            return ResponseHelper.success(sysCashCheckService.delete(Integer.valueOf(loginUserId), id));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{id}")
    @ApiOperation(value = "查询单条数据", notes = "通过id查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "后台管理系统提现审核记录表主键id", required = true) @PathVariable("id") Integer id){
        return ResponseHelper.success(sysCashCheckService.queryById(id));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sysCashCheck 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询后台管理系统提现审核记录表的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "后台管理系统提现审核记录表对象", required = true) @RequestBody SysCashCheck sysCashCheck) {
        return ResponseHelper.success(sysCashCheckService.list(Integer.valueOf(loginUserId), sysCashCheck));
    }
 
}