package com.kiss.monitor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 11723
 *
 * 监控对象接口
 * 需要作为监控方时需要实现该接口
 *
 * */
public interface Monitor {
    /**
     * 被监控对象集合
     * */
    Set<BeMonitorObj> BE_MONITOR_OBJS = new HashSet<BeMonitorObj>();

    /**
     * 添加一个被监控对象到监控器中
     * @param obj 被监控的对象
     * @return 返回添加是否成功
     * */
    boolean addBeMonitorObj(BeMonitorObj obj);

    /**
     * 在监控器中移出一个被监控对象
     * @param obj 被监控的对象
     * */
    void remove(BeMonitorObj obj);


    /**
     * 开始监控
     * */
    void start();
}
