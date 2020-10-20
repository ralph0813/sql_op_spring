package me.stephenj.sqlope.common.utils;

import com.mysql.jdbc.Connection;
import me.stephenj.sqlope.config.DataSourceHelper;
import me.stephenj.sqlope.domain.ResultCell;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.model.Db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class DBConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHelper.class);
    @Autowired
    private DbMapper dbMapper;
    @Autowired
    private ResultSetLoad resultSetLoad;

    @Value("${datasource.driver}")
    private String driver;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;

    public int execute(String dbName, String sqlStatement) {
        try{
            Class.forName(driver);
            URI uri = new URI(url.replace("jdbc:", ""));
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false", username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
            return 0;
        }

        return 1;
    }

    public int execute(int dbId, String sqlStatement) {
        Db db = dbMapper.selectByPrimaryKey(dbId);
        return execute(db.getName(), sqlStatement);
    }

    public List<List<ResultCell>> query(String dbName, String sqlStatement) {
        ResultSet resultSet = null;
        List<List<ResultCell>> columns = null;
        try{
            Class.forName(driver);
            URI uri = new URI(url.replace("jdbc:", ""));
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false", username, password);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);
            columns = resultSetLoad.load(resultSet);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return columns;
    }
}
