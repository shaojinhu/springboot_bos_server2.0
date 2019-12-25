package com.bos.basic.controller;

import com.bos.basic.service.SubAreaService;
import com.bos.execption.MyException;
import com.bos.pojo.basic.SubArea;
import com.bos.response.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("subArea")
public class SubAreaController {

    @Resource
    private SubAreaService subAreaService;

    /**
     * 获得分区列表有分页
     * @param map
     * @return
     */
    @PostMapping("getSubAreaList")
    public Result getSubAreaList(@RequestBody Map<String,String> map){
        return subAreaService.getSubAreaList(map);
    }

    /**
     * 添加分区
     * @param subArea
     * @return
     */
    @PostMapping("addSubArea")
    public Result addSubArea(@RequestBody SubArea subArea){
        return subAreaService.addSubArea(subArea);
    }

    /**
     * 修改分区
     * @param subArea
     * @return
     */
    @PutMapping("updateSubArea")
    public Result updateSubArea(@RequestBody SubArea subArea){
        return subAreaService.updateSubArea(subArea);
    }

    /**
     * 删除分区
     * @param subArea
     * @return
     * @throws MyException
     */
    @DeleteMapping("deleteSubArea")
    public Result deleteSubArea(@RequestBody SubArea subArea) throws MyException {
        return subAreaService.deleteSubArea(subArea);
    }

    /**
     * 关联快递员表
     * @param subArea
     * @return
     */
    @PutMapping("releCourierAndSubArea")
    public Result releCourierAndSubArea(@RequestBody SubArea subArea) throws MyException {
        return subAreaService.releCourierAndSubArea(subArea);
    }

    /**
     * 导出Execl
     * @param response
     * @param map
     * @return
     * @throws IOException
     */
    @PostMapping("exportSubArea")
    public Result exportSubArea(HttpServletResponse response, @RequestBody Map<String,Object> map) throws IOException {
//        return subAreaService.exportSubArea(response,map);
        return subAreaService.UtilExportExecl(response,map);
    }

}
