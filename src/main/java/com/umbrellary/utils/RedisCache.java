package com.umbrellary.utils;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisCache {

    public enum Handler {
        Save, Delete, Clear
    }

    public String key() default "";//缓存key

    public int expire() default 0;//缓存多少秒,默认无限期

    public Handler handler() default Handler.Save;//操作类型
}
