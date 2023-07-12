package com.example.springboot2022nocv.api;

import com.alibaba.fastjson.JSONObject;
import com.example.springboot2022nocv.entity.ChinaTotal;
import com.example.springboot2022nocv.service.ChinaTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
//定时任务
public class testapi {
    @Autowired
    private ChinaTotalService chinaTotalService;
/*
* 每小时执行一次，更新全国数据情况
* */

    /*public static void main(String[] args) throws Exception*/
    @Scheduled(fixedDelay=100000)  //10s执行一次
    /*@Scheduled(cron="0 0 8,9,10,11,12,13,18,20 * * ?")*/
    public  void updateChinaTotalToDB() throws Exception {

        HttpUtils httpUtils=new HttpUtils();
        String string=httpUtils.getData();
        /*System.out.println("原始数据："+string);*/

        //1.所有数据的alibaba格式
        JSONObject jsonObject=JSONObject.parseObject(string);
        Object data=jsonObject.get("data");
        /*System.out.println("data:"+data);*/

        //2.拿到data
        JSONObject jsonObjectData=JSONObject.parseObject(data.toString());
        Object chinaTotal=jsonObjectData.get("chinaTotal");
        Object lastUpdateTime = jsonObjectData.get("overseaLastUpdateTime");//最近更新时间
        /*System.out.println("chinaTotal:"+chinaTotal);*/

        //3.total全中国整体疫情数据
        JSONObject jsonObjectTotal=JSONObject.parseObject(chinaTotal.toString());
        Object total=jsonObjectTotal.get("total");
        System.out.println("total:"+total);

        //4.全国数据total
        JSONObject totalData=JSONObject.parseObject(total.toString());
        Object confirm=totalData.get("confirm");
        /*System.out.println("confirm:"+confirm);*/
        Object input=totalData.get("input");
        Object severe=totalData.get("severe");
        Object heal=totalData.get("heal");
        Object dead=totalData.get("dead");
        Object suspect=totalData.get("suspect");

      //为程序实体赋值
        ChinaTotal   dataEntity= new ChinaTotal();
        dataEntity.setConfirm( (Integer) confirm);
        dataEntity.setInput((Integer)input);
        dataEntity.setSevere( (Integer)severe);
        dataEntity.setHeal( (Integer)heal);
        dataEntity.setDead( (Integer)dead);
        dataEntity.setSuspect( (Integer)suspect);

        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        dataEntity.setUpdateTime(format.parse(String.valueOf(lastUpdateTime)));

        //6.插入数据库【插入】
        chinaTotalService.save(dataEntity);//插入，查看时显示最新数据










    }
}
