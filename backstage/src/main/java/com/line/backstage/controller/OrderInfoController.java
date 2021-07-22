package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.OrderInfo;
import com.line.backstage.service.OrderInfoService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 订单信息(OrderInfo)表控制层
 *
 * @author jack
 * @since 2000-06-29 14:53:19
 */
@Api(tags = "订单信息(OrderInfo)") 
@RestController
@RequestMapping("orderInfo")
public class OrderInfoController {
 
    /**
     * 服务对象
     */
    @Resource
    private OrderInfoService orderInfoService;
 
    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改订单信息的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "订单信息对象", required = true) @RequestBody @Validated OrderInfo orderInfo) {
//        int result = orderInfoService.save(Integer.valueOf(loginUserId), orderInfo);
        Map<String,Object> map = orderInfoService.insertForBuy(Integer.valueOf(loginUserId), orderInfo);
        return ResponseHelper.success(map);
    }
 
    /**
     * 通过主键删除数据
     *
     * @param loginUserId 用户ID
     * @param orderId 主键
     * @return 是否成功
     */
    @DeleteMapping("delete/{orderId}")
    @ApiOperation(value = "删除单条数据", notes = "删除主键orderId的单条数据")
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "订单信息主键orderId", required = true) @PathVariable("orderId") Integer orderId) {
            return ResponseHelper.success(orderInfoService.delete(Integer.valueOf(loginUserId), orderId));
    }
 
    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{orderId}")
    @ApiOperation(value = "查询单条数据", notes = "通过orderId查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "订单信息主键orderId", required = true) @PathVariable("orderId") Integer orderId){
        return ResponseHelper.success(orderInfoService.queryById(orderId));
    }
 
    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param orderInfo 查询条数
     * @return 对象列表
     */
    @PostMapping("queryMyOrder")
    @ApiOperation(value = "列表", notes = "查询订单信息的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "订单信息对象", required = true) @RequestBody OrderInfo orderInfo) {
        return ResponseHelper.success(orderInfoService.list(Integer.valueOf(loginUserId), orderInfo));
    }
 
}