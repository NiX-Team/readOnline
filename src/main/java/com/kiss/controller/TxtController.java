package com.kiss.controller;

import com.kiss.common.ReturnObject;
import com.kiss.model.TxtModel;
import com.kiss.service.TxtService;
import com.kiss.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TxtController {
    @Autowired
    private TxtService txtService;

    /**
     * 获取txt图书列表
     * */
    @GetMapping("/all")
    public List<TxtModel> all() {
        return txtService.findAll();
    }

    /**
     * 添加txt图书
     * @param model txt的信息
     * @param file txt文件
     * @return txt文件保存成功返回{"status":1,"msg":"SUCCESS","data":null}
     * 失败返回{"status":-1,"msg":"FAIL","data":错误信息}
     * */
    @ResponseBody
    @PostMapping("/add")
    public ReturnObject save(@Valid@ModelAttribute TxtModel model,@RequestParam(value = "file") MultipartFile file) throws Exception {
        txtService.save(model,file);
        return ReturnUtil.success(null);
    }
}
