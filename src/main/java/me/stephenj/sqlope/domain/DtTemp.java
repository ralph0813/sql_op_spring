package me.stephenj.sqlope.domain;

/**
 * @ClassName DtTemp.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 13:01
 * @Field :
 */
public class DtTemp {
    private int dbId;

    private String dbName;

    private int tbId;

    private String tbName;

    private int id;

    private String name;

    private String oldName;

    private String type;

    private boolean autoIncrement;

    private boolean foreignkey;

    private String tgTb;

    private int tgDtId;

    private String tgDt;

    public int getId() {
        return id;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public boolean isForeignkey() {
        return foreignkey;
    }

    public void setForeignkey(boolean foreignkey) {
        this.foreignkey = foreignkey;
    }

    public String getTgTb() {
        return tgTb;
    }

    public void setTgTb(String tgTb) {
        this.tgTb = tgTb;
    }

    public int getTgDtId() {
        return tgDtId;
    }

    public void setTgDtId(int tgDtId) {
        this.tgDtId = tgDtId;
    }

    public String getTgDt() {
        return tgDt;
    }

    public void setTgDt(String tgDt) {
        this.tgDt = tgDt;
    }

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

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public int getTbId() {
        return tbId;
    }

    public void setTbId(int tbId) {
        this.tbId = tbId;
    }


}
