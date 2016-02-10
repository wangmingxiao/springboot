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
        double start = (double) System.currentTimeMillis() / 1000.0;

        Object result = point.proceed();

        double end = (double) System.currentTimeMillis() / 1000.0;
        logger.info(point.getSignature().getName() + " cost: " + new java.text.DecimalFormat("#.00").format(end - start) + "s");
        return result;
    }
}
