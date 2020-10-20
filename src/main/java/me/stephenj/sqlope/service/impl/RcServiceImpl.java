package me.stephenj.sqlope.service.impl;

import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.utils.DBConnector;
import me.stephenj.sqlope.common.utils.SqlCheck;
import me.stephenj.sqlope.common.utils.SqlGenerator;
import me.stephenj.sqlope.domain.*;
import me.stephenj.sqlope.service.RcService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RcServiceImpl.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 21:14
 * @Field :
 */
@Service
public class RcServiceImpl implements RcService {
    @Autowired
    private SqlCheck sqlCheck;
    @Autowired
    private DBConnector dbConnector;
    @Autowired
    private SqlGenerator sqlGenerator;

    @Override
    public List<List<ResultCell>> listRcs(TbTemp tbTemp) throws TableNotExistException, DatabaseNotExistException {
        if (sqlCheck.checkRc(tbTemp)) {
            String listRcsSql = sqlGenerator.listRc(tbTemp);
            return dbConnector.query(tbTemp.getDbName(), listRcsSql);
        }
        return null;
    }

    @Override
    public int addRc(RcAddParam rcAddParam) throws DatabaseNotExistException, TableNotExistException {
        TbTemp tbTemp = new TbTemp();
        BeanUtils.copyProperties(rcAddParam, tbTemp);
        if (sqlCheck.checkRc(tbTemp)) {
            String addRcSql = sqlGenerator.addRc(rcAddParam);
            return dbConnector.execute(rcAddParam.getDbName(), addRcSql);
        }
        return 0;
    }

    @Override
    public int updateRc(RcUpdateParam rcUpdateParam) throws DatabaseNotExistException, TableNotExistException {
        TbTemp tbTemp = new TbTemp();
        BeanUtils.copyProperties(rcUpdateParam, tbTemp);
        if (sqlCheck.checkRc(tbTemp)) {
            String updateRcSql = sqlGenerator.updateRc(rcUpdateParam);
            return dbConnector.execute(rcUpdateParam.getDbName(), updateRcSql);
        }
        return 0;
    }

    @Override
    public int deleteRc(RcDeleteParam rcDeleteParam) throws DatabaseNotExistException, TableNotExistException {
        TbTemp tbTemp = new TbTemp();
        BeanUtils.copyProperties(rcDeleteParam, tbTemp);
        if (sqlCheck.checkRc(tbTemp)) {
            String deleteRcSql = sqlGenerator.deleteRc(rcDeleteParam);
            return dbConnector.execute(rcDeleteParam.getDbName(), deleteRcSql);
        }
        return 0;
    }
}
