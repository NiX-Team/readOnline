package com.kiss.controller;

import com.kiss.common.ReturnObject;
import com.kiss.dto.TxtChapterDto;
import com.kiss.model.TxtModel;
import com.kiss.service.TxtChaperService;
import com.kiss.service.TxtService;
import com.kiss.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class TxtController {
    @Autowired
    private TxtService txtService;
    @Autowired
    private TxtChaperService chaperService;


    /**
     * 获取txt图书列表
     * */
    @GetMapping("/all")
    public List<TxtModel> all() {
        return txtService.findAll();
    }

    /**
     * 添加txt图书
     * @param model txt图书信息
     * @param txtFile txt图书文件
     * @param coverImg txt图书封面文件
     * @return txt文件保存成功返回{"status":1,"msg":"SUCCESS","data":null}
     * 失败返回{"status":-1,"msg":"FAIL","data":错误信息}
     * */
    @PostMapping("/add")
    public ReturnObject save(@Valid@ModelAttribute TxtModel model,
                             @RequestParam(value = "txtFile") MultipartFile txtFile,
                             @RequestParam(value = "coverImg",required = false) MultipartFile coverImg) throws Exception {
        txtService.save(model,txtFile,coverImg);
        return ReturnUtil.success(null);
    }

    /**
     * 获取图书的章节信息
     * @param txtSn txt图书编号
     * @param page 章节信息页
     * @param limit 当前页显示多少章节
     * @param sort 排序方式
     * */
    @GetMapping("/chapter/{txtSn}")
    public ReturnObject chapterList(@PathVariable("txtSn") String txtSn,@RequestParam(value = "page",defaultValue = "1") Integer page,
                                    @RequestParam(value = "limit",defaultValue = "100") Integer limit,
                                    @RequestParam(value = "sort",defaultValue = "asc") String sort) {
        return ReturnUtil.success(chaperService.chapterList(txtSn,page,limit,sort));
    }

    /**
     * 提交章节信息  返回章节内
     ** @param txtSn txt图书编号
     * @param limit 阅读每页显示字数
     * @return 返回格式{"code":1,"haveNext(是否有下页)":true,"date":txt内容}
     * */
    @GetMapping("/read/{txtSn}/{page}")
    public ReturnObject readChapter(
            @PathVariable("txtSn") String txtSn,@PathVariable("page") Integer page, @ModelAttribute TxtChapterDto dto,
            @RequestParam(value = "limit",defaultValue = "1204") Integer limit
            ) {
        return ReturnUtil.success(txtService.readChapter(txtSn,dto,page,limit));
    }
}
