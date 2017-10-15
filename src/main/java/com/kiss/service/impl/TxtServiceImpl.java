package com.kiss.service.impl;

import com.kiss.Exception.WebException;
import com.kiss.jpa.TxtModelJpa;
import com.kiss.model.TxtModel;
import com.kiss.service.base.BaseServiceImpl;
import com.kiss.service.TxtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Service
public class TxtServiceImpl extends BaseServiceImpl implements TxtService{
    @Autowired
    private TxtModelJpa txtJpa;

    @Override
    protected void init() {
        jpaRepository = txtJpa;
    }

    @Override
    @Transactional
    public void save(TxtModel o, MultipartFile file) throws Exception{
        Assert.isNull(file,"文件为空");
        if (!file.getOriginalFilename().matches(".*[.]txt"))
            throw new WebException(1,"只支持txt文件");
        File txtFile = new File(o.getLogPath() + "/" + file.getOriginalFilename());
        file.transferTo(txtFile);
        save(o);
    }
}
