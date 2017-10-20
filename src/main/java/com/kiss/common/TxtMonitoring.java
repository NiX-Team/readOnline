package com.kiss.common;

import com.kiss.monitor.BeMonitorObj;
import com.kiss.monitor.Monitor;
import com.kiss.util.log.LogKit;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.Observable;

/**
 * 对txt在线阅读进行监视
 *
 * @author 11723
 * */
@Component
public class TxtMonitoring implements Monitor {

    public static final TxtMonitoring TXT_MONITORING = new TxtMonitoring();

    @Override
    public boolean addBeMonitorObj(BeMonitorObj obj) {
       obj.addObserver(this);
       LogKit.info("添加被监视对象:" + obj);
       return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        ((BeMonitorObj)o).run();
    }
}
