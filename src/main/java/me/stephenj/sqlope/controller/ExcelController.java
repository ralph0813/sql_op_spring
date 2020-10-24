package me.stephenj.sqlope.controller;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.stephenj.sqlope.Exception.ConditionsException;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.common.utils.LogGenerator;
import me.stephenj.sqlope.domain.TbTemp;
import me.stephenj.sqlope.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private LogGenerator logGenerator;
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

    @ApiOperation("导入Excel")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult importExcel(@RequestParam(value = "file")
                                    @ApiParam("Excel文件") MultipartFile file,
                                    HttpServletRequest request) {
        int count = 0;
        try {
            count = excelService.importExcel(file);
        } catch (TableNotExistException | DatabaseNotExistException | IOException e) {
            LOGGER.error("import Excel failed:{}", e.getMessage());
            return CommonResult.failed(e.getMessage());
        }
        if (count == 1) {
            logGenerator.log(request, "给数据库" + FileUtil.mainName(file.getOriginalFilename()) + "导入Excel数据");
            LOGGER.info("import excel success");
            return CommonResult.success(FileUtil.mainName(file.getOriginalFilename()));
        } else {
            LOGGER.error("import excel failed: 执行sql语句失败");
            return CommonResult.failed("import Excel failed: 执行sql语句失败");
        }
    }
}
