package com.kiss.service;

import com.kiss.dto.TxtChapterDto;
import com.kiss.model.TxtChapterMsgModel;
import com.kiss.model.TxtModel;
import com.kiss.service.base.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface TxtService extends BaseService {
    /**
     * 添加一本txt图书
     * @param o TXTModel 存放txt信息
     * @param txtFile txt文件
     * @param coverImg txt图书封面图片
     * */
    void save(TxtModel o, MultipartFile txtFile,MultipartFile coverImg) throws Exception;

    /**
     * 阅读
     * */
    String readChapter(String txtSn, TxtChapterDto txtChapterDto,Integer page,Integer limit);

    /**
     * 通过txt编号查找txt信息
     * */
    TxtModel findBySn(String sn);
}
