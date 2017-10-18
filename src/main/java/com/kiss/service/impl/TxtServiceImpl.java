package com.kiss.service.impl;

import com.kiss.Exception.WebException;
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
import com.kiss.util.CommonUtil;
import com.kiss.util.TxtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11723
 */
@Service
public class TxtServiceImpl extends BaseServiceImpl implements TxtService,BeMonitorObj{
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
        if (model.getChapters().getNioOffsets() != null) {
            //nio读取方式
            int startOffset = Math.toIntExact(txtChapterDto.getNioOffset());
            int endOffset = Math.toIntExact(model.getChapters().getNioOffsets()[txtChapterDto.getChapter() + 1]);
            date = TxtUtil.readStartToEndByNio(txtFile,startOffset,endOffset,"GBK");
        }else {
            //stream读取方式
            Long startLine = txtChapterDto.getOffset();
            Long endSLine = model.getChapters().getOffsets()[txtChapterDto.getChapter() + 1];
            date = TxtUtil.readStartToEndByStream(txtFile,startLine,endSLine);
        }
        return date.substring((page-1)*limit,page*limit > date.length() ? date.length() : page*limit);
    }


    


    @Override
    public TxtModel findBySn(String sn) {
        TxtModel model = new TxtModel();
        model.setSn(sn);
        return (TxtModel) jpaRepository.findOne(Example.of(model));
    }

    @Override
    public boolean addMonitor() {
        return TxtMonitoring.TXT_MONITORING.addBeMonitorObj(this);
    }

    @Override
    public boolean getStartMission() {
        return IDENTIFICATION.isStartMission();
    }

    @Override
    public void setStartMission(boolean startMission) {
        IDENTIFICATION.setStartMission(startMission);
    }

    @Override
    public void run() {
        System.out.println("ok");
        setStatus(false);

    }

    @Override
    public void setAllowRun(boolean allowRun) {
        IDENTIFICATION.setAllowRun(allowRun);
    }

    @Override
    public boolean getAllowRun() {
        return IDENTIFICATION.isAllowRun();
    }

    @Override
    public void setStatus(boolean status) {
        IDENTIFICATION.setStatus(status);

    }

    @Override
    public boolean getStatus() {
        return IDENTIFICATION.isStatus();
    }
}
