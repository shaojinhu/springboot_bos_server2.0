package com.bos.basic.controller;

import com.bos.base.BaseController;
import com.bos.basic.service.FixedAreaService;
import com.bos.pojo.basic.FixedArea;
import com.bos.response.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("fixedarea")
public class FixedController extends BaseController {

    @Resource
    private FixedAreaService fixedAreaService;

    /**
     * 或取定区FixedArea列表
     * @param map
     * @return
     */
    @PostMapping("getFixedAreaList")
    public Result getFixedAreaList(@RequestBody Map<String,String> map){
        return fixedAreaService.getFixedAreaList(map);
    }

    /**
     * 添加定区
     * @param fixedArea
     * @return
     */
    @PostMapping("addFixedArea")
    public Result addFixedArea(@RequestBody FixedArea fixedArea){
        return fixedAreaService.addFixedArea(fixedArea,super.map);
    }

    /**
     * 修改定区
     * @param fixedArea
     * @return
     */
    @PutMapping("updateFixedArea")
    public Result updateFixedArea(@RequestBody FixedArea fixedArea){
        return fixedAreaService.updateFixedArea(fixedArea,super.map);
    }

    /**
     * 删除定区
     * @param fixedArea
     * @return
     */
    @DeleteMapping("deleteFixedArea")
    public Result deleteFixedArea(@RequestBody FixedArea fixedArea){
        return fixedAreaService.deleteFixedArea(fixedArea);
    }

    /**
     * 获取全部的定区
     * @return
     */
    @GetMapping("getFixedArea")
    public  Result getFixedArea(){
        return fixedAreaService.getFixeArea();
    }

    /**
     * 根据区域id获得区域下的定区
     * @param areaID
     * @return
     */
    @GetMapping("getFixedAreaByAreaId/{areaid}")
    public Result getFixedAreaByAreaId(@PathVariable("areaid") String areaID){
        return fixedAreaService.getFixedAreaByAreaId(areaID);
    }
}
