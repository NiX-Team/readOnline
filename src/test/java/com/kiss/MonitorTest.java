package com.kiss;

import com.kiss.common.TxtMonitoring;
import com.kiss.monitor.BeMonitorObj;

public class MonitorTest implements BeMonitorObj {
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
        System.out.println("ok");
        setStatus(false);

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
    public void setStatus(boolean status) {
        IDENTIFICATION.setStatus(status);

    }

    @Override
    public boolean getStatus() {
        return IDENTIFICATION.isStatus();
    }

    public static void main(String[] args) {
        MonitorTest monitorTest = new MonitorTest();
        monitorTest.addMonitor();
        monitorTest.setStartMission(true);
    }
}
