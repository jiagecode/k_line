package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.PositionInfo;
import com.line.backstage.entity.sysentity.ManPosiVo;
import com.line.backstage.service.PositionInfoService;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import com.line.backstage.vo.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户持仓信息(PositionInfo)表控制层
 *
 * @author jack
 * @since 2000-07-01 11:34:50
 */
@Api(tags = "用户持仓信息(PositionInfo)") 
@RestController
@RequestMapping("positionInfo")
public class PositionInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private PositionInfoService positionInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param positionInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改用户持仓信息的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户持仓信息对象", required = true) @RequestBody @Validated PositionInfo positionInfo) {
            return ResponseHelper.success(positionInfoService.save(Integer.valueOf(loginUserId), positionInfo));
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param positionId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{positionId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键positionId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户持仓信息主键positionId", required = true) @PathVariable("positionId") Integer positionId) {
            return ResponseHelper.success(positionInfoService.delete(Integer.valueOf(loginUserId), positionId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param positionId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{positionId}")
    @ApiOperation(value = "查询单条数据", notes = "通过positionId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "用户持仓信息主键positionId", required = true) @PathVariable("positionId") Integer positionId){
        return ResponseHelper.success(positionInfoService.queryById(positionId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param positionInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("mySkuForList")
    @ApiOperation(value = "列表", notes = "查询用户持仓信息的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户持仓信息对象", required = true) @RequestBody PositionInfo positionInfo) {
        return ResponseHelper.success(positionInfoService.list(Integer.valueOf(loginUserId), positionInfo));
    }

    @PostMapping("pullList")
    @ApiOperation(value = "列表", notes = "持仓信息的多条数据")
    public ResponseModel pullList(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "用户持仓", required = true) @RequestBody ManPosiVo positionInfo) {
        return ResponseHelper.success(positionInfoService.queryManPosiVo(Integer.valueOf(loginUserId), positionInfo));
    }

    @PostMapping("handEnd")
    @ApiOperation(value = "结算", notes = "结算")
    public ResponseModel handEnd(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "订单号", required = true) @RequestBody ManPosiVo vo) {
//        String id = JsonUtils.toJsonNode(orderId).get("orderId").asText("");
        return ResponseHelper.successWith(positionInfoService.handleEndOrderById(Integer.valueOf(loginUserId), vo), ResultCode.SUCCESS);
    }

}