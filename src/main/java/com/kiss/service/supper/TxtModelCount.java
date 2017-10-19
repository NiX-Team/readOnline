package com.kiss.service.supper;

import com.kiss.common.Const;
import com.kiss.common.TxtMonitoring;
import com.kiss.monitor.BeMonitorObj;
import com.kiss.util.log.LogKit;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 11723
 */
public class TxtModelCount  implements BeMonitorObj {
    private AtomicInteger count = new AtomicInteger(0);
    private String cacheKey;
    private File txtFile;

    private final Object clock = new Object();

    private int min = 10;

    public TxtModelCount(File txtFile) {
        this.txtFile = txtFile;
        autoCount();
        addMonitor();
    }


    /**
     * 自减开启
     * */
    private void autoCount() {
        Const.addRunnable(() -> {
            while (true) {
                subtraction();
                try {
                    Thread.sleep(5*60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    public void add() {
        count.getAndIncrement();
        synchronized (clock) {
            if (count.get() > min && getCacheKey() == null) {
                setStartMission(true);
            }
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


    public int getCount() {
        return count.intValue();
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
}
