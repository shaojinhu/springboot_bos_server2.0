package com.bos.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.basic.mapper.ParentArchiveMapper;
import com.bos.basic.mapper.SubArchiveMapper;
import com.bos.basic.service.ArchiveService;
import com.bos.execption.MyException;
import com.bos.pojo.basic.ParentArchive;
import com.bos.pojo.basic.SubArchive;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class ArchiveServiceImpl implements ArchiveService {


    @Resource
    private ParentArchiveMapper parentArchiveMapper;
    @Resource
    private SubArchiveMapper subArchiveMapper;


    /**
     * 获取列表并分页
     * @param map
     * @return
     */
    @Override
    public Result getList(Map<String, String> map) {
        //解析分页参数
        int page = Integer.parseInt(map.get("page"));
        int size = Integer.parseInt(map.get("size"));
        //获得其他参数
        //创建条件构造器
        QueryWrapper<ParentArchive> queryWrapper = new QueryWrapper<>();
        //TODO 判断进行模糊查询的条件

        //定义分页构造器
        IPage<ParentArchive> iPage = new Page<>(page,size);
        IPage<ParentArchive> iPageResult = parentArchiveMapper.selectPage(iPage, queryWrapper);
        List<ParentArchive> records = iPageResult.getRecords();
        long total = iPageResult.getTotal();
        PageResult<ParentArchive> pageResult = new PageResult<>(total,records);
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 添加父级档案
     * @return
     * @param parentArchive 添加的对象
     * @param map 操作人等信息
     */
    @Override
    public Result addParent(ParentArchive parentArchive,Map<String,String> map) {
        parentArchive.setOperator(map.get("nikename"));
        parentArchive.setOperatingCompany(map.get("company"));
        parentArchive.setOperatingTime(map.get("operatingTime"));
        int insert = parentArchiveMapper.insert(parentArchive);
        if(insert > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 修改父档案
     * @param parentArchive  修改的对象
     * @param map 操作人等信息
     * @return
     */
    @Override
    public Result updateParent(ParentArchive parentArchive, Map<String, String> map) {
        parentArchive.setOperator(map.get("nikename"));
        parentArchive.setOperatingCompany(map.get("company"));
        parentArchive.setOperatingTime(map.get("operatingTime"));
        int i = parentArchiveMapper.updateById(parentArchive);
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除父档案
     * @param parentArchive 要删除的对象
     * @param map 操作人等信息
     * @return
     */
    @Override
    public Result deleteParent(ParentArchive parentArchive, Map<String, String> map) throws MyException {
        String hasChild = parentArchiveMapper.selectById(parentArchive.getId()).getHasChild();
        if(hasChild.equals("0")){
            //有子级表示不可以删除
            throw new MyException(ResultCode.HASCHILD_IS_NOTDELETE);
        }
        //物理删除
        parentArchiveMapper.deleteById(parentArchive.getId());
        return Result.SUCCESS();
    }

    /**
     * 根据父档案id查询子档案并分页
     * @param map 包含父档案id
     * @return
     */
    @Override
    public Result getSubByParentId(Map<String, String> map) {
        String parentid = map.get("parentid");
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        //获得分页构造器
        IPage<SubArchive> iPage = new Page<SubArchive>(page,size);
        //获得条件构造器
        QueryWrapper<SubArchive> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentid",parentid);
        IPage<SubArchive> iPageResult = subArchiveMapper.selectPage(iPage, queryWrapper);
        List<SubArchive> records = iPageResult.getRecords();
        long total = iPageResult.getTotal();
        PageResult<SubArchive> subArchivePageResult = new PageResult<>(total,records);
        return new Result(ResultCode.SUCCESS,subArchivePageResult);
    }

    /**
     * 修改子档案
     * @param subArchive 要修改的对象
     * @param map 操作人信息
     * @return
     */
    @Override
    public Result updateSub(SubArchive subArchive,Map<String, String> map) {
        subArchive.setOperator(map.get("nikename"));
        subArchive.setOperatingCompany(map.get("company"));
        subArchive.setOperatingTime(map.get("operatingTime"));
        int i = subArchiveMapper.updateById(subArchive);
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 添加子档案
     * @param subArchive 要添加的对象
     * @param map 操作人信息
     * @return
     */
    @Override
    public Result addArchive(SubArchive subArchive, Map<String, String> map) {
        subArchive.setOperator(map.get("nikename"));
        subArchive.setOperatingTime(map.get("operatingTime"));
        subArchive.setOperatingCompany(map.get("company"));
        int insert = subArchiveMapper.insert(subArchive);
        if(insert > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除子档案
     * @param subArchive 要删除的对象
     * @return
     */
    @Override
    public Result deleteSub(SubArchive subArchive) {
        int i = subArchiveMapper.deleteById(subArchive.getSubId());
        if(i >0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }


}
