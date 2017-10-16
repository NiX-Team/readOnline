package com.kiss.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * txt图书的章节信息
 * */
@Entity
@Table(indexes = @Index(name = "index_txtSn",columnList = "txtSn",unique = true))
public class TxtChapterMsgModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "increment")
    private Integer id;
    //章节数
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    private String chapter;

    //当前章节开头到txt文件头偏移量
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    private String offset;

    //改章节标题
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    private String title;

    private String txtSn;


    @Transient
    private Integer[] chapters;
    @Transient
    private Long[] offsets;
    @Transient
    private String[] titles;

    public void addChapter(Integer chapter,Long offset,String title) {
        if (this.chapter == null) {
            this.chapter = String.valueOf(chapter);
            this.offset = String.valueOf(offset);
            this.title = title;
        }else {
            this.chapter += "," + chapter ;
            this.offset += "," +  offset ;
            this.title += "," +  title ;
        }
    }

    public void addChapter(List chapter,List offset,List title) {
        StringBuffer sb = new StringBuffer();
        for (Object cha:chapter) {
            sb.append(cha + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str1 = sb.toString();

        sb.delete(0,sb.length());
        for (Object off:offset) {
            sb.append(off + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str2 = sb.toString();

        sb.delete(0,sb.length());
        for (Object tit:title) {
            sb.append(tit + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str3 = sb.toString();

        if (this.chapter == null) {
            this.chapter = str1;
            this.offset = str2;
            this.title = str3;
        }else {
            this.chapter +=  str1;
            this.offset +=  str2;
            this.title += str3;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer[] getChapters() {
        String[] chapters = getChapter().split(",");
        Integer[] integers = new Integer[chapters.length];
        for (int i = 0;i < chapters.length;i ++) {
            integers[i] = new Integer(chapters[i]);
        }
        return integers;
    }

    public void setChapters(Integer[] chapters) {
        this.chapters = chapters;
    }


    public void setOffsets(Long[] offsets) {
        this.offsets = offsets;
    }

    public Long[] getOffsets() {
        String[] chapters = getOffset().split(",");
        Long[] longs = new Long[chapters.length];
        for (int i = 0;i < chapters.length;i ++) {
            longs[i] = new Long(chapters[i]);
        }
        return longs;
    }

    public String[] getTitles() {
        return getTitle().split(",");
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String getTxtSn() {
        return txtSn;
    }

    public void setTxtSn(String txtSn) {
        this.txtSn = txtSn;
    }

    @Override
    public String toString() {
        return "TxtChapterMsgModel{" +
                "id=" + id +
                ", chapter='" + chapter + '\'' +
                '}';
    }
}
