package me.stephenj.sqlope.common.utils;

import com.mysql.jdbc.Connection;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.config.DataSourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class DBConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHelper.class);

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
}
