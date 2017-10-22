package com.kiss.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * txt图书的章节信息
 *
 * @author 11723*/
@Entity
@Table(indexes = @Index(name = "index_txtSn",columnList = "txtSn",unique = true))
public class TxtChapterMsgModel implements Serializable{
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

    //nio流操作时章节头的byte偏移量
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    private String nioOffset;


    private String txtSn;


    @Transient
    private Integer[] chapters;
    @Transient
    private Long[] offsets;
    @Transient
    private String[] titles;
    @Transient
    private Long[] nioOffsets;

    public void addChapter(Integer chapter,Long offset,Long nioOffset,String title) {
        Assert.notNull(chapter);
        Assert.notNull(title);
        if (this.chapter == null) {
            this.chapter = String.valueOf(chapter);
            this.offset = offset == null ? String.valueOf(offset) : null;
            this.nioOffset = nioOffset == null ? String.valueOf(nioOffset) : null;
            this.title = title;
        }else {
            this.chapter += "," + chapter ;
            this.offset += offset == null ? "" : ("," +  offset);
            this.nioOffset += nioOffset == null ? "" : ("," +  nioOffset);
            this.title += "," +  title ;
        }
    }

    public void addChapter(List chapter, List offset, List title,List nioOffset) {
        Assert.notNull(chapter);
        Assert.notNull(title);
        StringBuffer sb = new StringBuffer();
        for (Object cha:chapter) {
            sb.append(cha + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str1 = sb.toString();
        sb.delete(0,sb.length());
        if (offset != null) {
            for (Object off:offset) {
                sb.append(off + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        String str2 = sb.toString();

        sb.delete(0,sb.length());
        for (Object tit:title) {
            sb.append(tit + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str3 = sb.toString();
        sb.delete(0,sb.length());
        if (nioOffset != null) {
            for (Object nio:nioOffset) {
                sb.append(nio + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        String str4 = sb.toString();

        if (this.chapter == null) {
            this.chapter = str1;
            this.offset = str2;
            this.title = str3;
            this.nioOffset = str4;
        }else {
            this.chapter +=  str1;
            this.offset +=  str2;
            this.title += str3;
            this.nioOffset += str4;
        }
    }


    public String getNioOffset() {
        return nioOffset;
    }

    public void setNioOffset(String nioOffset) {
        this.nioOffset = nioOffset;
    }

    public Long[] getNioOffsets() {
        if (getNioOffset() == null || getNioOffset().isEmpty()) return null;
        String[] chapters = getNioOffset().split(",");
        Long[] longs = new Long[chapters.length];
        for (int i = 0;i < chapters.length;i ++) {
            longs[i] = new Long(chapters[i]);
        }
        return longs;
    }

    public void setNioOffsets(Long[] nioOffsets) {
        this.nioOffsets = nioOffsets;
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
        if (getOffset() == null || getOffset().isEmpty()) return null;
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
