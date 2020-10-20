package me.stephenj.sqlope.service;

import me.stephenj.sqlope.Exception.DataNotCompleteException;
import me.stephenj.sqlope.Exception.DataNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.DtParam;
import me.stephenj.sqlope.domain.DtTemp;
import me.stephenj.sqlope.mbg.model.Dt;

import java.util.List;

/**
 * @InterfaceName DtService.java
 * @Description
 * @author 张润天
 * @Time 2020/10/18 23:35
 * @Field :
 */
public interface DtService {
    List<Dt> listDts(int tbId) throws TableNotExistException;

    int createDt(DtTemp dtTemp) throws TableNotExistException, DataNotCompleteException, DataNotExistException;
}
