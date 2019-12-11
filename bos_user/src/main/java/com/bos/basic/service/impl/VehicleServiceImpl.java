package com.bos.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.basic.mapper.CourierMapper;
import com.bos.basic.mapper.VehicleMapper;
import com.bos.basic.service.VehicleService;
import com.bos.pojo.basic.Courier;
import com.bos.pojo.basic.Vehicle;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class VehicleServiceImpl implements VehicleService {

    @Resource
    private VehicleMapper vehicleMapper;
    @Resource
    private CourierMapper courierMapper;


    /**
     * 获取车辆管理列表(有分页)
     * @param map
     * @return
     */
    @Override
    public Result getVehicleList(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt(map.get("size"));
        IPage<Vehicle> iPage = new Page<>(page,size);
        IPage<Vehicle> iPageResult = vehicleMapper.selectPage(iPage, null);
        PageResult<Vehicle> pageResult = new PageResult<>(iPageResult.getTotal(),iPageResult.getRecords());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 修改车辆管理
     * @param vehicle
     * @param map
     * @return
     */
    @Override
    public Result updateVehicle(Vehicle vehicle, Map<String, String> map) {
        //解析操作人信息
        vehicle.setOperator(map.get("nikename"));
        vehicle.setOperatingCompany(map.get("company"));
        vehicle.setOperatingTime(map.get("operatingTime"));
        int i = vehicleMapper.updateById(vehicle);
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 添加车辆管理
     * @param vehicle
     * @param map
     * @return
     */
    @Override
    public Result addVehicle(Vehicle vehicle, Map<String, String> map) {
        //解析操作人信息
        vehicle.setOperator(map.get("nikename"));
        vehicle.setOperatingCompany(map.get("company"));
        vehicle.setOperatingTime(map.get("operatingTime"));
        int insert = vehicleMapper.insert(vehicle);
        if(insert > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除车辆管理
     * @param vehicle
     * @return
     */
    @Override
    public Result deleteVehicle(Vehicle vehicle) {
        //如有快递员正在使用，则不允许删除
        QueryWrapper<Courier> queryWrapperCourier = new QueryWrapper<>();
        queryWrapperCourier.eq("vehicle_id",vehicle.getId());
        Integer integer = courierMapper.selectCount(queryWrapperCourier);
        if(integer > 0){
            return new Result(ResultCode.VEHICLE_USERING);
        }else {
            int i = vehicleMapper.deleteById(vehicle.getId());
            if (i > 0) {
                return Result.SUCCESS();
            }
            return Result.FAIL();
        }
    }


}
