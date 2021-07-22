package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.service.KLineDataService;
import com.line.backstage.utils.JsonUtils;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:k线数据接口
 */
@Api(tags = "k线数据接口")
@RestController
@RequestMapping("kline")
public class KLineInfoController {

    @Autowired
    private KLineDataService kLineDataService;

    /**
     * 查询日线数据
     */
    @PostMapping("/day/list")
    @ApiOperation(value = "日线数据列表", notes = "查询日线数据列表")
    public ResponseModel dayList(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "商品代码", required = true) @RequestBody String coinCode) {
        return ResponseHelper.success(kLineDataService.dayList(Integer.valueOf(loginUserId), JsonUtils.toJsonNode(coinCode).get("coinCode").asText("")));
    }

    /**
     * 查询分钟线数据
     */
    @PostMapping("/min/list")
    @ApiOperation(value = "分钟线数据列表", notes = "查询分钟线数据列表")
    public ResponseModel minList(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "商品代码", required = true) @RequestBody String coinCode) {
        return ResponseHelper.success(kLineDataService.minList(Integer.valueOf(loginUserId), JsonUtils.toJsonNode(coinCode).get("coinCode").asText("")));
    }

    /**
     * 查询分钟线数据
     */
    @PostMapping("/mils/list")
    @ApiOperation(value = "历史秒线数据列表", notes = "查询历史秒线数据列表")
    public ResponseModel milsList(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "商品代码", required = true) @RequestBody String coinCode) {
        return ResponseHelper.success(kLineDataService.ssList(Integer.valueOf(loginUserId), JsonUtils.toJsonNode(coinCode).get("coinCode").asText("")));
    }

}
