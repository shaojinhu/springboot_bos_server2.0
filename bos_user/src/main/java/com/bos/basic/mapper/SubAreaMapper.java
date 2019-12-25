package com.bos.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bos.pojo.basic.SubArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface SubAreaMapper extends BaseMapper<SubArea> {

    //动态列查询
    @Options(statementType= StatementType.STATEMENT )
    @Select("<script>" +
            " select " +
            " ${ col }" +
            " from sub_area " +
            "</script>")
    List<SubArea> getSubAreaListByCol(@Param("col")String col);



}
