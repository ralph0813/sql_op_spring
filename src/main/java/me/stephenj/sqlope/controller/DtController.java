package me.stephenj.sqlope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.stephenj.sqlope.Exception.DataNotCompleteException;
import me.stephenj.sqlope.Exception.DataNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.domain.DtParam;
import me.stephenj.sqlope.domain.DtTemp;
import me.stephenj.sqlope.mbg.model.Dt;
import me.stephenj.sqlope.service.DtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName DtController.java
 * @Description
 * @author 张润天
 * @Time 2020/10/19 01:01
 * @Field :
 */
@Api(tags = "DtController", description = "数据列管理")
@Controller
@RequestMapping("/dtope")
public class DtController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DtController.class);

    @Autowired
    private DtService dtService;

    @ApiOperation("获取数据列")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Dt>> getDtList(@RequestParam(value = "tbId")
                                            @ApiParam("数据表序号") int tbId) {
        List<Dt> dts;
        try {
            dts = dtService.listDts(tbId);
        } catch (TableNotExistException e) {
            LOGGER.debug("list data failed:{}", tbId);
            return CommonResult.failed("该表不存在");
        }
        return CommonResult.success(dts);
    }

    @ApiOperation("创建数据列")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createDt(@RequestBody DtParam dtParam) {
        int count = 0;
        DtTemp dtTemp = new DtTemp();
        BeanUtils.copyProperties(dtTemp, dtParam);
        BeanUtils.copyProperties(dtTemp, dtParam.getDtDomain());
        try {
            count = dtService.createDt(dtTemp);
        } catch (TableNotExistException | DataNotCompleteException | DataNotExistException e) {
            LOGGER.debug("create data failed:{}", dtParam.getTbId());
            return CommonResult.failed(e.getMessage());
        }
        if (count == 1) {
            LOGGER.debug("create data success:{}", dtTemp.getName());
            return CommonResult.success(dtParam);
        } else {
            LOGGER.debug("create data failed:{}", dtTemp.getName());
            return CommonResult.failed("创建数据列失败，其他原因");
        }
    }
}
