package com.umbrellary.daoimpl;

import com.umbrellary.dao.ICacheDao;
import com.umbrellary.service.ICacheService;
import com.umbrellary.utils.RedisCache;
import com.umbrellary.utils.RedisKey;
import org.springframework.stereotype.Repository;

@Repository("cacheDaoimpl")
public class CacheDaoimpl implements ICacheDao {

    @Override
    @RedisCache(expire = 60)
    public String stringCache(@RedisKey String key, String value) {
        return value;
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
