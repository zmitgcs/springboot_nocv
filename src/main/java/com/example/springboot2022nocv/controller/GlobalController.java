package com.example.springboot2022nocv.controller;

import com.example.springboot2022nocv.entity.NocvData;
import com.example.springboot2022nocv.entity.NocvGlobalData;
import com.example.springboot2022nocv.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GlobalController {
    /***
     * 跳转折线图页面
     */
    @Autowired
    private GlobalService globalService;


    @RequestMapping("/toGlobal")
    public String toGlobal(){
        return"global";
    }

    @RequestMapping("/queryGlobal")
    @ResponseBody
    public List<NocvGlobalData> queryGlobalData(){
        List<NocvGlobalData> list=globalService.list();
        return list;
    }




}
