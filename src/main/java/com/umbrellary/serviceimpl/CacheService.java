package com.umbrellary.serviceimpl;

import com.umbrellary.utils.RedisCache;
import com.umbrellary.utils.RedisKey;
import com.umbrellary.service.ICacheService;
import org.springframework.stereotype.Service;

@Service("cacheService")
public class CacheService implements ICacheService {

    @Override
    @RedisCache(expire = 60)
    public String stringCache(@RedisKey String key) {
        return "这里是缓存内容";
    }

    @Override
    @RedisCache(expire = 0, handler = RedisCache.Handler.Save)
    public String memsave(@RedisKey String key, String value) {
        return value;
    }

    @Override
    @RedisCache(handler = RedisCache.Handler.Delete)
    public void memdel(@RedisKey String key) {
    }

}
