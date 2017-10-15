package com.kiss.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * txt图书信息
 * */
@Entity
public class TxtModel {
    @Id
    @GeneratedValue
    private Integer id;
    //txt书名
    @NotNull
    @Length(min = 1,max = 100)
    private String name;
    //txt图书页面图片路径
    private String logPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
}
