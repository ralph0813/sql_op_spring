package me.stephenj.sqlope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.domain.ResultCell;
import me.stephenj.sqlope.domain.TbParam;
import me.stephenj.sqlope.domain.TbTemp;
import me.stephenj.sqlope.service.RcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    public CommonResult<List<List<ResultCell>>> getTbList(@RequestBody TbParam tbParam) {
        TbTemp tbTemp = new TbTemp();
        BeanUtils.copyProperties(tbParam, tbTemp);
        List<List<ResultCell>> columns = null;
        try {
             columns = rcService.listRcs(tbTemp);
        } catch (DatabaseNotExistException | TableNotExistException | SQLException e) {
            CommonResult.failed(e.getMessage());
        }
        return CommonResult.success(columns);
    }
}
