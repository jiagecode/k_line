package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.entity.SendMassage;
import com.line.backstage.service.SendMassageService;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 聊天记录(SendMassage)表控制层
 *
 * @author Zy
 * @since 2021-08-13 10:27:20
 */
@Api(tags = "聊天记录(SendMassage)")
@RestController
@RequestMapping("sendMassage")
public class SendMassageController {

    /**
     * 服务对象
     */
    @Resource
    private SendMassageService sendMassageService;

    /**
     * 新增/修改数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 实例对象
     * @return 是否成功
     */
    @PostMapping("save")
    @ApiOperation(value = "新增/修改", notes = "新增/修改聊天记录的一条数据")
    public ResponseModel save(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "聊天记录对象", required = true) @RequestBody @Validated SendMassage sendMassage) {
            return ResponseHelper.success(sendMassageService.save(Integer.valueOf(loginUserId), sendMassage));
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
    public ResponseModel delete(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "聊天记录主键id", required = true) @PathVariable("id") Integer id) {
            return ResponseHelper.success(sendMassageService.delete(Integer.valueOf(loginUserId), id));
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @GetMapping("queryById/{id}")
    @ApiOperation(value = "查询单条数据", notes = "通过id查询单条数据}")
    public ResponseModel queryById(@ApiParam(value = "聊天记录主键id", required = true) @PathVariable("id") Integer id){
        return ResponseHelper.success(sendMassageService.queryById(id));
    }

    /**
     * 查询多条数据
     *
     * @param loginUserId 用户ID
     * @param sendMassage 查询条数
     * @return 对象列表
     */
    @PostMapping("list")
    @ApiOperation(value = "列表", notes = "查询聊天记录的多条数据")
    public ResponseModel list(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "聊天记录对象", required = true) @RequestBody SendMassage sendMassage) {
        return ResponseHelper.success(sendMassageService.list(Integer.valueOf(loginUserId), sendMassage));
    }

}
