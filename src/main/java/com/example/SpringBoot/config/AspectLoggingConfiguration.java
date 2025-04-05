package com.example.SpringBoot.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLoggingConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AspectLoggingConfiguration.class);

    @Pointcut("execution(* *(..)) && within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {
    }

    @Before("restControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("START: {}.{} with arguments = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "restControllerMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("SUCCESS: {}.{} returned = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(pointcut = "restControllerMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("EXCEPTION in {}.{} with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getMessage());
    }
}
