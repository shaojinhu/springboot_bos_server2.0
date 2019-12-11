package com.bos.basic.controller;

import com.bos.base.BaseController;
import com.bos.basic.service.VehicleService;
import com.bos.pojo.basic.Vehicle;
import com.bos.response.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("vehicle")
public class VehicleController extends BaseController {

    @Resource
    private VehicleService vehicleService;

    /**
     * 获得获得车辆管理列表
     * @param map
     * @return
     */
    @PostMapping("getVehicleList")
    public Result getVehicleList(@RequestBody Map<String,String> map){
        return vehicleService.getVehicleList(map);
    }

    /**
     * 修改车辆管理
     * @param vehicle
     * @return
     */
    @PutMapping("updateVehicle")
    public Result updateVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.updateVehicle(vehicle,super.map);
    }

    /**
     * 添加车辆管理
     */
    @PostMapping("addVehicle")
    public Result addVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.addVehicle(vehicle,super.map);
    }


    /**
     * 删除车辆管理
     * @param vehicle
     * @return
     */
    @DeleteMapping("deleteVehicle")
    public Result deleteVehicle(@RequestBody Vehicle vehicle){
        return vehicleService.deleteVehicle(vehicle);
    }
}
