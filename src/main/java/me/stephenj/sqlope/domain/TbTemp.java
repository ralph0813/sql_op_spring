package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

public class TbTemp {

    @ApiModelProperty(value = "数据库序号")
    private int dbId;

    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    @ApiModelProperty(value = "数据表名称")
    private String name;

    @ApiModelProperty(value = "数据表序号")
    private int id;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
