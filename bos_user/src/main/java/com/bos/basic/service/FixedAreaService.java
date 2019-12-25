package com.bos.basic.service;

import com.bos.pojo.basic.FixedArea;
import com.bos.response.Result;

import java.util.Map;

public interface FixedAreaService {
    Result getFixedAreaList(Map<String, String> map);

    Result addFixedArea(FixedArea fixedArea, Map<String, String> map);

    Result updateFixedArea(FixedArea fixedArea, Map<String, String> map);

    Result deleteFixedArea(FixedArea fixedArea);

    Result getFixeArea();

    Result getFixedAreaByAreaId(String areaID);
}
