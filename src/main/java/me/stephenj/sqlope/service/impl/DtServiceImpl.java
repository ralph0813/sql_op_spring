package me.stephenj.sqlope.service.impl;

import me.stephenj.sqlope.Exception.DataNotCompleteException;
import me.stephenj.sqlope.Exception.DataNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.utils.DBConnector;
import me.stephenj.sqlope.common.utils.SqlCheck;
import me.stephenj.sqlope.common.utils.SqlGenerator;
import me.stephenj.sqlope.common.utils.SqlRegistrator;
import me.stephenj.sqlope.controller.DbController;
import me.stephenj.sqlope.domain.DtTemp;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.Dt;
import me.stephenj.sqlope.mbg.model.DtExample;
import me.stephenj.sqlope.service.DtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DtServiceImpl.java
 * @Description
 * @author 张润天
 * @Time 2020/10/18 23:36
 * @Field :
 */
@Service
public class DtServiceImpl implements DtService {
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private TbMapper tbMapper;
    @Autowired
    private DtMapper dtMapper;
    @Autowired
    private SqlCheck sqlCheck;
    @Autowired
    private SqlGenerator sqlGenerator;
    @Autowired
    private DBConnector dbConnector;
    @Autowired
    private SqlRegistrator sqlRegistrator;

    @Override
    public List<Dt> listDts(int tbId) throws TableNotExistException {
        DtExample dtExample = new DtExample();
        dtExample.createCriteria().andTbidEqualTo(tbId);
        List<Dt> dts = dtMapper.selectByExample(dtExample);
        if (dts.isEmpty()) {
            throw new TableNotExistException();
        }
        return dts;
    }

    @Override
    public int createDt(DtTemp dtTemp) throws TableNotExistException, DataNotCompleteException, DataNotExistException {
        if (sqlCheck.checkDtTemp(dtTemp)) {
            String createDtSql = sqlGenerator.createDt(dtTemp);
            dbConnector.execute(dtTemp.getDbName(), createDtSql);
            if (dtTemp.isForeignkey()) {
                String createFkSql = sqlGenerator.createFk(dtTemp);
                dbConnector.execute(dtTemp.getDbName(), createFkSql);
            }
            return sqlRegistrator.createDt(dtTemp);
        }
        return 0;
    }
}
