package com.bos.basic.service;

import com.bos.pojo.basic.Area;
import com.bos.response.Result;

import java.util.Map;

public interface AreaService {
    Result getAreaList(Map<String, String> map);

    Result getAreaAll();

    Result addArea(Area area);

    Result updateArea(Area area);

    Result deleteArea(Area area);
}
