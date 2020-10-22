package me.stephenj.sqlope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.stephenj.sqlope.Exception.ConditionsException;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.domain.*;
import me.stephenj.sqlope.service.RcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName RcController.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 21:12
 * @Field :
 */
@Api(tags = "RcController", description = "数据记录管理")
@Controller
@RequestMapping("/rcope")
public class RcController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TbController.class);

    @Autowired
    private RcService rcService;

    @ApiOperation("获取数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<List<ResultCell>>> getRcList(@RequestBody RcListParam rcListParam) {
        List<List<ResultCell>> columns = null;
        try {
             columns = rcService.listRcs(rcListParam);
        } catch (DatabaseNotExistException | TableNotExistException | SQLException | ConditionsException e) {
            LOGGER.debug("list record failed:{}", rcListParam);
            CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(columns);
    }

    @ApiOperation("添加数据")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addRc(@RequestBody RcAddParam rcAddParam) {
        int count = 0;
        try {
            count = rcService.addRc(rcAddParam);
        } catch (DatabaseNotExistException | TableNotExistException e) {
            LOGGER.debug("add data failed:{}", rcAddParam);
            CommonResult.failed(e.getMessage());
        }
        if (count == 1) {
            LOGGER.debug("add record success:{}", rcAddParam);
            return CommonResult.success(rcAddParam);
        } else {
            LOGGER.debug("add record failed:{}", rcAddParam);
            return CommonResult.failed("添加记录失败，其他原因");
        }
    }

    @ApiOperation("更新数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRc(@RequestBody RcUpdateParam rcUpdateParam) {
        int count = 0;
        try {
            count = rcService.updateRc(rcUpdateParam);
        } catch (DatabaseNotExistException | TableNotExistException e) {
            LOGGER.debug("update record failed:{}", rcUpdateParam);
            CommonResult.failed(e.getMessage());
        }
        if (count == 1) {
            LOGGER.debug("update record success:{}", rcUpdateParam);
            return CommonResult.success(rcUpdateParam);
        } else {
            LOGGER.debug("update record failed:{}", rcUpdateParam);
            return CommonResult.failed("更新记录失败，其他原因");
        }
    }

    @ApiOperation("删除数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteRc(@RequestBody RcDeleteParam rcDeleteParam) {
        int count = 0;
        try {
            count = rcService.deleteRc(rcDeleteParam);
        } catch (TableNotExistException | DatabaseNotExistException e) {
            LOGGER.debug("delete record failed:{}", rcDeleteParam);
            CommonResult.failed(e.getMessage());
        }
        if (count == 1) {
            LOGGER.debug("delete record success:{}", rcDeleteParam);
            return CommonResult.success(rcDeleteParam);
        } else {
            LOGGER.debug("delete record failed:{}", rcDeleteParam);
            return CommonResult.failed("删除记录失败，其他原因");
        }
    }

}
