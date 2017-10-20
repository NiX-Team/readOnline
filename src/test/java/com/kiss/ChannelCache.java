package com.kiss;

import com.kiss.cache.supper.CacheKey;

public class ChannelCache extends CacheKey{
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    protected int size() {
        return 0;
    }
}
