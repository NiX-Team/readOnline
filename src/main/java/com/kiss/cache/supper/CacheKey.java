package com.kiss.cache.supper;
import com.kiss.cache.util.ObjectUtil;

import java.io.Serializable;
import java.util.Random;

/**
 * @author 11723
 */
public abstract class CacheKey implements Serializable{
    private final String cacheKey;

    public CacheKey() {
        cacheKey = "cache-" + System.currentTimeMillis() + "-key-" + (new Random().nextInt());
    }
    public String getCacheKey() {
        return cacheKey;
    }
    /**
     * 获取当前对象的字节大小
     * @return
     * */
    protected abstract int size();
}
