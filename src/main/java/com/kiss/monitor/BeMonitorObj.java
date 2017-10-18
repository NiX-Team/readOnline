package com.kiss.monitor;

/**
 *
 * 被监控对象需要实现该接口才能被监控
 *
 * @author 11723
 */
public interface BeMonitorObj {

    /**
     * 被监控的必须配置
     * */
    Identification IDENTIFICATION = new Identification();

    /**
     * 将被检测对象加入到一个监控对象中
     * @return 返回添加是否成功
     * */
    boolean addMonitor();

    /**
     * 或去被监控对象的run任务是否应该开始执行
     * @return 返回任务是否开始执行
     * */
    boolean getStartMission();

    /**
     * 设置被监控对象是否开始执行任务
     * @param startMission 是否开始执行任务
     * */
    void setStartMission(boolean startMission);

    /**
     * 设置任务是否允许执行
     * @param allowRun
     * */
    void setAllowRun(boolean allowRun);

    /**
     * 获取任务是否允许被执行
     * @return
     * */
    boolean getAllowRun();


    /**
     * 被监控对象需要执行的任务
     * */
    void run();

    /**
     * 取消监控状态
     * @param status
     * */
    void setStatus(boolean status);

    /**
     * 查看被监控对象是否打开监控状态
     * @return
     * */
    boolean getStatus();


    /**
     * 被监控对象的主要配置
     * */
    class Identification {
        /**
         * 是否开启任务执行
         * */
        private boolean startMission = false;
        /**
         * 是否允许任务执行
         * */
        private boolean allowRun = true;
        /**
         * 被监视者的状态 （是否允许被监视）
         * */
        private boolean status = true;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public boolean isStartMission() {
            return startMission;
        }

        public void setStartMission(boolean startMission) {
            this.startMission = startMission;
        }

        public boolean isAllowRun() {
            return allowRun;
        }

        public void setAllowRun(boolean allowRun) {
            this.allowRun = allowRun;
        }
    }
}
