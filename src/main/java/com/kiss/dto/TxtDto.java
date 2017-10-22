package com.kiss.dto;

public class TxtDto {

    private String sn;

    private String name;

    private String coverName;

    private Integer chapterSum;

    public TxtDto() {}
    public TxtDto(String sn,String name,String coverName,Integer chapterSum) {
        this.sn = sn;
        this.name = name;
        this.coverName = coverName;
        this.chapterSum = chapterSum;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

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

    public Integer getChapterSum() {
        return chapterSum;
    }

    public void setChapterSum(Integer chapterSum) {
        this.chapterSum = chapterSum;
    }
}
