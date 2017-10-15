package com.kiss.controller;

import com.kiss.Exception.WebException;
import com.kiss.common.ReturnObject;
import com.kiss.common.config.SystemConfig;
import com.kiss.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {
    @Autowired
    private SystemConfig config;

    @GetMapping("/index")
    public ReturnObject index() {
        return ReturnUtil.success(config);
    }
    @GetMapping("/fail")
    public ReturnObject fail(@RequestParam("id") Integer id) throws WebException {
        if (id.equals(0)) throw new WebException(100,"id不合法");
        return ReturnUtil.success(config);
    }
}
