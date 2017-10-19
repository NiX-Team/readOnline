package com.kiss.service.impl;

import com.kiss.Exception.WebException;
import com.kiss.cache.supper.CacheKey;
import com.kiss.common.Const;
import com.kiss.common.TxtMonitoring;
import com.kiss.common.TxtRead;
import com.kiss.common.config.SystemConfig;
import com.kiss.common.supper.NioTxtRead;
import com.kiss.dto.TxtChapterDto;
import com.kiss.jpa.TxtModelJpa;
import com.kiss.model.TxtChapterMsgModel;
import com.kiss.model.TxtModel;
import com.kiss.monitor.BeMonitorObj;
import com.kiss.service.base.BaseServiceImpl;
import com.kiss.service.TxtService;
import com.kiss.service.supper.TxtContentByteCache;
import com.kiss.service.supper.TxtModelCount;
import com.kiss.util.CommonUtil;
import com.kiss.util.TxtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 11723
 */
@Service
public class TxtServiceImpl extends BaseServiceImpl implements TxtService{

    private final static ConcurrentHashMap<String,TxtModelCount> TXT_MODEL_COUNT = new ConcurrentHashMap<>();


    @Autowired
    private TxtModelJpa txtJpa;

    @Autowired
    private NioTxtRead txtRead;

    @Override
    protected void init() {
        jpaRepository = txtJpa;
    }

    @Override
    public void save(TxtModel o, MultipartFile txtFile,MultipartFile coverImg) throws Exception{
        if (!txtFile.getOriginalFilename().matches(".*[.]txt")) {
            throw new WebException(1, "只支持txt文件");
        }
        //上传了封面文件
        if (coverImg != null) {
            if (!coverImg.getOriginalFilename().matches(".*[.]jpg")) {
                throw new WebException(2, "不支持该图片作为书页面");
            }
            File coverFile = new File(SystemConfig.getCoverPath() + coverImg.getOriginalFilename());
            coverImg.transferTo(coverFile);
            o.setCoverName(coverImg.getOriginalFilename());
        }
        //获取txt文件和封面文件
        File txtFile1 = new File(SystemConfig.getTxtPath() + txtFile.getOriginalFilename());
        txtFile.transferTo(txtFile1);
        o.setTxtName(txtFile.getOriginalFilename());
        o.setChapters(txtRead.txtChapterMsgLists(txtFile1,o.getSn(),"GBK"));
        save(o);
    }

    @Override
    public String readChapter(String txtSn, TxtChapterDto txtChapterDto, Integer page, Integer limit) {
        TxtModel model = findBySn(txtSn);
        File txtFile = new File(SystemConfig.getTxtPath() + model.getTxtName());
        String date;
        txtModeAddOne(model);
        if (model.getChapters().getNioOffsets() != null) {
            //nio读取方式
            int startOffset = Math.toIntExact(txtChapterDto.getNioOffset());
            int endOffset = Math.toIntExact(model.getChapters().getNioOffsets()[txtChapterDto.getChapter() + 1]);
            byte[] bytes = getCacheContent(model);
            if (bytes == null){
                date = TxtUtil.readStartToEndByNio(txtFile,startOffset,endOffset,"GBK");
            }else {
                date = TxtUtil.readStartToEndByBytes(bytes,startOffset,endOffset,"GBK");
            }
        }else {
            //stream读取方式
            Long startLine = txtChapterDto.getOffset();
            Long endSLine = model.getChapters().getOffsets()[txtChapterDto.getChapter() + 1];
            date = TxtUtil.readStartToEndByStream(txtFile,startLine,endSLine);
        }
        return date.substring((page-1)*limit,page*limit > date.length() ? date.length() : page*limit);
    }

    private byte[] getCacheContent(TxtModel model) {
        TxtModelCount txtModelCount = TXT_MODEL_COUNT.get(model.getSn());
        CacheKey cacheKey = Const.CACHE.get(txtModelCount.getCacheKey());
        if (cacheKey == null) {
            return null;
        }else {
            return ((TxtContentByteCache)cacheKey).getContent();
        }
    }


    /**
     * txt阅读加一
     * */
    private void txtModeAddOne(TxtModel model) {
        synchronized(TXT_MODEL_COUNT){
            TxtModelCount txtModelCount = TXT_MODEL_COUNT.get(model.getSn());
            if (txtModelCount == null) {
                txtModelCount = new TxtModelCount(new File(SystemConfig.getTxtPath() + model.getTxtName()));
                TXT_MODEL_COUNT.put(model.getSn(),txtModelCount);
            }else {
                txtModelCount.add();
            }
        }
    }


    @Override
    public void count(HttpServletRequest request, String txtSn) {

    }



    @Override
    public TxtModel findBySn(String sn) {
        TxtModel model = new TxtModel();
        model.setSn(sn);
        return (TxtModel) jpaRepository.findOne(Example.of(model));
    }


}
