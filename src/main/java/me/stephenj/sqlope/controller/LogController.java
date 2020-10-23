package me.stephenj.sqlope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.domain.LogParam;
import me.stephenj.sqlope.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName LogController.java
 * @Description
 * @author 张润天
 * @Time 2020/10/23 22:56
 * @Field :
 */
@Api(tags = "LogController", description = "日志管理")
@Controller
@RequestMapping("/log")
public class LogController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TbController.class);
    @Autowired
    private LogService logService;

    @ApiOperation("获取日志")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<String>> getLogList(@RequestBody LogParam logParam) {
        return CommonResult.success(logService.getLogList(logParam));
    }
}
