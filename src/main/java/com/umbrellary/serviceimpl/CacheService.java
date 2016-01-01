package com.umbrellary.serviceimpl;

import com.umbrellary.utils.MethodCost;
import com.umbrellary.utils.RedisCache;
import com.umbrellary.utils.RedisKey;
import com.umbrellary.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("cacheService")
public class CacheService implements ICacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @RedisCache(expire = 60)
    public String stringCache(@RedisKey String key) {
        return "这里是缓存内容";
    }

    @Override
    @RedisCache(expire = 0)
    @MethodCost
    public String memtest(@RedisKey String key, String value) {
        return value;
    }

}
