package me.stephenj.sqlope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.stephenj.sqlope.Exception.*;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.common.utils.LogGenerator;
import me.stephenj.sqlope.domain.TbDomain;
import me.stephenj.sqlope.mbg.model.Tb;
import me.stephenj.sqlope.service.TbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName TbController.java
 * @Description
 * @author 张润天
 * @Time 2020/10/13 19:50
 */
@Api(tags = "TbController", description = "数据表管理")
@Controller
@RequestMapping("/tbope")
public class TbController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TbController.class);

    @Autowired
    private TbService tbService;
    @Autowired
    private LogGenerator logGenerator;
    @ApiOperation("获取数据表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Tb>> getTbList(@RequestParam(value = "dbId")
                                            @ApiParam("数据库序号") int dbId,
                                            HttpServletRequest request) {
        logGenerator.log(request, "获取数据表: " + dbId);
        return CommonResult.success(tbService.listTbs(dbId));
    }

    @ApiOperation("创建数据表")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createTb(@RequestBody TbDomain tbDomain, HttpServletRequest request) {
        int count = 0;
        try {
            count = tbService.createTb(tbDomain);
        } catch (TableExistException | DatabaseNotExistException | ParameterLackException e) {
            LOGGER.debug("create table failed:{}", tbDomain.getName());
            return CommonResult.failed(e.getMessage());
        }
        if (count == 0) {
            LOGGER.debug("create table failed:{}", tbDomain.getName());
            return CommonResult.failed("建表失败，语句执行异常");
        } else if (count == 1) {
            logGenerator.log(request, "创建数据表: " + tbDomain.getName());
            LOGGER.debug("create table success:{}", tbDomain.getName());
            return CommonResult.success(tbDomain.getName());
        } else {
            LOGGER.debug("create table failed:{}", tbDomain.getName());
            return CommonResult.failed("建表失败，其他原因");
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @ApiOperation("删除数据表")
    @RequestMapping(value = "/drop", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult dropTb(@RequestParam(value = "tbId")
                               @ApiParam("数据表序号") int tbId,
                               HttpServletRequest request) {
        int count = 0;
        try {
            count = tbService.dropTb(tbId);
        } catch (TableNotExistException | ForeignKeyExistException e) {
            LOGGER.debug("drop table failed:{}", tbId);
            return CommonResult.failed(e.getMessage());
        }
        if (count == 0) {
            LOGGER.debug("drop table failed:{}", tbId);
            return CommonResult.failed("删表失败，语句执行异常");
        } else if (count == 1) {
            logGenerator.log(request, "删除数据表: " + tbId);
            LOGGER.debug("drop table success:{}", tbId);
            return CommonResult.success(tbId);
        } else {
            LOGGER.debug("drop table failed:{}", tbId);
            return CommonResult.failed("删表失败，其他原因");
        }
    }

    @ApiOperation("重命名数据表")
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult renameTb(@RequestParam(value = "tbId")
                                 @ApiParam("数据表序号") int tbId,
                                 @RequestParam(value = "newName")
                                 @ApiParam("数据表新名") String newName,
                                 HttpServletRequest request) {
        int count = 0;
        try {
            count = tbService.renameTb(tbId, newName);
        } catch (TableNotExistException e) {
            LOGGER.debug("rename table failed:{}", tbId);
            return CommonResult.failed(e.getMessage());
        }
        if (count == 0) {
            LOGGER.debug("rename table failed:{}", tbId);
            return CommonResult.failed("重命名表失败，语句执行异常");
        } else if (count == 1) {
            logGenerator.log(request, "重命名数据表: " + tbId);
            LOGGER.debug("rename table success:{}", tbId);
            return CommonResult.success(tbId);
        } else {
            LOGGER.debug("rename table failed:{}", tbId);
            return CommonResult.failed("删表失败，其他原因");
        }
    }
}
