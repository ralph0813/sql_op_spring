package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TbDomain {
    @ApiModelProperty(value = "数据库序号")
    private int dbId;

    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    @ApiModelProperty(value = "数据表名称")
    private String name;

    @ApiModelProperty(value = "数据列列表")
    private List<DtDomain> dts;

    @ApiModelProperty(value = "数据表主键")
    private String primaryKey;

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

    public List<DtDomain> getDts() {
        return dts;
    }

    public void setDts(List<DtDomain> dts) {
        this.dts = dts;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

}
