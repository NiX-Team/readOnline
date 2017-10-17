package com.kiss.service;

import com.kiss.dto.TxtChapterDto;
import com.kiss.model.TxtChapterMsgModel;
import com.kiss.model.TxtModel;
import com.kiss.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TxtChapterService  extends BaseService {


    /**
     * 获取txt图书的章节信息
     * @param txtSn txt图书编号
     * @param page 页数
     * @param limit 一页显示限制
     * @param sort 排序
     * */
    List chapterList(String txtSn, Integer page, Integer limit, String sort);

    /**
     * 获取当前章节的下一章节信息
     * @param txtModel  图书信息
     * @param nowChapter 当前章节数
     * @return 返回下一章节的信息 {@link TxtChapterDto}
     * */
    TxtChapterDto nextChapter(TxtModel txtModel,Integer nowChapter);
}
