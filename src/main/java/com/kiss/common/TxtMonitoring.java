package com.kiss.common;

import com.kiss.monitor.BeMonitorObj;
import com.kiss.monitor.Monitor;
import com.kiss.util.log.LogKit;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

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
    public boolean addBeMonitorObj(BeMonitorObj obj) {
        synchronized (BE_MONITOR_OBJS) {
            LogKit.info("添加被监视对象：" + obj.hashCode());
            return BE_MONITOR_OBJS.add(obj);
        }
    }

    @Override
    public void remove(BeMonitorObj obj) {
        synchronized (BE_MONITOR_OBJS) {
            LogKit.info("移出被监视对象：" + obj.hashCode());
            BE_MONITOR_OBJS.remove(obj);
        }
    }

    @Override
    public void start() {
        Const.addRunnable(()->{
            while (true){
                synchronized (BE_MONITOR_OBJS) {
                    for (BeMonitorObj obj:BE_MONITOR_OBJS) {
                        if (obj.getStartMission() && obj.getAllowRun()) {
                            obj.setAllowRun(false);
                            Const.addRunnable(() -> obj.run());
                            LogKit.info("添加" + obj.getClass() + "的任务");
                        }
                    }
                }
            }
        });
    }
}
