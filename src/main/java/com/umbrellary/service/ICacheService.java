package com.umbrellary.service;

public interface ICacheService {
    public String stringCache(String key);

    public String memsave(String key, String value);

    public void memdel(String key);
}
