package com.bos.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bos.execption.MyException;
import com.bos.pojo.basic.Courier;
import com.bos.response.Result;

import java.util.Map;

public interface CourierService {
    Result getCourierList(Map<String, String> map);

    Result addCourier(Courier courier);

    Result updateCourier(Courier courier);

    Result revCourier(Courier courier);

    Result batchCourier(Map<String, Object> map) throws MyException;
}
