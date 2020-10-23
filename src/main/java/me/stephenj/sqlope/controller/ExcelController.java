package me.stephenj.sqlope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.stephenj.sqlope.Exception.ConditionsException;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.domain.TbTemp;
import me.stephenj.sqlope.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName ExcelController.java
 * @Description
 * @author 张润天
 * @Time 2020/10/24 01:59
 * @Field :
 */
@Api(tags = "ExcelController", description = "导入导出管理")
@Controller
@RequestMapping("/excel")
public class ExcelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TbController.class);
    @Autowired
    private ExcelService excelService;
    @ApiOperation("导出Excel")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult exportExcel(@RequestBody TbTemp tbTemp, HttpServletResponse response) {
        String fileName = null;
        try {
            fileName = excelService.exportExcel(tbTemp, response.getOutputStream());
        } catch (TableNotExistException | IOException | DatabaseNotExistException | SQLException | ConditionsException e) {
            LOGGER.error("导出Excel表格失败" + e.getMessage());
            return CommonResult.failed(e.getMessage());
        }
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        return CommonResult.success("导出Excel表格成功");
    }
}
