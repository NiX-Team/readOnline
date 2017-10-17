package com.kiss.dto;

public class TxtChapterDto {
    private Integer chapter;
    private Long offset;
    private Long nioOffset;
    private String title;

    public TxtChapterDto() {}

    public Long getNioOffset() {
        return nioOffset;
    }

    public void setNioOffset(Long nioOffset) {
        this.nioOffset = nioOffset;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
