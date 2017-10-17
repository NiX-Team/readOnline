package com.kiss.cache.supper;

import com.kiss.cache.Cache;

/**
 *
 * redis做缓存区
 * */
public class RedisCache implements Cache<CacheKey>{

    private int size;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void setSize(final int size) {
        this.size = size;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public fullResolveType fullResolveType() {
        return null;
    }

    @Override
    public void setFullResolveType(fullResolveType type) {

    }

    @Override
    public boolean put(CacheKey cacheKey) throws Exception{
        return false;
    }

    @Override
    public CacheKey get(String cacheKey) {
        return null;
    }
}
