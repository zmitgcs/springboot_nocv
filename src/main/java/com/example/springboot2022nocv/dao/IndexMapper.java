package com.example.springboot2022nocv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot2022nocv.entity.LineTrend;
import com.example.springboot2022nocv.entity.NocvData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IndexMapper extends BaseMapper<NocvData> {

   /**
    * 接口：只有方法定义，写业务逻辑
    * 1.实现类，写自己的业务逻辑
    * 2.xml mybatisplus 一种实现
    * 3.@Select在里面直接写sql
    * */
    @Select("select * from line_trend order by create_time limit 7")
    List<LineTrend> findSevenData();
}
