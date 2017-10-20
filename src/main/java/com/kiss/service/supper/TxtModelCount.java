package com.kiss.service.supper;

import com.kiss.common.Const;
import com.kiss.common.TxtMonitoring;
import com.kiss.common.config.SystemConfig;
import com.kiss.monitor.BeMonitorObj;
import com.kiss.monitor.Monitor;
import com.kiss.util.log.LogKit;

import java.io.File;
import java.util.Date;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 11723
 */
public class TxtModelCount  extends BeMonitorObj {


    /**
     * 计数
     * */
    private final AtomicInteger count = new AtomicInteger(0);
    /**
     * 缓存key值
     * */
    private String cacheKey;
    /**
     * txt文件
     * */
    private final File txtFile;
    /**
     * 最后阅读时间
     * */
    private Date endTime = new Date();

    /**
     * 内置锁
     * */
    private final Object clock = new Object();

    private int min = SystemConfig.getCacheBoundary();

    public TxtModelCount(File txtFile) {
        this.txtFile = txtFile;
        addMonitor(Const.MONITOR);
    }

    /**
     * 计数加一
     * */
    public void add() {
        count.getAndIncrement();
        synchronized (clock) {
            if (count.get() > min && getCacheKey() == null) {
                start();
            }
            endTime = new Date();
        }
    }

    /**
     * 自减
     * */
    public void subtraction() {
        synchronized (clock) {
            count.getAndAdd(-10);
            if (count.get() < min && getCacheKey() != null) {
                start();
            }
            if (count.get() < 0) {
                count.set(0);
            }
        }
    }
    @Override
    public void run() {
        LogKit.info("开始执行缓存操作");
        if (count.get() > min) {
            TxtContentByteCache cache = new TxtContentByteCache(txtFile);
            setCacheKey(cache.getCacheKey());
            Const.CACHE.put(cache);
        }else {
            if (getCacheKey() != null) {
                Const.CACHE.remove(cacheKey);
                setCacheKey(null);
            }
        }
    }


    @Override
    public void close() {
        LogKit.info("准备关闭该监控对象···");
        if (getCacheKey() != null){
            Const.CACHE.remove(cacheKey);
        }
        deleteObservers();
        LogKit.info("关闭成功···");
    }


    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public Date getEndTime() {
        return endTime;
    }
}
