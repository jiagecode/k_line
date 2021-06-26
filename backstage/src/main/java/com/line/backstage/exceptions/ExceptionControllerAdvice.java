package com.line.backstage.exceptions;

import com.line.backstage.vo.ResponseHelper;
import com.line.backstage.vo.ResponseModel;
import com.line.backstage.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 针对单个指定异常捕获，并且定义错误的返回体
 *
 * @author BaoBao
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * 捕捉shiro的异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ShiroException.class)
    public ResponseModel<String> handleShiRoException(ShiroException e) {
        log.error("handleShiRoException异常错误信息：", e);
        return ResponseHelper.failedWith(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResponseModel<String> handleAuthenticationException(AuthenticationException e) {
        log.error("handleAuthenticationException异常错误信息：{}", e);
        return ResponseHelper.failedWith(e.getMessage());
    }

    /**
     * 全局API异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public ResponseModel<String> APIGlobalExceptionHandler(ApiException e) {
        log.error("APIGlobalExceptionHandler异常错误信息：{}", e);
        return ResponseHelper.failedWith(e.getMessage());
    }

    /**
     * Http消息不可读异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseModel<String> handleParamJsonException(HttpMessageNotReadableException e) {
        log.error("handleParamJsonException异常错误信息：", e);
        return ResponseHelper.failedWith(e.getMessage());
    }

    /**
     * 全局实体属性验证异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel MethodArgumentNotValidGlobalExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidGlobalExceptionHandler异常错误信息：", e);
        return ResponseHelper.failedWith(e.getMessage());
    }

    /**
     * 空指针异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public ResponseModel NullPointerGlobalExceptionHandler(NullPointerException e) {
        log.error("NullPointerException：{}", e);
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        return ResponseHelper.failedWith("具体文件. " + stackTraceElement.getClassName() + ". 所属行号. " + stackTraceElement.getLineNumber());
    }

    /**
     * jackson取值异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(IOException.class)
    public ResponseModel IOEGlobalExceptionHandler(IOException e) {
        log.error("IOEGlobalExceptionHandler异常错误信息：{}", e);
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        return ResponseHelper.failedWith("具体文件. " + stackTraceElement.getClassName() + ". 所属行号. " + stackTraceElement.getLineNumber());
    }

    /**
     * 索引越界异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseModel IndexOutOfBoundsGlobalExceptionHandler(IndexOutOfBoundsException e) {
        log.error("IndexOutOfBoundsGlobalExceptionHandler异常错误信息：{}", e);
        StackTraceElement stackTraceElement = e.getStackTrace()[3];
        return ResponseHelper.failedWith("具体文件. " + stackTraceElement.getClassName() + ". 所属行号. " + stackTraceElement.getLineNumber(), ResultCode.INDEX_OUT_OF_BOUNDS);
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseModel<String> errorHandler(Exception e) {
        log.error("errorHandler异常错误信息：", e);
        return ResponseHelper.failedWith(e.getMessage(), ResultCode.ERROR);
    }
}
