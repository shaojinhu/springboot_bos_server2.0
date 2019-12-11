package com.bos.basic.service;

import com.bos.pojo.basic.Vehicle;
import com.bos.response.Result;

import java.util.Map;

public interface VehicleService {
    Result getVehicleList(Map<String, String> map);

    Result updateVehicle(Vehicle vehicle, Map<String, String> map);

    Result addVehicle(Vehicle vehicle, Map<String, String> map);

    Result deleteVehicle(Vehicle vehicle);
}
