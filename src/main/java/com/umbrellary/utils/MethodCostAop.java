package com.umbrellary.utils;

import com.umbrellary.serviceimpl.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class MethodCostAop {
    @Around("@annotation(MethodCost)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Logger logger = LoggerFactory.getLogger(MethodCostAop.class);
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        logger.info(point.getSignature().getName() + " cost: " + String.valueOf(System.currentTimeMillis() - start) + "s");
        return result;
    }
}
