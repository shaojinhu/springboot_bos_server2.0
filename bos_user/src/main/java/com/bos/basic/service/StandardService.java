package com.bos.basic.service;

import com.bos.pojo.basic.Standard;
import com.bos.response.Result;

import java.util.Map;

public interface StandardService {
    Result getStandardList(Map<String, Object> map);

    Result addStandard(Standard standard, Map<String, String> map);

    Result updateStandard(Standard standard, Map<String, String> map);

    Result deleteStandard(Standard standard);

    Result getStandard();
}
