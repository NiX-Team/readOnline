package com.kiss.cache.supper;
import java.io.Serializable;

public class CacheKey implements Serializable{
    private final String cacheKey;

    public CacheKey() {
        cacheKey = "cache-" + System.currentTimeMillis() + "-key-" + (int)(Math.random()*10000000);
    }
    public String getCacheKey() {
        return cacheKey;
    }
}
