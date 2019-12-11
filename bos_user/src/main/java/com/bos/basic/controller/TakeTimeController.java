package com.bos.basic.controller;

import com.bos.base.BaseController;
import com.bos.basic.service.TakeTimeService;
import com.bos.pojo.basic.TakeTime;
import com.bos.response.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("taketime")
public class TakeTimeController extends BaseController {

    @Resource
    private TakeTimeService takeTimeService;

    /**
     * 获得收派时间管理
     * 获得TakeTime的列表，有分页
     */
    @PostMapping("getTakeTimeList")
    public Result getTakeTimeList(@RequestBody Map<String,String> map){
        return takeTimeService.getTakeTimeList(map);
    }

    /**
     * 添加收派时间管理
     * @param takeTime
     * @return
     */
    @PostMapping("addTakeTime")
    public Result addTakeTime(@RequestBody TakeTime takeTime){
        return takeTimeService.addTakeTime(takeTime,super.map);
    }

    /**
     * 修改收派时间管理
     * @param takeTime
     * @return
     */
    @PutMapping("updateTakeTime")
    public Result updateTakeTime(@RequestBody TakeTime takeTime){
        return takeTimeService.updateTakeTime(takeTime,super.map);
    }

    /**
     * 收派时间管理启禁
     * @param takeTime
     * @return
     */
    @PutMapping("revTakeTime")
    public  Result revTakeTime(@RequestBody TakeTime takeTime){
        return takeTimeService.revTakeTime(takeTime,super.map);
    }

    /**
     * 获得全部启用的收派时间
     * @return
     */
    @GetMapping("getStatusIsOk")
    public Result getStatusIsOk(){
        return takeTimeService.getStatusIsOk();
    }
}
