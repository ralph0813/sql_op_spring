package me.stephenj.sqlope.mbg.mapper;

import java.util.List;
import me.stephenj.sqlope.mbg.model.Db;
import me.stephenj.sqlope.mbg.model.DbExample;
import org.apache.ibatis.annotations.Param;

public interface DbMapper {
    long countByExample(DbExample example);

    int deleteByExample(DbExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Db record);

    int insertSelective(Db record);

    List<Db> selectByExample(DbExample example);

    Db selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Db record, @Param("example") DbExample example);

    int updateByExample(@Param("record") Db record, @Param("example") DbExample example);

    int updateByPrimaryKeySelective(Db record);

    int updateByPrimaryKey(Db record);
}