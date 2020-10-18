package me.stephenj.sqlope.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfig.java
 * @Description
 * @author 张润天
 * @Time 2020/10/13 15:39
 * @Field :
 */
@Configuration
@MapperScan({"me.stephenj.sqlope.mbg.mapper", "me.stephenj.sqlope.dao"})
public class MyBatisConfig {
}
