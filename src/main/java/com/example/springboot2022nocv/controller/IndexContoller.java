package com.example.springboot2022nocv.controller;


import com.example.springboot2022nocv.entity.LineTrend;
import com.example.springboot2022nocv.entity.NocvData;
import com.example.springboot2022nocv.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexContoller {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/query")
    @ResponseBody
    public List<NocvData> queryData(){
        List<NocvData> list=indexService.list();
        return list;
    }
    //跳转pie页面
    @RequestMapping("/toPie")
    public String toPie(){
        return"pie";
    }
  /**
   * 分组聚合
   * select count(*)
   * */
  @RequestMapping("/queryPie")
  @ResponseBody
  public List<NocvData> queryPieData(){
      List<NocvData> list=indexService.list();
      return list;
  }

    /***
     * 跳转柱状图页面
     */
    //跳转bar页面
    @RequestMapping("/toBar")
    public String toBar(){
        return"bar";
    }

    @RequestMapping("/queryBar")
    @ResponseBody
    public Map<String,List<Object>> queryBarData(){
        //1.所有城市数据：数值
        List<NocvData> list=indexService.list();
        //2.拿到所有城市数据
        List<String> cityList=new ArrayList<>();
        for(NocvData data :list){
            cityList.add(data.getName());
        }
        //3.拿到所有疫情数值数据
        List<Integer> dataList=new ArrayList<>();
        for(NocvData data :list){
            dataList.add(data.getValue());
        }
        //4.创建Map容器
        Map map=new HashMap();
        map.put("cityList",cityList);
        map.put("dataList",dataList);

        return map;
    }



    /***
     * 跳转折线图页面
     */
    //跳转bar页面
    @RequestMapping("/toLine")
    public String toLine(){
        return"line";
    }
    @RequestMapping("/queryLine")
    @ResponseBody
    public Map<String,List<Object>> queryLineData(){
        //1.近七天查询所有的数据
        List<LineTrend> list7Day = indexService.findSevenData();
        //2.查询所有确诊数据（封装所有的确诊人数）
        List<Integer>  confirmList = new ArrayList<>();
        //3.查询所有隔离人数数据（封装所有的隔离人数）
        List<Integer>  isolationList = new ArrayList<>();
        //4.查询所有治愈人数数据（封装所有的治愈人数）
        List<Integer>  cureList = new ArrayList<>();
        //5.查询所有死亡人数数据（封装所有的死亡人数）
        List<Integer>  deadList = new ArrayList<>();
        //6.查询所有疑似人数数据（封装所有的疑似人数）
        List<Integer> similarList = new ArrayList<>();

        for(LineTrend data : list7Day){
            confirmList.add(data.getConfirm());
            isolationList.add(data.getIsolation());
            cureList.add(data.getCure());
            deadList.add(data.getDead());
            similarList.add(data.getSimilar());
        }
        //用一个大的集合装返回的数据（返回数据的格式容器map）
        Map map = new HashMap();
        map.put("confirmList",confirmList);
        map.put("isolationList",isolationList);
        map.put("cureList",cureList);
        map.put("deadList",deadList);
        map.put("similarList",similarList);

        return map;
    }



}
