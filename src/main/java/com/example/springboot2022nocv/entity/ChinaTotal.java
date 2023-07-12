package com.example.springboot2022nocv.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("china_total")
@Data
public class ChinaTotal {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer confirm;
    private Integer input;
    private Integer severe;
    private Integer heal;
    private Integer dead;
    private Integer suspect;
    private Date updateTime;


}
