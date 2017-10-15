package com.kiss.service;

import com.kiss.model.TxtModel;
import com.kiss.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface TxtService extends BaseService {
    void save(TxtModel o, MultipartFile file) throws Exception;
}
