package com.umbrellary.serviceimpl;

import com.umbrellary.config.RedisCache;
import com.umbrellary.config.RedisKey;
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
    @RedisCache(expire = 15)
    public String stringCache(@RedisKey String key) {
        return "这里是缓存内容";
    }

    @Override
    public String stringCache2() {
        ValueOperations<String, Object> redis = redisTemplate.opsForValue();
        redis.set("键", "值", 15, TimeUnit.SECONDS);
        return "15秒键值对存储成功";
    }
}
