package me.stephenj.sqlope.service.impl;

import me.stephenj.sqlope.Exception.*;
import me.stephenj.sqlope.common.utils.DBConnector;
import me.stephenj.sqlope.common.utils.SqlCheck;
import me.stephenj.sqlope.common.utils.SqlGenerator;
import me.stephenj.sqlope.common.utils.SqlRegistrator;
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
        dtExample.createCriteria().andTbIdEqualTo(tbId);
        List<Dt> dts = dtMapper.selectByExample(dtExample);
        if (dts.isEmpty()) {
            throw new TableNotExistException();
        }
        return dts;
    }

    @Override
    public int createDt(DtTemp dtTemp) throws TableNotExistException, DataNotCompleteException, DataNotExistException, DataExistException {
        if (sqlCheck.checkCreateDt(dtTemp)) {
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

    @Override
    public int dropDt(DtTemp dtTemp) throws DataNotExistException, ForeignKeyExistException, TableEmptyException {
        if (sqlCheck.checkDropDt(dtTemp)) {
            String dropDtSql = sqlGenerator.dropDt(dtTemp);
            dbConnector.execute(dtTemp.getDbName(), dropDtSql);
            return sqlRegistrator.dropDt(dtTemp);
        }
        return 0;
    }

    @Override
    public int modifyDt(DtTemp dtTemp) throws DataNotExistException, DataNotCompleteException, TableNotExistException, DataExistException {
        if (sqlCheck.checkModifyDt(dtTemp)) {
            String modifyDtSql = sqlGenerator.modifyDt(dtTemp);
            dbConnector.execute(dtTemp.getDbName(), modifyDtSql);
            if (dtTemp.isForeignkey()) {
                String dropFkSql = sqlGenerator.deleteFk(dtTemp);
                dbConnector.execute(dtTemp.getDbName(), dropFkSql);
                String createFkSql = sqlGenerator.createFk(dtTemp);
                dbConnector.execute(dtTemp.getDbName(), createFkSql);
            }
            return sqlRegistrator.modifyDt(dtTemp);
        }
        return 0;
    }

}
