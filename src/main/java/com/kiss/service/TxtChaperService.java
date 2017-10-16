package com.kiss.service;

import com.kiss.dto.TxtChapterDto;
import com.kiss.model.TxtChapterMsgModel;
import com.kiss.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TxtChaperService  extends BaseService {


    /**
     * 获取txt图书的章节信息
     * @param txtSn txt图书编号
     * @param page 页数
     * @param limit 一页显示限制
     * @param sort 排序
     * */
    List chapterList(String txtSn, Integer page, Integer limit, String sort);
}
