package com.kiss.service;

import com.kiss.Exception.WebException;
import com.kiss.dto.TxtChapterDto;
import com.kiss.model.TxtModel;
import com.kiss.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 11723
 */
@Service
public interface TxtService extends BaseService<TxtModel,String> {
    /**
     * 添加一本txt图书
     * @param o TXTModel 存放txt信息
     * @param txtFile txt文件
     * @param coverImg txt图书封面图片
     * @throws Exception
     * */
    void save(TxtModel o, MultipartFile txtFile,MultipartFile coverImg) throws Exception;

    /**
     * 阅读
     * @param txtSn txt图书编号
     * @param txtChapterDto 读取章节信息
     * @param page 当前章节阅读页数
     * @param limit 当前页显示限制字数
     * @return 返回当前页面txt内容
     * */
    String readChapter(String txtSn, TxtChapterDto txtChapterDto,Integer page,Integer limit);



    /**
     * 通过txt编号查找txt信息
     * @param sn
     * @return 返回txt图书信息
     * */
    TxtModel findBySn(String sn);

    /**
     * 删除txt图书
     * @param txtSn txt图书编号
     * @return 删除是否成功
     * @throws WebException web异常
     * */
    boolean delete(String txtSn) throws WebException;

}
