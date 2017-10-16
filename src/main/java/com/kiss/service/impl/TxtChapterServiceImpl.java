package com.kiss.service.impl;

import com.kiss.dto.TxtChapterDto;
import com.kiss.jpa.TxtChapterJpa;
import com.kiss.model.TxtChapterMsgModel;
import com.kiss.service.TxtChaperService;
import com.kiss.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TxtChapterServiceImpl  extends BaseServiceImpl implements TxtChaperService {

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
        model = (TxtChapterMsgModel) jpaRepository.findOne(Example.of(model,ExampleMatcher.matching().withMatcher("txtSn", ExampleMatcher.GenericPropertyMatchers.exact())));
        List list = new ArrayList<>();
        for (int i = 0 ;i < limit;i ++ ) {
            TxtChapterDto model1 = new TxtChapterDto();
            if ("asc".equals(sort.toLowerCase())) {
                model1.setChapter(model.getChapters()[(page - 1) * limit + i]);
                model1.setOffset(model.getOffsets()[(page - 1) * limit + i]);
                model1.setTitle(model.getTitles()[(page - 1) * limit + i]);
            }else {
                model1.setChapter(model.getChapters()[model.getChapters().length - 1 - ((page - 1) * limit + i)]);
                model1.setOffset(model.getOffsets()[model.getChapters().length - 1 - ((page - 1) * limit + i)]);
                model1.setTitle(model.getTitles()[model.getChapters().length - 1 - ((page - 1) * limit + i)]);
            }
            list.add(model1);
        }
        return list;
    }


}
