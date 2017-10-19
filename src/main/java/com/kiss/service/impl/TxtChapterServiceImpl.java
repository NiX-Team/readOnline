package com.kiss.service.impl;
import com.kiss.dto.TxtChapterDto;
import com.kiss.jpa.TxtChapterJpa;
import com.kiss.model.TxtChapterMsgModel;
import com.kiss.model.TxtModel;
import com.kiss.service.TxtChapterService;
import com.kiss.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
/**
 * @author 11723
 */
@Service
public class TxtChapterServiceImpl  extends BaseServiceImpl<TxtChapterMsgModel,Integer> implements TxtChapterService {

    @Autowired
    private TxtChapterJpa chapterJpa;

    @Override
    protected void init() {
        jpaRepository = chapterJpa;
    }

    @Override
    public List chapterList(String txtSn, Integer page, Integer limit, String sort) {
        TxtChapterMsgModel model = new TxtChapterMsgModel();
        model.setTxtSn(txtSn);
        model = jpaRepository.findOne(Example.of(model,ExampleMatcher.matching().withMatcher("txtSn", ExampleMatcher.GenericPropertyMatchers.exact())));
        Assert.notNull(model,"不存在该图书");
        List list = new ArrayList<>();
        for (int i = 0 ;i < limit;i ++ ) {
            TxtChapterDto model1 = new TxtChapterDto();
            if ("asc".equals(sort.toLowerCase())) {
                model1.setChapter(model.getChapters()[(page - 1) * limit + i]);
                model1.setOffset(model.getNioOffsets() != null ? null : model.getOffsets()[(page - 1) * limit + i]);
                model1.setTitle(model.getTitles()[(page - 1) * limit + i]);
                model1.setNioOffset(model.getNioOffsets() == null ? null : model.getNioOffsets()[(page - 1) * limit + i]);
            }else {
                model1.setChapter(model.getChapters()[model.getChapters().length - 1 - ((page - 1) * limit + i)]);
                model1.setOffset(model.getOffsets() == null ? null : model.getOffsets()[model.getChapters().length - 1 - ((page - 1) * limit + i)]);
                model1.setTitle(model.getTitles()[model.getChapters().length - 1 - ((page - 1) * limit + i)]);
                model1.setNioOffset(model.getNioOffsets() == null ? null : model.getNioOffsets()[model.getChapters().length - 1 - ((page - 1) * limit + i)]);
            }
            list.add(model1);
        }
        return list;
    }

    @Override
    public TxtChapterDto nextChapter(TxtModel txtModel, Integer nowChapter) {
        if (nowChapter == txtModel.getChapters().getChapters().length - 1) {
            return null;
        }
        TxtChapterDto dto = new TxtChapterDto();
        TxtChapterMsgModel model = txtModel.getChapters();
        dto.setChapter(model.getChapters()[nowChapter + 1]);
        dto.setOffset(model.getOffsets() == null ? null : model.getOffsets()[nowChapter + 1]);
        dto.setNioOffset(model.getNioOffsets() == null ? null : model.getNioOffsets()[nowChapter + 1]);
        dto.setTitle(model.getTitles()[nowChapter + 1]);
        return dto;
    }
}
