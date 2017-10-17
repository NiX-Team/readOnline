package com.kiss.cache;

import com.kiss.Exception.CacheException;
import com.kiss.cache.supper.MemoryCache;

/**
 * 缓存区生成工厂
 * */
public class CacheFactory {

    /**
     * 获得默认的缓存区
     * */
    public final static Cache getDeafultCache() {
        Cache cache = new MemoryCache();
        cache.setSize(Cache.DEFAULT_CACHE_SIZE);
        return cache;
    }

    /**
     * 获取指定缓存方式的缓存区
     * */
    public final static Cache getSpecifiedCache(Class<? extends Cache> clazz) throws CacheException{
       return getSpecifiedCache(clazz,Cache.DEFAULT_CACHE_SIZE, Cache.DEFAULT_FULL_RESOLVE_TYPE);
    }



    /**
     * 获取指定大小的缓存区
     * */
    public final static Cache getSpecifiedCache(Class<? extends Cache> clazz,int size,Cache.fullResolveType type) throws CacheException{
        try {
            Cache cache = clazz.newInstance();
            cache.setSize(size);
            cache.setFullResolveType(type);
            return cache;
        } catch (IllegalAccessException e) {
            throw new CacheException("不合法的" + clazz.getSimpleName() + "类类型");
        } catch (InstantiationException e) {
            throw new CacheException(clazz + "实例化失败");
        }
    }
}
