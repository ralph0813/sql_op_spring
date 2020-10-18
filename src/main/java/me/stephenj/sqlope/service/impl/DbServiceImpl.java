package me.stephenj.sqlope.service.impl;

import com.mysql.jdbc.Connection;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.model.Db;
import me.stephenj.sqlope.mbg.model.DbExample;
import me.stephenj.sqlope.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @ClassName DbServiceImpl.java
 * @Description
 * @author 张润天
 * @Time 2020/10/13 18:55
 */
@Service
public class DbServiceImpl implements DbService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbServiceImpl.class);

    @Value("${datasource.driver}")
    private String driver;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;

    @Autowired
    private DbMapper dbMapper;

    @Override
    public List<Db> ListAllDbs() {
        return dbMapper.selectByExample(new DbExample());
    }

    @Override
    public List<Db> ListDbs() {
        DbExample dbExample = new DbExample();
        dbExample.createCriteria().andStatusEqualTo(1);
        return dbMapper.selectByExample(dbExample);
    }

    @Override
    public int createDb(String name) {
        Db db = new Db();
        db.setName(name);
        DbExample dbExample = new DbExample();
        dbExample.createCriteria().andNameEqualTo(name);
        List<Db> existdbs = dbMapper.selectByExample(dbExample);
        if (existdbs != null && existdbs.size() > 0) {
            LOGGER.debug("service: createDb failed:{}", name);
            return 0;
        }
        try {
            Class.forName(driver);
            URI uri = new URI(url.replace("jdbc:", ""));
            String host = uri.getHost();
            int port = uri.getPort();
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false", username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + name + "` DEFAULT CHARACTER SET = `utf8` COLLATE `utf8_general_ci`;");
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }
        return dbMapper.insertSelective(db);
    }

    @Override
    public int dropDb(String name) {
        DbExample dbExample = new DbExample();
        dbExample.createCriteria().andNameEqualTo(name);
        List<Db> existdbs = dbMapper.selectByExample(dbExample);
        if (existdbs != null && existdbs.size() > 0) {
            Db db = existdbs.get(0);
            db.setStatus(0);
            return dbMapper.updateByPrimaryKey(db);
        }
        return 0;
    }


}
