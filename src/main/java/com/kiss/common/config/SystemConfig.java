package com.kiss.common.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "system-config")
public final class SystemConfig {

    //txt文件存放路径
    private static String txtPath;
    //txt数封面存放路径
    private static String coverPath;
    //txt默认封面文件名
    private static String defaultCoverImg;

    public static String getDefaultCoverImg() {
        return defaultCoverImg;
    }

    public static void setDefaultCoverImg(String defaultCoverImg) {
        SystemConfig.defaultCoverImg = defaultCoverImg;
    }

    public static String getTxtPath() {
        return txtPath;
    }

    public static void setTxtPath(String txtPath) {
        SystemConfig.txtPath = txtPath;
    }

    public static String getCoverPath() {
        return coverPath;
    }

    public static void setCoverPath(String coverPath) {
        SystemConfig.coverPath = coverPath;
    }
}
