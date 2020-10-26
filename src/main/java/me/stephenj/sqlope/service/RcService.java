package me.stephenj.sqlope.service;

import me.stephenj.sqlope.Exception.ConditionsException;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.ParameterLackException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.*;

import java.sql.SQLException;
import java.util.List;

/**
 * @InterfaceName RcService.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 21:14
 * @Field :
 */
public interface RcService {
    List<List<ResultCell>> listRcs(RcListParam rcListParam) throws TableNotExistException, DatabaseNotExistException, SQLException, ConditionsException;

    int addRc(RcAddParam rcAddParam) throws DatabaseNotExistException, TableNotExistException;

    int updateRc(RcUpdateParam rcUpdateParam) throws DatabaseNotExistException, TableNotExistException, ParameterLackException;

    int deleteRc(RcDeleteParam rcDeleteParam) throws DatabaseNotExistException, TableNotExistException, ParameterLackException;
}
