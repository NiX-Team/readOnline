package com.kiss.common;

import com.kiss.monitor.BeMonitorObj;
import com.kiss.monitor.Monitor;
import org.springframework.stereotype.Component;

/**
 * 对txt在线阅读进行监视
 *
 * @author 11723
 * */
@Component
public class TxtMonitoring implements Monitor {

    public static final TxtMonitoring TXT_MONITORING = new TxtMonitoring();

    static {
        TXT_MONITORING.start();
    }

    @Override
    public synchronized boolean addBeMonitorObj(BeMonitorObj obj) {
        return BE_MONITOR_OBJS.add(obj);
    }

    @Override
    public synchronized void remove(BeMonitorObj obj) {
        BE_MONITOR_OBJS.remove(obj);
    }

    @Override
    public void start() {
        Const.THREAD_RUNNABLE.addRunnable(()->{
            while (true){
                for (BeMonitorObj obj:BE_MONITOR_OBJS) {
                    if (!obj.getStatus()) {
                        remove(obj);
                    }else {
                        if (obj.getStartMission() && obj.getAllowRun()) {
                            Const.THREAD_RUNNABLE.addRunnable(() -> obj.run());
                            obj.setAllowRun(false);
                        }
                    }
                }
            }
        });

    }
}
