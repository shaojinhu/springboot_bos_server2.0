package com.bos.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class PoiUtil {

    /**
     *
     * 方法描述: 读取Excel数据 <br/>
     * 初始作者: JangSinyu<br/>
     * 创建日期: 2018年12月20日-上午11:10:45<br/>
     * 开始版本: 1.0.0<br/>
     * =================================================<br/>
     * 修改记录：<br/>
     * 修改作者 日期 修改内容<br/>
     * ================================================<br/>
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String[]> readExcel(MultipartFile file) throws IOException {
        return readExcel(file.getInputStream(),file.getOriginalFilename(),1,0);
    }

    /**
     *
     * 方法描述: 读取Excel数据 <br/>
     * 初始作者: JangSinyu<br/>
     * 创建日期: 2018年12月20日-上午11:10:45<br/>
     * 开始版本: 1.0.0<br/>
     * =================================================<br/>
     * 修改记录：<br/>
     * 修改作者 日期 修改内容<br/>
     * ================================================<br/>
     *
     * @param file file文件
     * @param starRow 开始行数(一般第一行是标题)
     * @param starCell 开始单元格
     * @return success or defeat
     */
    public static List<String[]> readExcel(InputStream file, String fileName, int starRow, int starCell) {
        Workbook wb = null;
        //判断文件类型 03或是07
        try {
            if (isExcel2007(fileName)) {
                wb = new XSSFWorkbook(file);
            }
            if (isExcel2003(fileName)) {
                wb = new HSSFWorkbook(file);
            }
        } catch (Exception e) {
            logger.error("解析excel文档流有误。", e);
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            List<String[]> list = new ArrayList<>();
            //选择sheet文件
            Sheet sheet = wb.getSheetAt(0);
            Row rowTable = sheet.getRow(0);
            //获得最大行数
            int lastRowNum = sheet.getLastRowNum();
            //获取第1行的最大cell格数量
            int lastCellNum = rowTable.getLastCellNum();
            //System.out.println("最大row行数是："+lastRowNum);
            for (int i = starRow; i <= lastRowNum; i++) {
                //获取第i行的数据
                Row row = sheet.getRow(i);
                //System.out.println("第"+(i+1)+"行最大cell格数是："+lastCellNum);
                //创建保存数据的String 数组对象
                String[] obj = new String[lastCellNum];
                for (int j = starCell; j < lastCellNum; j++) {
                    Cell cell = null;
                    if(row.getCell(j) == null){//为空则创建一个
                        cell = row.createCell(j);
                    }else{
                        cell = row.getCell(j);
                    }
                    //获取当前cell表格的值,并放入obj
                    obj[j] = getMyCellType(cell);
                }
                list.add(obj);
            }
            wb.close();
            logger.info("读取excel，成功将其转成Object数组");
            return list;
        } catch (Exception e) {
            logger.error("解析excel文档流有误。", e);
            return null;
        }
    }

    /**
     * 获得表头
     * @return
     */
    public static List<String> readExcelGetTableHeader(MultipartFile file,String fileName) throws IOException {
        Workbook wb = null;
        InputStream inputStream = file.getInputStream();
        //判断文件类型 03或是07
        try {
            if (isExcel2007(fileName)) {
                wb = new XSSFWorkbook(inputStream);
            }
            if (isExcel2003(fileName)) {
                wb = new HSSFWorkbook(inputStream);
            }
        } catch (Exception e) {
            logger.error("解析excel文档流有误。", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //解析表头
        Row row = wb.getSheetAt(0).getRow(0);
        //获得最大列数
        int lastCellNum = row.getLastCellNum();
        List<String> tableList = new ArrayList<>();
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            String myCellType = getMyCellType(cell);
            tableList.add(myCellType);
        }
        //返回表头
        return tableList;
    }

    /**
     *
     * 方法描述: Excel导出 <br/>
     * 初始作者: JangSinyu<br/>
     * 创建日期: 2018年12月20日-上午11:10:45<br/>
     * 开始版本: 1.0.0<br/>
     * =================================================<br/>
     * 修改记录：<br/>
     * 修改作者 日期 修改内容<br/>
     * ================================================<br/>
     *
     * @param response  响应HttpServletResponse
     * @param columnNames  到导出的对象属性名
     * @param keyList   要导出的对象自己起的属性对应的名字---（必须与属性名顺序相同）//应该是表头
     * @param objList  要导出的对象集合
     * //@param filePath 导出地址
     * @param fileName  导出文件名称
     * @throws Exception
     */
    public static void downLoadExcel(HttpServletResponse response,String[] columnNames, String[] keyList, List<?> objList, String fileName,String title){
        if (objList.size() > 1000000){
            List<List<?>> lists = fixedGrouping(objList, 1000000);
            for (int i = 0; i < lists.size(); i++) {
                //downLoadExcelManySheet(columnNames,keyList,lists,filePath,fileName,0);
                downLoadExcelManySheet(response,columnNames,keyList,lists,fileName,0);
            }
        }else {
            //downLoadExcelSingleSheet(response,columnNames,keyList,objList,filePath,fileName);
            downLoadExcelSingleSheet(response,columnNames,keyList,objList,fileName,title);
        }
    }

    private static void downLoadExcelManySheet(HttpServletResponse response,String[] columnNames, String[] keyList, List<List<?>> objList, String fileName,int num) {
        Workbook wb = null;
        //判断文件类型 03或是07
        if (isExcel2007(fileName)) {
            wb = new SXSSFWorkbook();
        }
        if (isExcel2003(fileName)) {
            wb = new HSSFWorkbook();
        }
        //创建sheet
        for (int a = 0; a < objList.size(); a++) {
            List<?> objects = objList.get(a);
            Sheet sheet = wb.createSheet("sheet" + a);
            //创建第一行，存放key
            Row row = sheet.createRow(0);
            for (int i = 0; i < keyList.length; i++) {
                row.createCell(i).setCellValue(keyList[i]);
            }
            //先创建object空对象
            Object project = null;
            for (int i = 0; i < objects.size(); i++) {
                Row row1 = sheet.createRow(i+1);
                for (int j = 0; j < columnNames.length; j++) {
                    //创建obj实例
                    project = objects.get(i);
                    row1.createCell(j).setCellValue(getValueByName(columnNames[j],project)+"");
                }
            }
        }
        //将文件响应到电脑
        try {
            //FileOutputStream fileOut = new FileOutputStream(filePath+"\\"+fileName);
            //wb.write(fileOut);

            //创建文件名称
            String fileNewName = URLEncoder.encode(fileName,"UTF-8") ;
            //设置响应头octet-stream
            response.setContentType("application/octet-stream;charset=UTF-8");//告诉浏览区要下载的文件类型的
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileNewName.getBytes(),"ISO-8859-1") );// 告诉浏览器这个文件的名字和类型，attachment：作为附件下载；inline：直接打开
            //进行导出使用输出流
            wb.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downLoadExcelSingleSheet(HttpServletResponse response,String[] columnNames, String[] keyList, List<?> objList, String fileName,String title) {
        Workbook wb = null;
        //判断文件类型 03或是07
        if (isExcel2007(fileName)) {
            wb = new SXSSFWorkbook();
        }
        if (isExcel2003(fileName)) {
            wb = new HSSFWorkbook();
        }
        //创建sheet
        Sheet sheet = wb.createSheet();

        //无标题
        if(StringUtils.isEmpty(title)){
            //创建第一行，存放key
            Row row = sheet.createRow(0);
            for (int i = 0; i < keyList.length; i++) {
                row.createCell(i).setCellValue(keyList[i]);
            }
            //先创建object空对象
            Object project = null;
            for (int i = 0; i < objList.size(); i++) {
                Row row1 = sheet.createRow(i+1);
                for (int j = 0; j < columnNames.length; j++) {
                    //创建obj实例
                    project = objList.get(i);
                    row1.createCell(j).setCellValue(getValueByName(columnNames[j],project)+"");
                }
            }
        }else{
            //创建第一行,放入标题,合并单元格，并进行居中
            Row rowTitle = sheet.createRow(0);//创建第一行
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,keyList.length));//合并单元格
            CellStyle cellStyle = wb.createCellStyle();//创建单元格样式对象
            cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置文字集中展示
            rowTitle.createCell(0).setCellValue(title);//单元格赋值
            rowTitle.getCell(0).setCellStyle(cellStyle);//渲染样式

            //创建第二行，存放key
            Row row = sheet.createRow(1);
            for (int i = 0; i < keyList.length; i++) {
                row.createCell(i).setCellValue(keyList[i]);
            }
            //先创建object空对象
            Object project = null;
            for (int i = 0; i < objList.size(); i++) {
                Row row1 = sheet.createRow(i+2);
                for (int j = 0; j < columnNames.length; j++) {
                    //创建obj实例
                    project = objList.get(i);
                    row1.createCell(j).setCellValue(getValueByName(columnNames[j],project)+"");
                }
            }
        }
        //将文件响应到电脑
        try {
            //FileOutputStream fileOut = new FileOutputStream(filePath+"\\"+fileName);
            //wb.write(fileOut);

            //创建文件名称
            String fileNewName = URLEncoder.encode(fileName,"UTF-8") ;
            //设置响应头octet-stream
            response.setContentType("application/octet-stream;charset=UTF-8");//告诉浏览区要下载的文件类型的
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileNewName.getBytes(),"ISO-8859-1") );// 告诉浏览器这个文件的名字和类型，attachment：作为附件下载；inline：直接打开
            //进行导出使用输出流
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //利用反射获得对象的值
    private static Object getValueByName(String fieldName, Object obj){
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(obj, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获得cell的值
    private static String getMyCellType(Cell cell){
        String value = "";
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: // 数字
                    //如果为时间格式的内容
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        value=sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                        break;
                    } else {
                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_STRING: // 字符串
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN: // Boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                case Cell.CELL_TYPE_FORMULA: // 公式
                    value = cell.getCellFormula() + "";
                    break;
                case Cell.CELL_TYPE_BLANK: // 空值
                    value = "";
                    break;
                case Cell.CELL_TYPE_ERROR: // 故障
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            value = "null";
        }
        return value;
    }


    // 关闭文件流
    private static void close(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
    //关闭流
    private static void close(InputStream file) {
        if (file != null) {
            try {
                file.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    //验证手机号格式是或正确
    private static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean isMatch = false;
        //制定验证条件
        String regex1 = "^[1][3,4,5,7,8][0-9]{9}$";
        String regex2 = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";
        p = Pattern.compile(regex2);
        m = p.matcher(str);
        isMatch = m.matches();
        return isMatch;
    }

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @return
     */
    private static List<List<?>> fixedGrouping(List<?> source, Integer n) {
        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<?>> result = new ArrayList<>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<?> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<?> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }

    private static Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    private static String suffix_xls = ".xls";

    private static String suffix_xlsx = ".xlsx";

    // 判断是否是03的excel:xls
    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // 判断是否是07的excel:xlsx
    private static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    // 根据后缀名判断excel是否合法
    private static boolean isCorrectExcel(String filePath) {
        if (isExcel2003(filePath) || isExcel2003(filePath)) {
            return true;
        } else {
            return false;
        }
    }

}