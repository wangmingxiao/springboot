package com.umbrellary.serviceimpl;

import com.umbrellary.dao.ICacheDao;
import com.umbrellary.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("cacheService")
public class CacheService implements ICacheService {

    @Autowired
    @Qualifier("cacheDaoimpl")
    private ICacheDao iCacheDao;

    @Override
    public String stringCache(String key, String value) {
        return iCacheDao.stringCache(key, value);
    }

    @Override
    public String memsave(String key, String value) {
        for (int i = 0; i < 10000; i++) {
            iCacheDao.memsave(key + i, value);
        }
        return "存储成功";
    }

    @Override
    public void memdel(String key) {
        for (int i = 0; i < 10000; i++) {
            iCacheDao.memdel(key + i);
        }
    }

}
