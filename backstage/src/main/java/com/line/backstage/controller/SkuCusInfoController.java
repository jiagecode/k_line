package com.line.backstage.controller;
 
import com.line.backstage.entity.SkuCusInfo;
import com.line.backstage.service.SkuCusInfoService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
 
/**
 * 自选商品信息表(SkuCusInfo)表控制层
 *
 * @author Zy
 * @since 2021-07-02 18:09:07
 */
@Api(tags = "自选商品信息表(SkuCusInfo)") 
@RestController
@RequestMapping("skuCusInfo")
public class SkuCusInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private SkuCusInfoService skuCusInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改自选商品信息表的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "自选商品信息表对象", required = true) @RequestBody @Validated SkuCusInfo skuCusInfo) {
            return ResponseHelper.success(skuCusInfoService.save(Integer.valueOf(loginUserId), skuCusInfo));
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
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "自选商品信息表主键skuId", required = true) @PathVariable("skuId") Integer skuId) {
            return ResponseHelper.success(skuCusInfoService.delete(Integer.valueOf(loginUserId), skuId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param skuId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{skuId}")
    @ApiOperation(value = "查询单条数据", notes = "通过skuId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "自选商品信息表主键skuId", required = true) @PathVariable("skuId") Integer skuId){
        return ResponseHelper.success(skuCusInfoService.queryById(skuId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param skuCusInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询自选商品信息表的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "自选商品信息表对象", required = true) @RequestBody SkuCusInfo skuCusInfo) {
        return ResponseHelper.success(skuCusInfoService.list(Integer.valueOf(loginUserId), skuCusInfo));
    }
 
}