package com.bos.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.basic.mapper.AreaMapper;
import com.bos.basic.mapper.FixedAreaMapper;
import com.bos.basic.service.AreaService;
import com.bos.pojo.basic.Area;
import com.bos.pojo.basic.FixedArea;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.util.PinYinUtil;
import com.bos.util.PoiUtil;
import com.bos.util.PostCodeUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@SuppressWarnings("all")
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaMapper areaMapper;
    @Resource
    private FixedAreaMapper fixedAreaMapper;

    /**
     * 获得区域列表，有分页
     * @param map
     * @return
     */
    @Override
    public Result getAreaList(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt(map.get("size"));
        IPage<Area> iPage = new Page<>(page,size);
        IPage<Area> iPageArea = areaMapper.selectPage(iPage, null);
        PageResult<Area> pageResult = new PageResult<>(iPageArea.getTotal(),iPageArea.getRecords());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 获得全部的Area，无分页
     * @return
     */
    @Override
    public Result getAreaAll() {
        List<Area> areas = areaMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,areas);
    }

    /**
     * 添加area
     * @param area
     * @return
     */
    @Override
    public Result addArea(Area area) {
        //已存在的则不允许重复添加，根据城市编码查询是否存在重复的数据
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",area.getCityCode());
        Integer integer = areaMapper.selectCount(queryWrapper);
        if(integer > 0){
            return new Result(ResultCode.CITY_IS_EXIT);
        }
        String address = area.getProvince() + area.getCity() + area.getDistrict();
        //设置简码
        String s = PinYinUtil.toFirstChar(address).toUpperCase();
        Integer num =  (int)((Math.random()*9+1)*100000);
        area.setBrevityCode(s.concat(num.toString()));
        //设置邮编
        //String postCodeByAddr = PostCodeUtil.getPostCodeByAddr(address);
        //area.setPostCode(postCodeByAddr);
        System.out.println(area);
        int insert = areaMapper.insert(area);
        if(insert > 0){
            return  Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 修改Area
     * @param area
     * @return
     */
    @Override
    public Result updateArea(Area area) {
        //如果换了城市，那么就需要重新生成城市简码
        String address = area.getProvince() + area.getCity() + area.getDistrict();
        //设置简码
        String s = PinYinUtil.toFirstChar(address).toUpperCase();
        Integer num =  (int)((Math.random()*9+1)*100000);
        area.setBrevityCode(s.concat(num.toString()));
        int i = areaMapper.updateById(area);
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除Area
     * @param area
     * @return
     */
    @Override
    public Result deleteArea(Area area) {
        QueryWrapper<FixedArea> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("area_id",area.getId());
        Integer integer = fixedAreaMapper.selectCount(queryWrapper);
        if(integer > 0){
            return new Result(ResultCode.AREA_EXIST_FIXEDAREA);
        }
        //执行删除
        int i = areaMapper.deleteById(area.getId());
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 导出Area外部Excel表格
     * @param file
     * @return
     */
    @Override
    public Result uploadExcel(HttpServletResponse response, MultipartFile file) throws IOException {

        List<String[]> strings = PoiUtil.readExcel(file);   //读取Excel表格的数据，返回 以每个对象为一个数组，多个数组为List的方式返回
        List<String> strings1 = PoiUtil.readExcelGetTableHeader(file, file.getOriginalFilename());//获取表头

        //获取上传文件的名称
        String fileName = file.getOriginalFilename();
        String newFileName = fileName.substring(0, fileName.lastIndexOf("."));

        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row rowHeader = sheet.createRow(0);
        String[] tableHeader = strings1.toArray(new String[strings1.size()]);//转换为数组
        for (int i = 0; i < tableHeader.length; i++) {
            rowHeader.createCell(i).setCellValue(tableHeader[i]);//创建表头
        }
        AtomicInteger tableRowAuto = new AtomicInteger(1);//创建线程安全计数器，从1开始
        //创建标记是否下载
        boolean isDown = false;

        //遍历集合，将每一个值赋值给每一个对象
        for (String[] string : strings) {
            //遍历出的每个string是一个数组
            System.out.println(string);
            if(checkExcel(string,6)){//数组对象可用为true
                Area area = new Area(string[0],string[1],string[2],string[3],string[4],string[5]);
                //添加数据库
                areaMapper.insert(area);
            }else{
                //检测不通过则标记需要下载
                isDown = true;
                Row row = sheet.createRow(tableRowAuto.getAndIncrement());
                for (int i = 0; i < string.length; i++) {
                    row.createCell(i).setCellValue(string[i]);
                }
            }
            System.out.println(string.getClass());
        }

        //执行下载
        if(isDown){
            //创建文件名称
//            String fileNewName = URLEncoder.encode("剩余数据","UTF-8") ;
//            //设置响应头octet-stream
//            response.setContentType("application/octet-stream;charset=UTF-8");//告诉浏览区要下载的文件类型的
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileNewName.getBytes(),"ISO-8859-1") );// 告诉浏览器这个文件的名字和类型，attachment：作为附件下载；inline：直接打开
//            //进行导出使用输出流
//            workbook.write(response.getOutputStream());
//            ServletOutputStream outputStream = response.getOutputStream();
            FileOutputStream fileOut = new FileOutputStream("E:\\upload\\"+newFileName+"错误数据.xlsx");
            workbook.write(fileOut);
            return new Result(ResultCode.EXCEL_HAVE_ERROR);
        }

        return new Result(ResultCode.EXCEL_IS_SUCCESS);
    }

    //检测数组中的元素是否可以添加数据库中
        private static boolean checkExcel(String[] strings,int num){

        //检测对象数组是否为空,长度不够
        if(strings.length != num){
            return false;
        }
        for (String string : strings) {
            if(string.equals("null") || string.equals("") || StringUtils.isEmpty(string)){
                return false;
            }
        }
        return true;
    }
}
