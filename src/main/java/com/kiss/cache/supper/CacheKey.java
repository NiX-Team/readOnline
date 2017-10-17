package com.kiss.cache.supper;
import com.kiss.cache.util.ObjectUtil;

import java.io.Serializable;
import java.util.Random;

/**
 * @author 11723
 */
public class CacheKey implements Serializable{
    private final String cacheKey;

    public CacheKey() {
        cacheKey = "cache-" + System.currentTimeMillis() + "-key-" + (new Random().nextInt());
    }
    public String getCacheKey() {
        return cacheKey;
    }

    public byte[] toBytes() throws Exception {
        return ObjectUtil.ObjectToByte(this);
    }
}
