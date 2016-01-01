package com.umbrellary.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodCostAop {

    @Around("@annotation(methodcost)")
    public Object around(ProceedingJoinPoint point, MethodCost methodcost) throws Throwable {
        Logger logger = LoggerFactory.getLogger(MethodCostAop.class);
        long start = System.currentTimeMillis();

        Object result = point.proceed();

        logger.info(point.getSignature().getName() + " cost: " + String.valueOf(System.currentTimeMillis() - start) + "s");
        return result;
    }
}
