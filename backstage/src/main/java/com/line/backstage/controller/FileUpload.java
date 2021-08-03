package com.line.backstage.controller;

import com.line.backstage.annotation.LoginUserId;
import com.line.backstage.enums.DataEnum;
import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author xxx
 */
@Slf4j
@RestController
public class FileUpload {

    @PostMapping("upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ResponseModel upload(@ApiParam(value = "用户ID", required = false) @LoginUserId String loginUserId, @ApiParam(value = "文件对象信息", required = true) @RequestParam(value="file", required = false) MultipartFile file, HttpServletRequest req) {

        // 校验文件不能为空
        if (file.isEmpty()) {
            return ResponseHelper.failedWith(DataEnum.FLAG_STATUS_VALID.getDesc());
        }
        // 获取文件名
        String oldName = file.getOriginalFilename();
        // 生产uuid名称+扩展名
        String newName =  loginUserId + "-" + UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."));
        // 根据时间生成保存文件
        String format = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        String pathUrl = "/img/" + loginUserId + format;
        String realPath = new ApplicationHome(this.getClass()).getSource().getParentFile().toString() + "/bian/web-manage" + pathUrl;
        // 生成文件路劲
        File folder = new File(realPath);
        // 校验文件夹路径是否存在
        if (!folder.exists()) {
            // 不存在 新增路径
            folder.mkdirs();
        }
        try {
//            ApplicationHome h = new ApplicationHome(this.getClass());
//            File jarF = h.getSource();
//            System.out.println(jarF.getParentFile().toString());
            // 保存文件
            log.info("用户ID【{}】保存文件路径【{}】文件名为【{}】", loginUserId, folder, newName);
            file.transferTo(new File(folder, newName));
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseHelper.failedWith(DataEnum.FLAG_STATUS_VALID.getDesc());
        }
        return ResponseHelper.success(pathUrl + newName);
    }
}
