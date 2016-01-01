package com.umbrellary.dao;

public interface ICacheDao {

    public String stringCache(String key, String value);

    public String memsave(String key, String value);

    public void memdel(String key);
}
