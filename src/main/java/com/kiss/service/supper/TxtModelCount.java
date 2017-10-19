package com.kiss.service.supper;

import com.kiss.common.Const;
import com.kiss.common.TxtMonitoring;
import com.kiss.common.config.SystemConfig;
import com.kiss.monitor.BeMonitorObj;
import com.kiss.util.log.LogKit;

import java.io.File;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 11723
 */
public class TxtModelCount  implements BeMonitorObj {


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
        addMonitor();
    }

    public void add() {
        count.getAndIncrement();
        synchronized (clock) {
            if (count.get() > min && getCacheKey() == null) {
                setStartMission(true);
            }
            endTime = new Date();
        }
    }

    public void subtraction() {
        synchronized (clock) {
            count.getAndAdd(-10);
            if (count.get() < min && getCacheKey() != null) {
                setStartMission(true);
            }
            if (count.get() < 0) {
                count.set(0);
            }
        }
    }


    @Override
    public boolean addMonitor() {
        return TxtMonitoring.TXT_MONITORING.addBeMonitorObj(this);
    }

    @Override
    public boolean getStartMission() {
        return IDENTIFICATION.isStartMission();
    }

    @Override
    public void setStartMission(boolean startMission) {
        IDENTIFICATION.setStartMission(startMission);
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
        setStartMission(false);
        setAllowRun(true);
    }

    @Override
    public void setAllowRun(boolean allowRun) {
        IDENTIFICATION.setAllowRun(allowRun);
    }

    @Override
    public boolean getAllowRun() {
        return IDENTIFICATION.isAllowRun();
    }


    @Override
    public void close() {
        LogKit.info("准备关闭该监控对象···");
        Const.CACHE.remove(cacheKey);
        TxtMonitoring.TXT_MONITORING.remove(this);
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
