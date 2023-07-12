package com.example.springboot2022nocv.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot2022nocv.dao.ChinaTotalMapper;
import com.example.springboot2022nocv.entity.ChinaTotal;
import org.springframework.stereotype.Service;

@Service
public class ChinaTotalServiceImpl extends ServiceImpl<ChinaTotalMapper,ChinaTotal> implements ChinaTotalService{
}
