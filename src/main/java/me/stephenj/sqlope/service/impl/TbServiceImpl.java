package me.stephenj.sqlope.service.impl;

import me.stephenj.sqlope.Exception.TableExistException;
import me.stephenj.sqlope.common.utils.DBConnector;
import me.stephenj.sqlope.common.utils.SqlGenerator;
import me.stephenj.sqlope.common.utils.SqlRegistrator;
import me.stephenj.sqlope.domain.TbDomain;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.Db;
import me.stephenj.sqlope.mbg.model.DbExample;
import me.stephenj.sqlope.mbg.model.Tb;
import me.stephenj.sqlope.mbg.model.TbExample;
import me.stephenj.sqlope.service.TbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TbServiceImpl.java
 * @Description
 * @author 张润天
 * @Time 2020/10/14 04:36
 * @Field :
 */
@Service
public class TbServiceImpl implements TbService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TbServiceImpl.class);
    @Value("${datasource.driver}")
    private String driver;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;

    @Autowired
    private TbMapper tbMapper;

    @Autowired
    private DbMapper dbMapper;

    @Autowired
    private DBConnector dbConnector;
    @Autowired
    private SqlGenerator sqlGenerator;
    @Autowired
    private SqlRegistrator sqlRegistrator;

    @Override
    public List<Tb> listTbs(int dbId) {
        TbExample tbExample = new TbExample();
        tbExample.createCriteria().andDbidEqualTo(dbId);
        return tbMapper.selectByExample(tbExample);
    }

    @Override
    public int createTb(TbDomain tbDomain) throws TableExistException {
        DbExample dbExample = new DbExample();
        dbExample.createCriteria().andNameEqualTo(tbDomain.getDbName());
        List<Db> dbs = dbMapper.selectByExample(dbExample);
        if (dbs.size() <= 0) {
            return 0;
        }
        try {
            String createTbSql = sqlGenerator.createTb(tbDomain);
            if (createTbSql.equals("-2")) {
                return -2;
            }
            dbConnector.execute(tbDomain.getDbName(), createTbSql);

        } catch (NullPointerException e){
            return -1;
        }
        sqlRegistrator.createTb(tbDomain);

//        try {
//            URI uri = new URI(url.replace("jdbc:", ""));
//            String host = uri.getHost();
//            int port = uri.getPort();
////            DataSource dataSource = DataSourceBuilder.create().driverClassName(driver).url("jdbc:mysql://" + host + ":" + port + "/" + tbDomain.getDbName() + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false").username(username).password(password).build();
////            TransactionFactory transactionFactory = new JdbcTransactionFactory();
////            Environment environment = new Environment.Builder("work").dataSource(dataSource).transactionFactory(transactionFactory).build();
////            Configuration configuration = new Configuration(environment);
//////            configuration.addMapper(TbDao.class);
////            configuration.addMappers("me.stephenj.sqlope.dao", TbDao.class);
//////            configuration.addLoadedResource("me/stephenj/sqlope/dao/TbDao.xml");
////            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
////            SqlSession sqlSession = sqlSessionFactory.openSession();
////            TbDao tbDao = sqlSession.getMapper(TbDao.class);
////            tbDao.createTb(tbDomain);
//////            sqlSession.update("TbDao.", tbDomain);
////            sqlSession.commit();
////            sqlSession.clearCache();
////            sqlSession.close();
//        } catch (URISyntaxException e) {
//            LOGGER.error(e.getMessage());
//        }
        return 1;
    }
}
