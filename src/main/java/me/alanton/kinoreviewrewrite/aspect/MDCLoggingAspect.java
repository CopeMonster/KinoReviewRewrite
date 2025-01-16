package me.alanton.kinoreviewrewrite.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MDCLoggingAspect {
    @Pointcut("execution(* me.alanton.kinoreviewrewrite.service.*.*(..))")
    public void serviceMethods(){}

    @Before("serviceMethods()")
    public void beforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        MDC.put("serviceName", className);
        MDC.put("methodName", methodName);
    }

    @After("serviceMethods()")
    public void afterMethod() {
        MDC.clear();
    }
}
