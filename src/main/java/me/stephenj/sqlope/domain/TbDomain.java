package me.stephenj.sqlope.domain;

import java.util.List;

public class TbDomain {
    private int dbId;
    private String dbName;
    private String name;
    private List<DtDomain> dts;
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
