package com.bos.basic.controller;

import com.bos.base.BaseController;
import com.bos.basic.service.ArchiveService;
import com.bos.execption.MyException;
import com.bos.pojo.basic.ParentArchive;
import com.bos.pojo.basic.SubArchive;
import com.bos.response.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 基础档案
 */
@RestController
@RequestMapping("archive")
public class ArchiveController extends BaseController {

    @Resource
    private ArchiveService archiveService;

    /**
     * 获取全部父级档案并分页
     */
    @RequestMapping(value = "getParentList",method = RequestMethod.POST)
    public Result getParentList(@RequestBody Map<String,String> map){
        return archiveService.getList(map);
    }

    /**
     * 添加父级档案
     */
    @RequestMapping(value = "addParentArchive",method = RequestMethod.POST)
    public Result addParentArchive(@RequestBody ParentArchive parentArchive){
        return  archiveService.addParent(parentArchive,super.map);
    }

    /**
     * 修改父档案
     */
    @RequestMapping(value = "updateParentArchive",method = RequestMethod.PUT)
    public Result updateParentArchive(@RequestBody ParentArchive parentArchive){
        return archiveService.updateParent(parentArchive,super.map);
    }

    /**
     * 删除父档案
     */
    @RequestMapping(value = "deleteParentArchive",method = RequestMethod.DELETE)
    public Result deleteParentArchive(@RequestBody ParentArchive parentArchive) throws MyException {
        return archiveService.deleteParent(parentArchive,super.map);
    }

    /**
     * 根据父档案id查询子档案
     */
    @RequestMapping(value = "getSubArchiveByParentId",method = RequestMethod.POST)
    public Result getSubArchiveByParentId(@RequestBody Map<String,String> map){
        return archiveService.getSubByParentId(map);
    }

    /**
     * 修改子档案
     */
    @RequestMapping(value = "updateSubArchive",method = RequestMethod.PUT)
    public Result updateSubArchive(@RequestBody SubArchive subArchive){
        return archiveService.updateSub(subArchive,super.map);
    }

    /**
     * 添加子档案
     */
    @RequestMapping(value = "addSubArchive",method = RequestMethod.POST)
    public Result addArchive(@RequestBody SubArchive subArchive){
        return archiveService.addArchive(subArchive,super.map);
    }

    /**
     * 删除子档案
     */
    @RequestMapping(value = "deleteSubArchive",method = RequestMethod.DELETE)
    public Result deleteSubArchive(@RequestBody SubArchive  subArchive){
        return archiveService.deleteSub(subArchive);
    }
}
