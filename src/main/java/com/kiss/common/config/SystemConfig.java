package com.kiss.common.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system-config")
public class SystemConfig {
    private String txtPath;

    public String getTxtPath() {
        return txtPath;
    }

    public void setTxtPath(String txtPath) {
        this.txtPath = txtPath;
    }

    @Override
    public String toString() {
        return "SystemConfig{" +
                "txtPath='" + txtPath + '\'' +
                '}';
    }
}
