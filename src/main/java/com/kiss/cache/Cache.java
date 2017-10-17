package com.kiss.cache;

import com.kiss.cache.supper.CacheKey;

/**
 * 缓存
 *
 * @author 11723
 * */
public interface Cache<C extends CacheKey> {
    /**
     * 缓存区默认大小(M)
     * */
    int DEFAULT_CACHE_SIZE = 10;

    /**
     * 最大内存耗尽时的村里方式
     * */
    Cache.fullResolveType DEFAULT_FULL_RESOLVE_TYPE = Cache.fullResolveType.do_nothing;

    /**
     * 缓存区配置
     * */
    Config CONFIG = new Config();

    int M_SIZE = 1024*1024;

    /**
     * 缓存满后的处理方式
     * */
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
     * @return 返回处理缓存区满时添加缓存的策略枚举
     * */
    Cache.fullResolveType fullResolveType();

    /**
     * 设置缓存处理溢出时的处理策略
     * @param type {@link Cache.fullResolveType} 溢出处理策略
     * */
    void setFullResolveType(Cache.fullResolveType type);


    /**
     * 添加对象{@link CacheKey}到缓存
     * 如果缓存对象已经存在 则更新缓存对象
     * @param c 缓存对象
     * @throws Exception 缓存失败异常
     * @return 返回添加缓存是否成功
     * */
    boolean put(C c);
    /**
     * 根据缓存key获取缓存对象
     * @param cacheKey 缓存key值
     * @return 返回继承 {@link CacheKey}的对象
     * */
    C get(String cacheKey);


    /**
     * 获取当前缓存区占用大小
     * @return 缓存区目前占用大小
     * */
    int getPosition();

    /**
     * 缓存区共有配置
     * */
    class Config{
        /**
         * 缓存区大小
         * */
        private int size;
        /**
         * 缓存区目前偏移位置
         * */
        private int position;
        /**
         * 缓存区溢出处理策略
         * */
        private Cache.fullResolveType fullResolveType;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Cache.fullResolveType getFullResolveType() {
            return fullResolveType;
        }

        public void setFullResolveType(Cache.fullResolveType fullResolveType) {
            this.fullResolveType = fullResolveType;
        }
    }

}
