package com.kiss.model;

import com.kiss.util.CommonUtil;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * txt图书信息
 * */
@Entity
@Table(indexes = {@Index(name = "name_index",columnList = "name",unique = true)})
public class TxtModel implements Serializable{

    //txt图书编号
    @Id
    @Column(length = 50)
    private String sn;

    //txt书名
    @NotNull
    @Length(min = 1,max = 50)
    @Column(length = 50)
    private String name;


    //txt图书文件名
    @Column(length = 50)
    private String coverName;

    //txt图书封面文件名
    @Column(length = 100)
    private String txtName;

    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "chapterId")
    private TxtChapterMsgModel chapters;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public TxtChapterMsgModel getChapters() {
        return chapters;
    }

    public void setChapters(TxtChapterMsgModel chapters) {
        this.chapters = chapters;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    /**
     * 产生txt编号
     * */
    private String randSn() {
        return CommonUtil.randSn("txt");
    }
    public TxtModel() {
        setSn(randSn());
    }
}
