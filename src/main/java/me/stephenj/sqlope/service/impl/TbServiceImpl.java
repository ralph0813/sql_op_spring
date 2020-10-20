package me.stephenj.sqlope.service.impl;

import me.stephenj.sqlope.Exception.ForeignKeyExistException;
import me.stephenj.sqlope.Exception.TableExistException;
import me.stephenj.sqlope.Exception.TableNotExistException;
import me.stephenj.sqlope.common.utils.DBConnector;
import me.stephenj.sqlope.common.utils.SqlGenerator;
import me.stephenj.sqlope.common.utils.SqlRegistrator;
import me.stephenj.sqlope.domain.TbDomain;
import me.stephenj.sqlope.mbg.mapper.DbMapper;
import me.stephenj.sqlope.mbg.mapper.DtMapper;
import me.stephenj.sqlope.mbg.mapper.TbMapper;
import me.stephenj.sqlope.mbg.model.*;
import me.stephenj.sqlope.service.TbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private DtMapper dtMapper;

    @Autowired
    private DBConnector dbConnector;
    @Autowired
    private SqlGenerator sqlGenerator;
    @Autowired
    private SqlRegistrator sqlRegistrator;

    @Override
    public List<Tb> listTbs(int dbId) {
        TbExample tbExample = new TbExample();
        tbExample.createCriteria().andDbIdEqualTo(dbId);
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

    //删除table需要查看其他data有没有外键指向该table中的data
    @Override
    public int dropTb(int tbId) throws TableNotExistException, ForeignKeyExistException {
        Optional<Tb> tbOptional = Optional.ofNullable(tbMapper.selectByPrimaryKey(tbId));
        if (!tbOptional.isPresent()){
            throw new TableNotExistException("该表不存在");
        }
        DtExample dtExample = new DtExample();
        dtExample.createCriteria().andTbIdEqualTo(tbId);
        List<Dt> dts = dtMapper.selectByExample(dtExample);
        for (Dt dt: dts) {
            int fk = dt.getId();
            DtExample dtExample_fk = new DtExample();
            dtExample_fk.createCriteria().andFkEqualTo(fk);
            List<Dt> fks = dtMapper.selectByExample(dtExample_fk);
            if (!fks.isEmpty()) {
                Dt dt_fk = fks.get(0);
                Tb tb_fk = tbMapper.selectByPrimaryKey(dt_fk.getTbId());
                throw new ForeignKeyExistException(tb_fk.getName(), dt_fk.getName());
            }
        }
        String dropTbSql = sqlGenerator.dropTb(tbOptional.get().getName());
        int state = dbConnector.execute(tbOptional.get().getDbId(), dropTbSql);
        if (state == 1) {
            dtMapper.deleteByExample(dtExample);
            tbMapper.deleteByPrimaryKey(tbId);
            return 1;
        }
        return 0;
    }

    @Override
    public int renameTb(int tbId, String newName) throws TableNotExistException {
        Optional<Tb> tbOptional = Optional.ofNullable(tbMapper.selectByPrimaryKey(tbId));
        if (!tbOptional.isPresent()) {
            throw new TableNotExistException("该表不存在");
        }
        String renameTbSql = sqlGenerator.renameTb(tbOptional.get().getName(), newName);
        int state = dbConnector.execute(tbOptional.get().getDbId(), renameTbSql);
        if (state == 1) {
            tbOptional.get().setName(newName);
            tbMapper.updateByPrimaryKeySelective(tbOptional.get());
            return 1;
        }
        return 0;
    }


}
