package com.line.backstage.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.line.backstage.dao.mapper.UserLogMapper;
import com.line.backstage.entity.UserLog;
import com.line.backstage.shiro.JwtUtil;
import com.line.backstage.utils.IpUtil;
import com.line.backstage.vo.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 日志
 *
 * @author pc
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private UserLogMapper userLogMapper;

    /**
     * 获取header中的key
     */
    public static final String LOGIN_TOKEN_KEY = "Authorization";

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.line.backstage.annotation.Log)")
    public void pointcut() {
    }

    /**
     * 周围
     *
     * @param point
     * @return
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint point) {

        // 定义注解处的方法结果对象
        Object result;
        // 执行
        int executeStatus;
        long beginTime = System.currentTimeMillis();

        try {

            // 执行注解处方法并返回结果
            // 执行成功
            executeStatus = 1;
            result = point.proceed();
        } catch (Throwable throwable) {

            // 将异常信息包装返回
            // 执行失败
            executeStatus = 0;
            System.err.println(throwable.getMessage());
            result = ResponseHelper.failedWith(throwable.getMessage());
        }

        // 计算执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        // 保存日志
        saveLog(point, time, result, executeStatus);

        // 返回执行结果
        return result;
    }

    /**
     * 注解处方法执行结果-再保存日志
     *
     * @param joinPoint
     * @param time
     * @param result
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time, Object result, int executeStatus) {

        // 获取HttpServletRequest请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        Log logAnnotation = method.getAnnotation(Log.class);

        UserLog userLog = new UserLog();
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (StringUtils.isNotBlank(token)) {

            String userId = JwtUtil.getUserId(token);
            userLog.setUserId(Integer.valueOf(userId));
        }

        // 模块名称
        userLog.setModelName(joinPoint.getTarget().getClass().getName());

        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称 局部变量表参数名称发现器
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        // 请求方式：["request","body"]
        String[] paramNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);

        try{

            String paramNamesStr = new ObjectMapper().writeValueAsString(paramNames);
            String argsStr = new ObjectMapper().writeValueAsString(args);
            // 设置原始请求参数
            userLog.setOriginalParams(paramNamesStr + " - " + argsStr);
        } catch(IOException e){
            log.error(e.getMessage());
        }

        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {

                params += "  " + paramNames[i] + "：" + args[i];
            }
            // 设置处理后的请求参数
            userLog.setParams(params.trim());
        }

        // 操作名称
        userLog.setActionName(signature.getName() + "()");
        // 请求地址
        userLog.setRequestUrl(request.getRequestURL().toString());
        // 接口描述
        userLog.setDescription(logAnnotation.description());
        userLog.setUserType(logAnnotation.userType());
        // 设置IP地址
        userLog.setIp(IpUtil.getIpAddr(request));
        userLog.setExecuteTime((double) time);

        // 请求结果
        try {
            // 请求的结果
            String resultStr = new ObjectMapper().writeValueAsString(result);
            userLog.setResponseResult(resultStr);
        } catch (IOException e) {
            System.err.println(e);
        }
        userLog.setExecuteStatus(executeStatus);

        // 保存系统日志
        userLogMapper.insert(userLog);
    }
}
