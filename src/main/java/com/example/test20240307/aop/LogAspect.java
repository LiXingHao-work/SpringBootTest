package com.example.test20240307.aop;

import com.alibaba.fastjson.JSONObject;
import com.example.test20240307.mapper.OperateLogMapper;
import com.example.test20240307.pojo.OperateLog;
import com.example.test20240307.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect //切面类
public class LogAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.example.test20240307.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //操作人ID - 当前登陆员工id
        //获取请求头中的jwt令牌,解析令牌
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer operateUser = (Integer) claims.get("id");

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类名
        String className = joinPoint.getTarget().getClass().getName();

        //操作方法名
        String mothodName = joinPoint.getSignature().getName();

        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        //记录方法开始前时间
        long begin = System.currentTimeMillis();

        //调用原始目标方法运行
        Object result = joinPoint.proceed();

        // 记录方法结束时间
        long end = System.currentTimeMillis();

        //操作方法返回值
        String returnValue = JSONObject.toJSONString(result);

        //操作耗时
        Long constTime = end - begin;



        //记录操作日志
        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className, mothodName, methodParams, returnValue, constTime);
        operateLogMapper.insert(operateLog);

        log.info("AOP记录操作日志: {}", operateLog);

        return result;
    }

}
