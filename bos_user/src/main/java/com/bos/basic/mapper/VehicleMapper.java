package com.bos.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bos.pojo.basic.Vehicle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VehicleMapper extends BaseMapper<Vehicle> {
}
