package me.stephenj.sqlope.config;

import com.mysql.jdbc.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DataSourceHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHelper.class);

  @Value("${datasource.driver}")
  private String driver; // com.mysql.cj.jdbc.Driver
  @Value("${datasource.url}")
  private String url; // jdbc:mysql://localhost:3306/pybbs?useSSL=false&characterEncoding=utf8
  @Value("${datasource.username}")
  private String username; // root
  @Value("${datasource.password}")
  private String password; // password

  @PostConstruct
  public void init() {
    try {
      Class.forName(driver);
      URI uri = new URI(url.replace("jdbc:", ""));
      String host = uri.getHost();
      int port = uri.getPort();
      String path = uri.getPath();
      Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false", username, password);
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + path.replace("/", "") + "` DEFAULT CHARACTER SET = `utf8` COLLATE `utf8_general_ci`;");
      statement.close();
      connection.close();
      Connection dbsc = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/dbs?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false", username, password);
      Statement dbss = dbsc.createStatement();
      dbss.executeUpdate("CREATE TABLE IF NOT EXISTS `db` ( `id` INT AUTO_INCREMENT COMMENT '数据库序号(自增)', `name` VARCHAR(255) COMMENT '数据库名', `status` INT DEFAULT 1 COMMENT '数据库状态(0->删除;1->可用)', PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;");
      dbss.executeUpdate("CREATE TABLE IF NOT EXISTS `tb` ( `id` INT AUTO_INCREMENT COMMENT '数据表序号(自增)', `db_id` INT COMMENT '数据库序号', `name` VARCHAR(255) COMMENT '数据表名', `fk` INT DEFAULT -1 COMMENT '外键', PRIMARY KEY (`id`), CONSTRAINT tb_db_id_fk FOREIGN KEY (`db_id`) REFERENCES `db` (`id`)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;");
      dbss.executeUpdate("CREATE TABLE IF NOT EXISTS `dt` ( `id` INT AUTO_INCREMENT COMMENT '数据序号(自增)', `tb_id` INT COMMENT '数据表序号', `name` VARCHAR(255) COMMENT '数据名', `fk` INT DEFAULT -1 COMMENT '外键', `pk` BOOLEAN DEFAULT FALSE COMMENT '是否为主键', PRIMARY KEY (`id`), CONSTRAINT dt_tb_id_fk FOREIGN KEY (`tb_id`) REFERENCES `tb` (`id`)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;");
      dbss.close();
      dbsc.close();
    } catch (ClassNotFoundException | SQLException | URISyntaxException e) {
      LOGGER.error(e.getMessage());
    }
  }
}