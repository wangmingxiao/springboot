package com.umbrellary.service;

public interface ICacheService {
    public String stringCache(String key, String value);

    public String memsave(String key, String value);

    public void memdel(String key);
}
