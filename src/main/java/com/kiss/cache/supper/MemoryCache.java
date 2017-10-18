package com.kiss.cache.supper;

import com.kiss.cache.Cache;
import com.kiss.cache.exception.CacheException;
import com.kiss.cache.util.ObjectUtil;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用内存做缓存区
 * 用map完成
 *
 * @author 11723
 * */
public class MemoryCache implements Cache<CacheKey>{

    private final ConcurrentHashMap<String,byte[]> cache = new ConcurrentHashMap();

    @Override
    public int size() {
        return CONFIG.getSize();
    }

    @Override
    public void setSize(final int size) {
        CONFIG.setSize(size);
    }

    @Override
    public boolean isFull() {
        return CONFIG.getPosition() > size() * M_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return CONFIG.getPosition() == 0;
    }

    @Override
    public fullResolveType fullResolveType() {
        return CONFIG.getFullResolveType();
    }

    @Override
    public void setFullResolveType(Cache.fullResolveType type) {
        CONFIG.setFullResolveType(type);
    }

    @Override
    public synchronized boolean put(CacheKey cacheKey){
        try {
            return put(cacheKey,fullResolveType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据缓存区溢出更新策略添加缓存
     * @param cacheKey 缓存内容
     * @param fullResolveType 策略枚举
     * @return 缓存是否成功
     * */
    private boolean put(CacheKey cacheKey,Cache.fullResolveType fullResolveType) throws Exception {
        switch (fullResolveType) {
            case do_nothing:return putByDoNoting(cacheKey);
            case clear_and_start:return putByDoNoting(cacheKey);
            case covering_after:return putByDoNoting(cacheKey);
            case covering_before:return putByDoNoting(cacheKey);
            case covering_rand:return putByDoNoting(cacheKey);
            default:return false;
        }
    }



    /**
     * 不做任何操作的溢出缓存策略
     * */
    private boolean putByDoNoting(CacheKey cacheKey) throws Exception {
        assert !isFull() : new CacheException("缓存区已满");
        byte[] bytes = cacheKey.toBytes();
        if (cache.containsKey(cacheKey.getCacheKey())) {
            CacheKey agoCache = get(cacheKey.getCacheKey());
            assert (bytes.length - agoCache.toBytes().length) + getPosition() <= size() * M_SIZE : new CacheException("空间不足");
            CONFIG.setPosition(getPosition() + (bytes.length - agoCache.toBytes().length));
            cache.replace(cacheKey.getCacheKey(),cacheKey.toBytes());
        }else {
            assert bytes.length + getPosition() <= size() * M_SIZE : new CacheException("剩余空间不足");
            CONFIG.setPosition(getPosition() + bytes.length);
            cache.put(cacheKey.getCacheKey(), bytes);
        }
        return true;
    }



    /**
     * 不需要实时的获取最新缓存信息不需要对get与put方法同步加锁
     * */
    @Override
    public CacheKey get(String cacheKey) {
        return (CacheKey) ObjectUtil.ByteToObject(cache.get(cacheKey));
    }

    @Override
    public boolean remove(String key) {
        return cache.remove(key) != null;
    }

    @Override
    public int getPosition() {
        return CONFIG.getPosition();
    }
}
