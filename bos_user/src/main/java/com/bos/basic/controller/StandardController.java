package com.bos.basic.controller;

import com.bos.base.BaseController;
import com.bos.basic.service.StandardService;
import com.bos.pojo.basic.Standard;
import com.bos.response.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("standard")
public class StandardController extends BaseController {

    @Resource
    private StandardService standardService;

    /**
     * 获得取派标准列表，有分页
     * @param map
     * @return
     */
    @RequiresPermissions("STANDARD_API_LIST")
    @PostMapping("getStandardList")
    public Result getStandardList(@RequestBody Map<String,Object> map){
        return standardService.getStandardList(map);
    }

    /**
     * 获取全部的取派标准，提供其他模块使用
     * @return
     */
    @GetMapping("getStandard")
    public Result getStandard(){
        return standardService.getStandard();
    }

    /**
     * 添加取派标准
     * @param standard
     * @return
     */
    @PostMapping("addStandard")
    public Result addStandard(@RequestBody Standard standard){
        return standardService.addStandard(standard,super.map);
    }

    /**
     * 修改Standard取派标准
     * @param standard
     * @return
     */
    @PutMapping("updateStandard")
    public Result updateStandard(@RequestBody Standard standard){
        return standardService.updateStandard(standard,super.map);
    }

    /**
     * 删除Standard
     * @param standard
     * @return
     */
    @DeleteMapping("deleteStandard")
    public Result deleteStandard(@RequestBody Standard standard){
        return standardService.deleteStandard(standard);
    }
}
