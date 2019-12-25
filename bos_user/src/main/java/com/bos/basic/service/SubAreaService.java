package com.bos.basic.service;

import com.bos.execption.MyException;
import com.bos.pojo.basic.SubArea;
import com.bos.response.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface SubAreaService {
    Result getSubAreaList(Map<String, String> map);

    Result addSubArea(SubArea subArea);

    Result updateSubArea(SubArea subArea);

    Result deleteSubArea(SubArea subArea) throws MyException;

    Result releCourierAndSubArea(SubArea subArea) throws MyException;

    Result exportSubArea(HttpServletResponse response, Map<String, Object> map) throws IOException;

    Result UtilExportExecl(HttpServletResponse response,Map<String, Object> map);
}
