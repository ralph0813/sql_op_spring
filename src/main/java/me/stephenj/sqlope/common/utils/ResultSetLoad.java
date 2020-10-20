package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.domain.ResultCell;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultSetLoad {
    public List<List<ResultCell>> load(ResultSet rs) throws SQLException {
        List<List<ResultCell>> columns = new ArrayList<>();
        // 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            // 遍历每一列
            List<ResultCell> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                String type = metaData.getColumnTypeName(i);
                row.add(new ResultCell(columnName, value, type));
            }
            columns.add(row);
        }
        return columns;
    }
}
