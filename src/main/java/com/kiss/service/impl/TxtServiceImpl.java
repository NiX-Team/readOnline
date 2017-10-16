package com.kiss.service.impl;

import com.kiss.Exception.WebException;
import com.kiss.common.config.SystemConfig;
import com.kiss.dto.TxtChapterDto;
import com.kiss.jpa.TxtModelJpa;
import com.kiss.model.TxtChapterMsgModel;
import com.kiss.model.TxtModel;
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

@Service
public class TxtServiceImpl extends BaseServiceImpl implements TxtService{
    @Autowired
    private TxtModelJpa txtJpa;

    @Override
    protected void init() {
        jpaRepository = txtJpa;
    }

    @Override
    public void save(TxtModel o, MultipartFile txtFile,MultipartFile coverImg) throws Exception{
        if (!txtFile.getOriginalFilename().matches(".*[.]txt"))
            throw new WebException(1,"只支持txt文件");
        //上传了封面文件
        if (coverImg != null) {
            if (!coverImg.getOriginalFilename().matches(".*[.]jpg"))
                throw new WebException(2,"不支持该图片作为书页面");
            File coverFile = new File(SystemConfig.getCoverPath() + coverImg.getOriginalFilename());
            coverImg.transferTo(coverFile);
            o.setCoverName(coverImg.getOriginalFilename());
        }
        //获取txt文件和封面文件
        File txtFile1 = new File(SystemConfig.getTxtPath() + txtFile.getOriginalFilename());
        txtFile.transferTo(txtFile1);
        o.setTxtName(txtFile.getOriginalFilename());
        o.setChapters(txtChapterMsgLists(txtFile1,o.getSn()));
        save(o);
    }

    @Override
    public String readChapter(String txtSn, TxtChapterDto txtChapterDto, Integer page, Integer limit) {
        TxtModel model = findBySn(txtSn);
        File txtFile = new File(SystemConfig.getTxtPath() + model.getTxtName());
        Long startLine = txtChapterDto.getOffset();
        Long endSLine = model.getChapters().getOffsets()[txtChapterDto.getChapter() + 1];
        String date = TxtUtil.readStartToEndByStream(txtFile,startLine,endSLine);
        return date.substring((page-1)*limit,page*limit > date.length() ? date.length() : page*limit);
    }


    @Override
    public TxtModel findBySn(String sn) {
        TxtModel model = new TxtModel();
        model.setSn(sn);
        return (TxtModel) jpaRepository.findOne(Example.of(model));
    }

    /**
     * 找出txt的章节信息
     * @param file txt文件
     * @param txtSn txt图书编号
     * */
    private TxtChapterMsgModel txtChapterMsgLists(File file,String txtSn) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            String line;
            int chapterCount = 0;
            long lineCount = 0;
            List chapters = new ArrayList();
            List offsets = new ArrayList();
            List titles = new ArrayList();
            TxtChapterMsgModel model = new TxtChapterMsgModel();
            while ((line = reader.readLine()) != null) {
                if (line.matches("第.*")) {
                    chapters.add(chapterCount++);
                    offsets.add(lineCount);
                    titles.add(line);
                }
                lineCount++;
            }
            model.addChapter(chapters,offsets,titles);
            model.setTxtSn(txtSn);
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
