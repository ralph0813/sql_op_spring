package me.stephenj.sqlope.service;

import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.ResultCell;
import me.stephenj.sqlope.domain.TbTemp;

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
    List<List<ResultCell>> listRcs(TbTemp tbTemp) throws TableNotExistException, DatabaseNotExistException, SQLException;

}
