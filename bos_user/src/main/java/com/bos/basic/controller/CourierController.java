package com.bos.basic.controller;

import com.bos.basic.service.CourierService;
import com.bos.execption.MyException;
import com.bos.pojo.basic.Courier;
import com.bos.response.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("courier")
public class CourierController {

    @Resource
    private CourierService courierService;

    /**
     * 获取Courier列表
     * @param map
     * @return
     */
    @PostMapping("getCourierList")
    public Result getCourierList(@RequestBody Map<String,String> map){
        return courierService.getCourierList(map);
    }

    /**
     * 添加Courier
     */
    @PostMapping("addCourier")
    public Result addCourier(@RequestBody Courier courier){
        return courierService.addCourier(courier);
    }

    /**
     * 修改Courier
     */
    @PutMapping("updateCourier")
    public Result updateCourier(@RequestBody Courier courier){
        return  courierService.updateCourier(courier);
    }

    /**
     * 启禁Courier
     * @param courier
     * @return
     */
    @PutMapping("revCourier")
    public Result revCourier(@RequestBody Courier courier){
        return courierService.revCourier(courier);
    }

    /**
     * 批量修改Courier
     */
    @PutMapping("batchCourier")
    public Result batchCourier(@RequestBody Map<String,Object> map) throws MyException {
        return courierService.batchCourier(map);
    }

    /**
     *获取全部快递员id
     */
    @PostMapping("getCourierID")
    public Result getCourierID(){
        return courierService.getCourierID();
    }
}
