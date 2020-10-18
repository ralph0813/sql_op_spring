package me.stephenj.sqlope.service;

import me.stephenj.sqlope.Exception.ForeignKeyExistException;
import me.stephenj.sqlope.Exception.TableExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.TbDomain;
import me.stephenj.sqlope.mbg.model.Tb;

import java.util.List;

/**
 * @InterfaceName TbService.java
 * @Description
 * @author 张润天
 * @Time 2020/10/13 19:13
 */
public interface TbService {
    List<Tb> listTbs(int dbId);

    int createTb(TbDomain tbDomain) throws TableExistException;

    int dropTb(int tbId) throws TableNotExistException, ForeignKeyExistException;

    int renameTb(int tbId, String newName) throws TableNotExistException;
}
