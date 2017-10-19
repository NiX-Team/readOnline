package com.kiss.common;

import com.kiss.cache.Cache;
import com.kiss.cache.CacheFactory;
import com.kiss.cache.exception.CacheException;
import com.kiss.cache.supper.CacheKey;
import com.kiss.cache.supper.MemoryCache;

import java.util.concurrent.*;

/**
 * @author 11723
 * 项目全局常量
 * */
public final class Const {

    /**
     * 项目线程池 任务队列
     * */
    public static final ThreadRunnable THREAD_RUNNABLE = new ThreadRunnable();

    public static final Cache CACHE = new CacheConfig().getCache();

    /**
     * 给线程池添加任务
     * */
    public static void addRunnable(Runnable work) {
        THREAD_RUNNABLE.addRunnable(work);
    }





    /**
     * 项目缓存配置
     * */
    private static class CacheConfig {

        private final Class<? extends Cache> clazz;
        private final int size;
        private final Cache.fullResolveType type;

        public CacheConfig(){
            this(MemoryCache.class,100,Cache.fullResolveType.do_nothing);
        }
        public CacheConfig(Class<? extends Cache> clazz,int size,Cache.fullResolveType type){
            this.clazz = clazz;
            this.size = size;
            this.type = type;
        }



        private Cache<CacheKey> getCache() {
            try {
                return CacheFactory.getSpecifiedCache(clazz,size,type);
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

         public ThreadRunnable() {
             this(200,200,0, TimeUnit.SECONDS);
         }
         public ThreadRunnable(
                 int corePoolSize,
                 int maximumPoolSize,
                 long keepAliveTime,
                 TimeUnit unit
         ) {
             THREAD_POOL =  new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, unit,WORK_QUEUE);
         }

        /**
         * 任务队列是否开始执行
         * */
        private boolean start = true;
        /**
         * 任务队列
         * */
        private static final BlockingDeque<Runnable> WORK_QUEUE = new LinkedBlockingDeque<>();
        /**
         * 线程池
         * */
        private final ThreadPoolExecutor THREAD_POOL;
        public synchronized void addRunnable(Runnable work) {
            WORK_QUEUE.add(work);
//            if (start) {
//                THREAD_POOL.execute(WORK_QUEUE.poll());
//                start = false;
//            }
            THREAD_POOL.execute(work);
        }



        public synchronized void removeRunnable(Runnable work) {
            WORK_QUEUE.remove(work);
        }
    }
}
