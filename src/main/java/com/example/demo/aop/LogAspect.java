package com.example.demo.aop;

import com.example.demo.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.example.demo.controller..*(..))")
    private void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            LOG.debug("参数：" + JsonUtil.bean2Json(args));
        }
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        LOG.debug(className + "." + methodName + "() 开始！");
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        LOG.debug(className + "." + methodName + "() 结束！");
    }

    @Around("pointCut()")
    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {
        LOG.debug("方法执行之前");
        Object result = invocation.proceed();
        LOG.debug("方法执行完毕");
        return result;
    }
}
