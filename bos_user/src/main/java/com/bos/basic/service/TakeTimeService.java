package com.bos.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bos.base.BaseController;
import com.bos.pojo.basic.TakeTime;
import com.bos.response.Result;

import java.util.Map;

public interface TakeTimeService {
    Result getTakeTimeList(Map<String, String> map);

    Result addTakeTime(TakeTime takeTime,Map<String,String> map);

    Result updateTakeTime(TakeTime takeTime, Map<String, String> map);


    Result revTakeTime(TakeTime takeTime, Map<String, String> map);

    Result getStatusIsOk();
}
