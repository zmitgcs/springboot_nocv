package com.example.springboot2022nocv.vo;
//视图返回包

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor  //无参构造函数
public class DataView {
    private Integer code=0;//状态码
    private String msg="";//
    private Long count=0L;//消息条数
    private Object data;//返回的数据格式

    public DataView(Long count,Object data){
        this.count=count;
        this.data=data;
    }

    public DataView(Object data){
        this.data=data;
    }
}
