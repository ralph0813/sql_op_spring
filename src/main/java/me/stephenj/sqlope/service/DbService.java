package me.stephenj.sqlope.service;

import me.stephenj.sqlope.mbg.model.Db;

import java.util.List;

/**
 * @InterfaceName DbService.java
 * @Description
 * @author 张润天
 * @Time 2020/10/13 15:04
 * @Field :
 */
public interface DbService {
    List<Db> ListAllDbs();

    List<Db> ListDbs();

    int createDb(String name);

    int dropDb(String name);
}
