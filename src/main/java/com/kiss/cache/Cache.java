package com.kiss.cache;

import com.kiss.cache.supper.CacheKey;

/**
 * 缓存
 * */
public interface Cache<C extends CacheKey> {
    //缓存区默认大小(M)
    int DEFAULT_CACHE_SIZE = 10;

    //最大内存耗尽时的村里方式
    Cache.fullResolveType DEFAULT_FULL_RESOLVE_TYPE = Cache.fullResolveType.do_nothing;

    //缓存满后的处理方式
    enum fullResolveType{
        //从缓存区头开向后覆盖
        covering_before,
        //从缓存区末尾向前覆盖
        covering_after,
        //随机覆盖
        covering_rand,
        //丢弃此次缓存
        do_nothing,
        //清空缓存重新开始
        clear_and_start
    }

    /**
     * 获取当前缓存区占用大小
     * */
    int size();

    /**
     * 设置缓存区大小
     * */
    void setSize(int size);

    /**
     * 获取当前缓存是否满
     * */
    boolean isFull();

    /**
     * 获取缓存区是否为空
     * */
    boolean isEmpty();

    /**
     * 获取缓存区对缓存区满后添加处理的模式
     * */
    Cache.fullResolveType fullResolveType();

    void setFullResolveType(Cache.fullResolveType type);


    /**
     * 添加对象{@link CacheKey}到缓存
     * */
    boolean put(C c) throws Exception;
    /**
     * 根据缓存key获取缓存对象
     * */
    C get(String cacheKey);

}
