package com.example.springboot2022nocv.api;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpUtils {
    /*
    * 发送网络请求的工具
    * */
    public String getData()throws  Exception{
        //1.请求参数
        RequestConfig requestConfig= RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .build();
        //创建http客户端
        CloseableHttpClient httpClient=null;
        HttpGet request=null;
        CloseableHttpResponse response=null;

        try {
            //1.创建HttpClient
            httpClient = HttpClients.createDefault();
            //2.发送网络请求
            request = new HttpGet("https://c.m.163.com/ug/api/wuhan/app/data/list-total");
            //3.配置信息
            request.setConfig(requestConfig);
            //4.发送请求
            response = httpClient.execute(request);
            //5.看一下状态码是否成功
            int statusCode=response.getStatusLine().getStatusCode();
            if(statusCode==200){
                //6.解析数据
                HttpEntity entity=response.getEntity();
                String string=EntityUtils.toString(entity,"utf-8");
                return string;
            }
        }finally {
            if(response!=null){
                response.close();
            }
            if(request!=null){
                request.releaseConnection();
            }
            if(httpClient!=null){
                httpClient.close();
            }
        }
        return null;
    }
}
