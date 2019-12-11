package com.bos.basic.service;

import com.bos.execption.MyException;
import com.bos.pojo.basic.ParentArchive;
import com.bos.pojo.basic.SubArchive;
import com.bos.response.Result;

import java.util.Map;

public interface ArchiveService {

    public Result getList(Map<String,String> map);

    Result addParent(ParentArchive parentArchive,Map<String,String> map);

    Result updateParent(ParentArchive parentArchive, Map<String, String> map);

    Result deleteParent(ParentArchive parentArchive, Map<String, String> map) throws MyException;

    Result getSubByParentId(Map<String, String> map);

    Result updateSub(SubArchive subArchive,Map<String, String> map);

    Result addArchive(SubArchive subArchive, Map<String, String> map);

    Result deleteSub(SubArchive subArchive);
}
