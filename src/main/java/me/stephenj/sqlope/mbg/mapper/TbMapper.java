package me.stephenj.sqlope.mbg.mapper;

import java.util.List;
import me.stephenj.sqlope.mbg.model.Tb;
import me.stephenj.sqlope.mbg.model.TbExample;
import org.apache.ibatis.annotations.Param;

public interface TbMapper {
    long countByExample(TbExample example);

    int deleteByExample(TbExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tb record);

    int insertSelective(Tb record);

    List<Tb> selectByExample(TbExample example);

    Tb selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tb record, @Param("example") TbExample example);

    int updateByExample(@Param("record") Tb record, @Param("example") TbExample example);

    int updateByPrimaryKeySelective(Tb record);

    int updateByPrimaryKey(Tb record);
}