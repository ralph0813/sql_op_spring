package me.stephenj.sqlope.domain;

import java.util.List;

public class TbDomain {
    private String dbName;
    private String name;
    private List<DtDomain> dts;
    private String primaryKey;
    private List<FkDomain> fks;

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

    public List<FkDomain> getFks() {
        return fks;
    }

    public void setFks(List<FkDomain> fks) {
        this.fks = fks;
    }
}
