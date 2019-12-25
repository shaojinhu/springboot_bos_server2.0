package com.bos.basic.controller;

import com.bos.basic.service.AreaService;
import com.bos.pojo.basic.Area;
import com.bos.response.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("area")
public class AreaController {

    @Resource
    private AreaService areaService;

    /**
     * 获得Area区域列表，有分页
     * @param map
     * @return
     */
    @PostMapping("getAreaList")
    public Result getAreaList(@RequestBody Map<String,String> map){
        return areaService.getAreaList(map);
    }

    /**
     * 获得全部的Area，无分页
     */
    @GetMapping("getAreaAll")
    public Result getAreaAll(){
        return areaService.getAreaAll();
    }

    /**
     * 添加Area
     */
    @PostMapping("addArea")
    public Result addArea(@RequestBody Area area){
        return areaService.addArea(area);
    }

    /**
     * 修改Area
     * @param area
     * @return
     */
    @PutMapping("updateArea")
    public Result updateArea(@RequestBody Area area){
        return areaService.updateArea(area);
    }

    /**
     * 删除Area
     * @param area
     * @return
     */
    @DeleteMapping("deleteArea")
    public Result deleteArea(@RequestBody Area area){
        return areaService.deleteArea(area);
    }

    /**
     * 区域（Area）的导入
     */
    @PostMapping("upload")
    public Result uploadExcel(HttpServletResponse response, @RequestParam("file")MultipartFile file) throws IOException {
        return areaService.uploadExcel(response,file);
    }
}
