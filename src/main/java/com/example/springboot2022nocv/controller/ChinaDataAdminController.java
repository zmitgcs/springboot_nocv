package com.example.springboot2022nocv.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot2022nocv.entity.NocvData;
import com.example.springboot2022nocv.service.IndexService;
import com.example.springboot2022nocv.vo.DataView;
import com.example.springboot2022nocv.vo.NocvDataVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChinaDataAdminController {
    //1.跳转页面
    @Autowired
    private IndexService indexService;

    //跳转页面
    @RequestMapping("toChinaManager")
    public String toChinaManager() {
        return "admin/chinadatamanager";
    }

    /**
     * 模糊查询带分页
     */
    @RequestMapping("/listDataByPage")
    @ResponseBody
    public DataView listDataByPage(NocvDataVo nocvDataVo) {
        //1.创建分页的对象,当前页，每页限制大小
        IPage<NocvData> page = new Page<>(nocvDataVo.getPage(), nocvDataVo.getLimit());
        //2.创建mybatisplus 模糊查询条件（QueryWrapper）
        QueryWrapper<NocvData> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!(nocvDataVo.getName() == null), "name", nocvDataVo.getName());
        //3.疫情数据确诊最多的排在最上面
        queryWrapper.orderByDesc("value");
        //4.查询数据库
        indexService.page(page, queryWrapper);
        //5.返回数据封装
        DataView dataView = new DataView(page.getTotal(), page.getRecords());

        return dataView;
    }

    /**
     * 删除数据根据id
     */
    @RequestMapping("/china/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id) {
        indexService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除中国地图数据成功！");
        return dataView;

    }


    /*
    * 新增或者修改    用ID判断
    * ID:nocvdata  有值修改，没有值就是新增
    * updata nocv_data set name=" "  where id=？
    *
    * */
    @RequestMapping("/china/addOrUpdateChina")
    @ResponseBody
    public DataView addOrUpdateChina(NocvData nocvData) {
        boolean save = indexService.saveOrUpdate(nocvData);
        DataView dataView = new DataView();
        if (save) {
            dataView.setCode(200);
            dataView.setMsg("新增中国地图数据成功！");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("新增中国地图数据失败！");
        return dataView;
    }


    /*
    * Excel的拖拽或者点击上传
    * 1.前台页面发送一个请求，上传文件MutilpartFile http
    * 2.Controller接受的参数 ，上传文件MutilpartFIle参数
    * 3.poi解析文件，将里面数据一行一行解析出来
    * 4.每一条数据插入数据库
    * */

    @RequestMapping("/excelImportChina")
    @ResponseBody

    public DataView excelImportChina(@RequestParam("file") MultipartFile file)throws Exception{
        DataView dataView = new DataView();
        //1.文件不能为空
        if (file.isEmpty()) {
            dataView.setMsg("文件为空，不能上传！");
        }

        //2.POI获取Excel解析数据
        HSSFWorkbook wb=new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet= wb.getSheetAt(0);

        //3.定义一个程序集合，接受文件中的数据
        List<NocvData> list=new ArrayList<>();
        HSSFRow row = null;


        //4.遍历Excel数据（解析数据），装到集合里面
        for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
            //4.1定义实体
            NocvData nocvData=new NocvData();
            //4.2每一行的数据放到实体类里面
            row = sheet.getRow(i);
            //4.3解析数据
            nocvData.setName(row.getCell(0).getStringCellValue());//拿到城市名称
            nocvData.setValue((int)row.getCell(1).getNumericCellValue());//拿到城市数据

            //5.添加list集合
            list.add(nocvData);

        }

        //6.插入数据库
        indexService.saveBatch(list);
        dataView.setCode(200);
        dataView.setMsg("excel插入成功");
        return dataView;

    }


    //导出Excel数据
    /*
    *1.查询数据库
    * 2.建立Excel对象，封装数据
    * 3.建立输出流，输出文件
     */
    @RequestMapping("/excelOutportChina")
    @ResponseBody
    public void excelOutportChina(HttpServletResponse response) throws Exception {
        //1.查询数据库
        List<NocvData> list=indexService.list();
        //2.建立excel，封装数据
        response.setCharacterEncoding("UTF-8");
        //2.1创建excel对象
        HSSFWorkbook wb= new HSSFWorkbook();
        //2.2创建sheet对象
        HSSFSheet  sheet=wb.createSheet("疫情数据sheet1");
        //2.3创建表头
        HSSFRow hssfRow=sheet.createRow(0);
        hssfRow.createCell(0).setCellValue("城市名称");
        hssfRow.createCell(1).setCellValue("确诊数量");
        //3.遍历数据，封装Excel工作对象
        for(NocvData data:list) {
            HSSFRow dataRow=sheet.createRow(sheet.getLastRowNum()+1);
            dataRow.createCell(0).setCellValue(data.getName());
            dataRow.createCell(1).setCellValue(data.getValue());
        }
        //4.建立输出流，输出浏览器文件
        OutputStream os =null;
        //4.1设置输出的Excel的名字,输出类型编码
        response.setContentType("application/octet-stream;chartset=UTF8");
        response.setHeader("content-Disposition","attachment; filename="+new String("中国疫情数据表".getBytes(),"iso-8859-1")+".xls");
        //4.2输出文件
        os=response.getOutputStream();
        wb.write(os);
        os.flush();
        //5.关闭输出流
        os.close();
    }

}
