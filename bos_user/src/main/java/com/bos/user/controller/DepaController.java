package com.bos.user.controller;

import com.bos.pojo.user.Depa;
import com.bos.response.Result;
import com.bos.user.service.DepaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("depa")
public class DepaController {

    @Resource
    private DepaService depaService;

    /**
     * 获取全部的部门Depa
     * @return
     */
    @RequestMapping("getDepa")
    public Result getDepa(){
        return depaService.getDepa();
    }

    /**
     * 添加部门
     * @param depa
     * @return
     */
    @PostMapping("addDepa")
    public Result addDepa(@RequestBody Depa depa){
        return depaService.addDepa(depa);
    }

    /**
     * 修改部门
     */
    @PutMapping("updateDepa")
    public Result updateDepa(@RequestBody Depa depa){
        return depaService.updateDepa(depa);
    }

    @DeleteMapping("deleteDepa")
    public Result deleteDepa(@RequestBody Depa depa){
        return depaService.deleteDepa(depa);
    }
}
