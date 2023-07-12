package com.example.springboot2022nocv.vo;

import com.example.springboot2022nocv.entity.NocvData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NocvDataVo extends NocvData {
    private Integer page;
    private Integer limit;
}
