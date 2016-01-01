package com.umbrellary.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisCacheableAop {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(cache)")
    public Object cached(final ProceedingJoinPoint point, RedisCache cache) throws Throwable {

        if (cache.handler() == RedisCache.Handler.Save) {
            String key = getCacheKey(point, cache);

            Object value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                return value;
            }

            value = point.proceed();

            if (cache.expire() <= 0) {
                redisTemplate.opsForValue().set(key, value);
            } else {
                redisTemplate.opsForValue().set(key, value, cache.expire(), TimeUnit.SECONDS);
            }
            return value;

        } else if (cache.handler() == RedisCache.Handler.Delete) {
            String key = getCacheKey(point, cache);

            redisTemplate.delete(key);

            return point.proceed();
        } else {

            return point.proceed();
        }
    }

    /**
     * 根据类和参数获取key
     *
     * @param point
     * @param cache
     * @return
     */
    private String getCacheKey(ProceedingJoinPoint point, RedisCache cache) throws NoSuchMethodException {

        StringBuilder buf = new StringBuilder();
        buf.append(point.getSignature().getDeclaringTypeName());//.append(".").append(point.getSignature().getName())
        if (cache.key().length() > 0) {
            buf.append(".").append(cache.key());
        }

        Object[] args = point.getArgs();

        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[][] pas = point.getTarget().getClass().getMethod(methodName, parameterTypes).getParameterAnnotations();


        for (int i = 0; i < pas.length; i++) {
            for (Annotation an : pas[i]) {
                if (an instanceof RedisKey) {
                    buf.append(".").append(args[i].toString());
                    break;
                }
            }
        }
        return buf.toString();
    }
}
