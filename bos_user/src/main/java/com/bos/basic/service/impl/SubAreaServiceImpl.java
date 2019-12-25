package com.bos.basic.service.impl;

import com.bos.basic.mapper.CourierMapper;
import com.bos.basic.mapper.SubArchiveMapper;
import com.bos.basic.mapper.SubAreaMapper;
import com.bos.basic.repository.SubAreaRepository;
import com.bos.basic.service.SubAreaService;
import com.bos.execption.MyException;
import com.bos.pojo.basic.Courier;
import com.bos.pojo.basic.SubArea;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.util.PoiUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@SuppressWarnings("all")
public class SubAreaServiceImpl implements SubAreaService {

    @Resource
    private SubAreaMapper subAreaMapper;
    @Resource
    private SubAreaRepository subAreaRepository;
    @Resource
    private CourierMapper courierMapper;

    /**
     * 获取分区列表，有分页
     * @param map
     * @return
     */
    @Override
    public Result getSubAreaList(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt(map.get("size"));
        Pageable pageable = PageRequest.of(page-1,size,new Sort(Sort.Direction.DESC,"id"));

        //构造条件
        Specification<SubArea> specification = new Specification<SubArea>() {
            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                //获得外部条件
                String keyWord = map.get("keyWord");
                String areaId = map.get("areaId");
                String fixedAreaId = map.get("fixedAreaId");

                List<Predicate> predicates = new ArrayList<>();

                if(!StringUtils.isEmpty(keyWord)) {
                   Predicate predicate = criteriaBuilder.like(root.get("keyWord").as(String.class),"%"+ keyWord.trim()+"%");
                    predicates.add(predicate);
                }
                if(!StringUtils.isEmpty(areaId)){
                    Predicate predicate = criteriaBuilder.equal(root.get("areaId").as(String.class),areaId);
                    predicates.add(predicate);
                }
                if(!StringUtils.isEmpty(fixedAreaId)){
                    Predicate predicate = criteriaBuilder.equal(root.get("fixedAreaId").as(String.class),fixedAreaId);
                    predicates.add(predicate);
                }
                //判断结合中是否有数据
                if (predicates.size() == 0) {
                    return null;
                }

                //将集合转化为CriteriaBuilder所需要的Predicate[]
                Predicate[] predicateArr = new Predicate[predicates.size()];
                predicateArr = predicates.toArray(predicateArr);

                // 返回所有获取的条件： 条件 and 条件 and 条件 and 条件
                return criteriaBuilder.and(predicateArr);
            }
        };

        Page<SubArea> all = subAreaRepository.findAll(specification,pageable);
        PageResult<SubArea> pageResult = new PageResult<>(all.getTotalElements(),all.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 添加分区
     * @param subArea
     * @return
     */
    @Override
    public Result addSubArea(SubArea subArea) {
        if(subArea.getFixedAreaId() == null || subArea.getFixedAreaId() == ""){
            return Result.FAIL();
        }
        int insert = subAreaMapper.insert(subArea);
        if(insert > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 修改分区
     * @param subArea
     * @return
     */
    @Override
    public Result updateSubArea(SubArea subArea) {
        if(StringUtils.isEmpty(subArea.getFixedAreaId())){
            return Result.FAIL();
        }
        int i = subAreaMapper.updateById(subArea);
        if(i >0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除分区
     * @param subArea
     * @return
     */
    @Override
    public Result deleteSubArea(SubArea subArea) throws MyException {
        try {
            subAreaRepository.deleteById(subArea.getId());
            return Result.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultCode.FAIL);
        }
    }

    /**
     * 关联快递员
     * @param subArea
     * @return
     */
    @Override
    public Result releCourierAndSubArea(SubArea subArea) throws MyException {
        try {
            SubArea targetSubArea = subAreaRepository.findById(subArea.getId()).get();
            BeanUtils.copyProperties(subArea,targetSubArea);
            String[] split = subArea.getCourierIds().split(",");
            List<Courier> couriers = new ArrayList<>();
            for (String s : split) {
                Courier courier = courierMapper.selectById(s);
                couriers.add(courier);
            }
            if(!StringUtils.isEmpty(couriers)){
                targetSubArea.setCouriers(couriers);
                //进行关联
                subAreaRepository.save(targetSubArea);
                return Result.SUCCESS();
            }
            return Result.FAIL();
        } catch (BeansException e) {
            e.printStackTrace();
            throw new MyException(ResultCode.FAIL);
        }
    }

    /**
     * 导出分区表
     * @param map
     * @return
     */
    @Override
    public Result exportSubArea(HttpServletResponse response,Map<String, Object> map) throws IOException {
        //获取Execl表头
        List<String> list = (ArrayList<String>)map.get("list");
        //排列有序表头
        List<String> tableList = new ArrayList<>();
        if(list.contains("分区关键字")) tableList.add("分区关键字");
        if(list.contains("分区辅助关键字")) tableList.add("分区辅助关键字");
        if(list.contains("单双号码")) tableList.add("单双号码");
        if(list.contains("起始号码")) tableList.add("起始号码");
        if(list.contains("终止号码")) tableList.add("终止号码");
        if(list.contains("分区负责人")) tableList.add("分区负责人");
        if(list.contains("分区负责人部门")) tableList.add("分区负责人部门");
        if(list.contains("电话号码")) tableList.add("电话号码");

        //创建Execl对象
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //创建Sheet对象
        Sheet sheet = workbook.createSheet("分区数据");

        //创建线程安全计数器，用作设置表头单元格的顺序，默认下标从0开始
        AtomicInteger tableHeaderAuto = new AtomicInteger();
        //创建表头，即获得下标为0的第一行
        Row tableRow = sheet.createRow(0);
        //设置表头行
        for (String s : tableList) {
            int andIncrement = tableHeaderAuto.getAndIncrement();
            tableRow.createCell(andIncrement).setCellValue(s);
//            sheet.setColumnWidth(andIncrement, 256*s.getBytes().length+184);
//            System.err.println(s.getBytes().length + " --- " +s.length());
        }

        Integer keyWord = 0;
        Integer assistKeyWord = 0;
        Integer single = 0;
        Integer startNum = 0;
        Integer endNum = 0;
        Integer subAreaLeader = 0;
        Integer company = 0;
        Integer telephone = 0;

        //查询数据
        List<SubArea> subAreas = subAreaMapper.selectList(null);
        //创建线程安全计数器，用于设置每行的位置
        AtomicInteger rowAuto = new AtomicInteger(1);//此时下标从1开始，因为第一行已经设置了表头
        for (SubArea subArea : subAreas) {//遍历获得每一个对象
            Row row = sheet.createRow(rowAuto.getAndIncrement());//每一个对象为一行
            for (int i = 0; i < tableList.size(); i++) {
                if(tableList.get(i).equals("分区关键字")) {
                    row.createCell(i).setCellValue(subArea.getKeyWord());
                    if(subArea.getKeyWord().getBytes().length > keyWord){ //判断单元格最大宽度
                        keyWord = subArea.getKeyWord().getBytes().length; //设置最大长度
                        sheet.setColumnWidth(i, 256*keyWord+1000); //  参数一 列下标 、参数二 列宽度
                    }
                }
                if(tableList.get(i).equals("分区辅助关键字")){ row.createCell(i).setCellValue(subArea.getAssistKeyWord());}
                if(tableList.get(i).equals("单双号码")) {row.createCell(i).setCellValue(subArea.getSingle());}
                if(tableList.get(i).equals("起始号码")) {row.createCell(i).setCellValue(subArea.getStartNum());}
                if(tableList.get(i).equals("终止号码")) {row.createCell(i).setCellValue(subArea.getEndNum());}
                if(tableList.get(i).equals("分区负责人")) row.createCell(i).setCellValue(subArea.getSubAreaLeader());
                if(tableList.get(i).equals("分区负责人部门")) row.createCell(i).setCellValue(subArea.getCompany());
                if(tableList.get(i).equals("电话号码")) row.createCell(i).setCellValue(subArea.getTelephone());
            }
        }


        //创建文件名称
        String fileName = URLEncoder.encode("分区数据.xlsx","UTF-8") ;
        //设置响应头octet-stream
        response.setContentType("application/octet-stream;charset=UTF-8");//告诉浏览区要下载的文件类型的
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(),"ISO-8859-1") );// 告诉浏览器这个文件的名字和类型，attachment：作为附件下载；inline：直接打开
        //进行导出使用输出流
        workbook.write(response.getOutputStream());

        return null;
    }

    /**
     * 实用工具类进行导出Execl
     * @param map
     * @return
     */
    @Override
    public Result UtilExportExecl(HttpServletResponse response,Map<String, Object> map){
        //获得标题
        String title = map.get("title").toString();
        //获取Execl表头
        List<String> list = (ArrayList<String>)map.get("list");
        //排列有序表头
        List<String> tableList = new ArrayList<>();
        //有序对象属性名
        List<String> colNames = new ArrayList<>();
        if(list.contains("分区关键字")){ tableList.add("分区关键字"); colNames.add("keyWord");}
        if(list.contains("分区辅助关键字")){ tableList.add("分区辅助关键字");colNames.add("assistKeyWord");}
        if(list.contains("单双号码")) {tableList.add("单双号码"); colNames.add("single");}
        if(list.contains("起始号码")) {tableList.add("起始号码");colNames.add("startNum");}
        if(list.contains("终止号码")) {tableList.add("终止号码");colNames.add("endNum");}
        if(list.contains("分区负责人")) {tableList.add("分区负责人");colNames.add("subAreaLeader");}
        if(list.contains("分区负责人部门")){ tableList.add("分区负责人部门");colNames.add("company");}
        if(list.contains("电话号码")) {tableList.add("电话号码");colNames.add("telephone");}

        //将列集合转换为数组
        String[] columnNames = colNames.toArray(new String[colNames.size()]);
        String[] tableHeader = tableList.toArray(new String[tableList.size()]);
        //查询数据
        List<SubArea> subAreas = subAreaMapper.selectList(null);
        //文件名
        String fileName = "数据.xlsx";
        PoiUtil.downLoadExcel(response,columnNames,tableHeader,subAreas,fileName,title);
        return null;
    }
}
