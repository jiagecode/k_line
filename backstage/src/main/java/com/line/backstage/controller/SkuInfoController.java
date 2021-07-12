package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.SkuInfo;
import com.line.backstage.service.SkuInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
 
/**
 * 商品信息表(SkuInfo)表控制层
 *
 * @author jack
 * @since 2000-07-01 11:35:00
 */
@Api(tags = "商品信息表(SkuInfo)") 
@RestController
@RequestMapping("skuInfo")
public class SkuInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private SkuInfoService skuInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改商品信息表的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "商品信息表对象", required = true) @RequestBody @Validated SkuInfo skuInfo) {
            return ResponseHelper.success(skuInfoService.save(Integer.valueOf(loginUserId), skuInfo));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param skuId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{skuId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键skuId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "商品信息表主键skuId", required = true) @PathVariable("skuId") Integer skuId) {
            return ResponseHelper.success(skuInfoService.delete(Integer.valueOf(loginUserId), skuId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param skuId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{skuId}")
    @ApiOperation(value = "查询单条数据", notes = "通过skuId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "商品信息表主键skuId", required = true) @PathVariable("skuId") Integer skuId){
        return ResponseHelper.success(skuInfoService.queryById(skuId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param skuInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询商品信息表的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "商品信息表对象", required = true) @RequestBody SkuInfo skuInfo) {
        return ResponseHelper.success(skuInfoService.list(Integer.valueOf(loginUserId), skuInfo));
    }
 
}