package com.kiss.cache.supper;

import com.kiss.cache.Cache;
import com.kiss.cache.util.ObjectUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用内存做缓存区
 * 用map完成
 * */
public class MemoryCache implements Cache<CacheKey>{
    //缓存区大小
    private int size;
    private Cache.fullResolveType fullResolveType;

    //缓存大小记录
    private int position = 0;
    private final ConcurrentHashMap cache = new ConcurrentHashMap();

    @Override
    public int size() {
        return position;
    }

    @Override
    public void setSize(final int size) {
        this.size = size;
    }

    @Override
    public boolean isFull() {
        return position > size * 1024*1024;
    }

    @Override
    public boolean isEmpty() {
        return position == 0;
    }

    @Override
    public fullResolveType fullResolveType() {
        return fullResolveType;
    }

    @Override
    public void setFullResolveType(Cache.fullResolveType type) {
        fullResolveType = type;
    }

    @Override
    public synchronized boolean put(CacheKey cacheKey) throws Exception {
        if (cache.containsKey(cacheKey.getCacheKey())) return false;
        byte[] bytes = ObjectUtil.ObjectToByte(cacheKey);
        if (bytes.length + position > size*1024*1024) throw new Exception("剩余空间不够");
        cache.put(cacheKey.getCacheKey(), bytes);
        position += bytes.length;
        return true;
    }

    @Override
    public CacheKey get(String cacheKey) {
        return (CacheKey) ObjectUtil.ByteToObject((byte[]) cache.get(cacheKey));
    }
}
