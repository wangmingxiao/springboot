package com.umbrellary.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

        String key = getCacheKey(point, cache);
        ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
        Object value = valueOper.get(key);//从缓存获取数据
        if (value != null) return value;//如果有数据,则直接返回

        value = point.proceed();//跳过缓存,到后端查询数据

        if (cache.expire() <= 0) {//如果没有设置过期时间,则无限期缓存
            valueOper.set(key, value);
        } else {//否则设置缓存时间
            valueOper.set(key, value, cache.expire(), TimeUnit.SECONDS);
        }
        return value;
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
        buf.append(point.getSignature().getDeclaringTypeName()).append(".").append(point.getSignature().getName());
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
