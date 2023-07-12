package com.example.springboot2022nocv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("nocv_data")
public class NocvData {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String name;
    private Integer value;
}
