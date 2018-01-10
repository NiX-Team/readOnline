package com.kiss.controller;

import com.kiss.Exception.WebException;
import com.kiss.common.ReturnObject;
import com.kiss.dto.TxtChapterDto;
import com.kiss.model.TxtModel;
import com.kiss.service.TxtChapterService;
import com.kiss.service.TxtService;
import com.kiss.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TxtController {
    @Autowired
    private TxtService txtService;
    @Qualifier("txtChapterServiceImpl")
    @Autowired
    private TxtChapterService chapterService;


    /**
     * 获取txt图书列表
     * */
    @GetMapping("/all")
    public List all() {
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
        System.out.println(txtFile.getOriginalFilename() + "---" + coverImg.getOriginalFilename());
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
        return ReturnUtil.success(chapterService.chapterList(txtSn,page,limit,sort));
    }

    /**
     * 提交章节信息  返回章节内
     ** @param txtSn txt图书编号
     * @param limit 阅读每页显示字数
     * @return 返回格式{"code":1,"msg":"SUCCESS","date":{"nextChapter":下一章节信息,"txt":阅读内容}}
     * */
    @GetMapping("/read/{txtSn}/{page}")
    public ReturnObject readChapter(
            @PathVariable("txtSn") String txtSn,@PathVariable("page") Integer page, @ModelAttribute TxtChapterDto dto,
            @RequestParam(value = "limit",defaultValue = "1204") Integer limit
            ) {
        Map<String,Object> map = new HashMap<>();
        map.put("txt",txtService.readChapter(txtSn,dto,page,limit));
        map.put("nextChapter",chapterService.nextChapter(txtService.findBySn(txtSn),dto.getChapter()));
        return ReturnUtil.success(map);
    }


    /**
     * 删除txt图书
     * */
    @GetMapping("/delete/{txtSn}")
    public ReturnObject delete(@PathVariable("txtSn") String txtSn) throws WebException {
        return ReturnUtil.success(txtService.delete(txtSn));
    }


    @ResponseBody
    @PostMapping("post_file")
    public String postFile(MultipartFile file) {
        System.out.println("file-legth=" + file.getSize());
        return "SUCCESS";
    }
}
