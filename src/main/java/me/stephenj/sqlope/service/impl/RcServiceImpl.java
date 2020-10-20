package me.stephenj.sqlope.service.impl;

import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.utils.DBConnector;
import me.stephenj.sqlope.common.utils.SqlCheck;
import me.stephenj.sqlope.common.utils.SqlGenerator;
import me.stephenj.sqlope.domain.ResultCell;
import me.stephenj.sqlope.domain.TbTemp;
import me.stephenj.sqlope.service.RcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
        if (sqlCheck.checkListRc(tbTemp)) {
            String listRcsSql = sqlGenerator.listRc(tbTemp);
            return dbConnector.query(tbTemp.getDbName(), listRcsSql);
        }
        return null;
    }
}
