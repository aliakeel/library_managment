package com.akeel.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.akeel.library.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with cause: {}", joinPoint.getSignature(), exception.getMessage());
    }
}
