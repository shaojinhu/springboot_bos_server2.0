package com.bos.basic.service;

import com.bos.pojo.basic.Area;
import com.bos.response.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface AreaService {
    Result getAreaList(Map<String, String> map);

    Result getAreaAll();

    Result addArea(Area area);

    Result updateArea(Area area);

    Result deleteArea(Area area);

    Result uploadExcel(HttpServletResponse response,MultipartFile file) throws IOException;
}
