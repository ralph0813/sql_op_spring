package me.stephenj.sqlope.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import me.stephenj.sqlope.Exception.ConditionsException;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.utils.SqlCheck;
import me.stephenj.sqlope.domain.RcListParam;
import me.stephenj.sqlope.domain.ResultCell;
import me.stephenj.sqlope.domain.TbTemp;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.Tb;
import me.stephenj.sqlope.mbg.model.TbExample;
import me.stephenj.sqlope.service.ExcelService;
import me.stephenj.sqlope.service.RcService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExcelServiceImpl.java
 * @Description
 * @author 张润天
 * @Time 2020/10/24 03:28
 * @Field :
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private SqlCheck sqlCheck;
    @Autowired
    private RcService rcService;
    @Value("${file.path}")
    private String path;
    @Autowired
    private TbMapper tbMapper;
    @Override
    public String exportExcel(TbTemp tbTemp, ServletOutputStream out) throws DatabaseNotExistException, SQLException, ConditionsException, TableNotExistException {
        sqlCheck.checkExcel(tbTemp);
        ExcelWriter writer = ExcelUtil.getWriter(path + "/" + DateUtil.format(DateUtil.date(), "yyyyMMdd-HHmmss") + "/" + tbTemp.getDbName() + ".xls");
        List<Tb> tbs;
        if (tbTemp.getName() == null) {
            TbExample tbExample = new TbExample();
            tbExample.createCriteria().andDbIdEqualTo(tbTemp.getDbId());
            tbs = tbMapper.selectByExample(tbExample);
            for (int i = 0; i < tbs.size(); i++) {
                writer.setSheet(i);
                tbTemp.setId(tbs.get(i).getId());
                tbTemp.setName(tbs.get(i).getName());
                writeExcel(writer, tbTemp);
            }
        } else {
            writeExcel(writer, tbTemp);
        }
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return tbTemp.getDbName() + ".xls";
    }

    private void writeExcel(ExcelWriter writer, TbTemp tbTemp) throws SQLException, DatabaseNotExistException, ConditionsException, TableNotExistException {
        writer.renameSheet(tbTemp.getName());
        RcListParam rcListParam = new RcListParam();
        BeanUtils.copyProperties(tbTemp, rcListParam);
        List<List<ResultCell>> results = rcService.listRcs(rcListParam);
        ArrayList<Map<String, String>> rows = CollUtil.newArrayList();
        for (List<ResultCell> resultCells : results) {
            Map<String, String> row = new LinkedHashMap<>();
            for (ResultCell resultCell : resultCells) {
                row.put(resultCell.getName(), resultCell.getValue());
            }
            rows.add(row);
        }
        if (rows.size() > 0) {
            if (rows.get(0).size() > 1) {
                writer.merge(rows.get(0).size() - 1, tbTemp.getName());
            } else {
                writer.writeCellValue(0, 0, tbTemp.getName());
            }
            writer.write(rows, true);
        }
    }
}
