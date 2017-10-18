package com.kiss.common;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 11723
 * 项目全局常量
 * */
public final class Const {

    public static final ThreadRunnable THREAD_RUNNABLE = ThreadRunnable.getPool();



    public static class ThreadRunnable {

        private static ThreadRunnable getPool() {
            return new ThreadRunnable();
        }
        private boolean start = true;
        private final BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>();
        private final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(200,200,0, TimeUnit.SECONDS,workQueue);
        public synchronized void addRunnable(Runnable work) {
            workQueue.add(work);
            if (start) {
                THREAD_POOL.execute(workQueue.poll());
            }
        }
        public synchronized void removeRunnable(Runnable work) {
            workQueue.remove(work);
        }
    }
}
