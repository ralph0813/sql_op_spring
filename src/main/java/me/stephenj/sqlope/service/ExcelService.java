package me.stephenj.sqlope.service;

import me.stephenj.sqlope.Exception.ConditionsException;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.TbTemp;

import javax.servlet.ServletOutputStream;
import java.sql.SQLException;

public interface ExcelService {
    String exportExcel(TbTemp tbTemp, ServletOutputStream out) throws DatabaseNotExistException, SQLException, ConditionsException, TableNotExistException;
}
