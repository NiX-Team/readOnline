package com.kiss.common;

import com.kiss.cache.Cache;
import com.kiss.cache.CacheFactory;
import com.kiss.cache.exception.CacheException;
import com.kiss.cache.supper.CacheKey;
import com.kiss.cache.supper.MemoryCache;
import com.kiss.common.config.SystemConfig;
import com.kiss.monitor.Monitor;

import java.util.concurrent.*;

/**
 * @author 11723
 * 项目全局常量
 * */
public final class Const {

    /**
     * 任务队列
     * */
    private static final BlockingDeque<Runnable> WORK_QUEUE = new LinkedBlockingDeque<>();

    /**
     * 项目线程池
     * */
    private static final ThreadPoolExecutor THREAD_RUNNABLE = ThreadRunnable.getThreadPool();

    /**
     * 缓存
     * */
    public static final Cache CACHE = CacheConfig.getCache();

    /**
     * 监视器
     * */
    public static final Monitor MONITOR = new TxtMonitoring();

    /**
     * 给线程池添加任务
     * */
    public static void addRunnable(Runnable work) {
        THREAD_RUNNABLE.execute(work);
    }









    /**
     * 项目缓存配置
     * */
    private static class CacheConfig {

        /**
         * 缓存方式
         * */
        private final static Class<? extends Cache> cacheClass = MemoryCache.class;
        /**
         * 缓存区大小
         * */
        private final static int cacheSize = 100;
        /**
         * 移出处理策略
         * */
        private final static Cache.fullResolveType cacheFullResolveType = Cache.fullResolveType.do_nothing;

        /**
         * 获取缓存
         * */
        private static Cache<CacheKey> getCache() {
            try {
                return CacheFactory.getSpecifiedCache(cacheClass,cacheSize,cacheFullResolveType);
            } catch (CacheException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
    /**
     * 线程池配置
     * */
     private static class ThreadRunnable {

        /**
         * 监视器执行线程数量(默认100)
         * */
        private final static int corePoolSize = 100;
        /**
         * 监视器执行线程最大数量（默认100）
         * */
        private final static int maxPoolSize = 100;

        /**
         * 获取线程池
         * */
        public static ThreadPoolExecutor getThreadPool() {
            return new ThreadPoolExecutor(corePoolSize,maxPoolSize,0,TimeUnit.SECONDS ,WORK_QUEUE);
        }

    }
}
