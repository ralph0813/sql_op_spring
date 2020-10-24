package me.stephenj.sqlope.service;

import me.stephenj.sqlope.Exception.ConditionsException;
import me.stephenj.sqlope.Exception.DatabaseNotExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.domain.TbTemp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public interface ExcelService {
    String exportExcel(TbTemp tbTemp, ServletOutputStream out) throws DatabaseNotExistException, SQLException, ConditionsException, TableNotExistException;

    int importExcel(TbTemp tbTemp, MultipartFile file) throws DatabaseNotExistException, TableNotExistException, IOException;
}
