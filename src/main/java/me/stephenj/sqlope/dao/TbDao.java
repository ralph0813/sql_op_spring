package me.stephenj.sqlope.dao;

import me.stephenj.sqlope.domain.TbDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName TbDao.java
 * @Description
 * @author 张润天
 * @Time 2020/10/14 03:24
 * @Field :
 */
public interface TbDao {
    int createTb(TbDomain tbDomain);
}
