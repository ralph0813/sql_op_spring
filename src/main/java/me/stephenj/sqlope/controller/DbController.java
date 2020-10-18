package me.stephenj.sqlope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.mbg.model.Db;
import me.stephenj.sqlope.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName DbController.java
 * @Description 数据库操作接口
 * @author 张润天
 * @Time 2020/10/13 14:52
 */
@Api(tags = "DbController", description = "数据库管理")
@Controller
@RequestMapping("/dbope")
public class DbController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbController.class);

    @Autowired
    private DbService dbService;

    @ApiOperation("获取所有的数据库")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Db>> getAllDbList() {
        return CommonResult.success(dbService.ListAllDbs());
    }

    @ApiOperation("获取所有可用的数据库")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Db>> getDbList() {
        return CommonResult.success(dbService.ListDbs());
    }

    @ApiOperation("新建数据库")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createDB(@RequestParam(value = "dbName")
                                 @ApiParam("数据库名") String dbName) {
        int count = dbService.createDb(dbName);
        if (count == 0) {
            LOGGER.debug("createDB failed:{}", dbName);
            return CommonResult.failed("已存在同名数据库");
        } else {
            LOGGER.debug("createDB success:{}", dbName);
            return CommonResult.success(dbName);
        }
    }

    @ApiOperation("删除数据库")
    @RequestMapping(value = "/drop", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult dropDB(@RequestParam(value = "dbName")
                               @ApiParam("数据库名") String dbName) {
        int count = dbService.dropDb(dbName);
        if (count == 0) {
            LOGGER.debug("dropDB failed:{}", dbName);
            return CommonResult.failed("不存在该数据库");
        } else {
            LOGGER.debug("dropDB success:{}", dbName);
            return CommonResult.success(dbName);
        }
    }

}
