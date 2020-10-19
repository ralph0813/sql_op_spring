package me.stephenj.sqlope.domain;

public class DtDomain {
    private String name;
    private String type;
    private boolean autoIncrement;
    private boolean foreignkey;
    private String tgTb;
    private String tgDt;

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

    public String getTgDt() {
        return tgDt;
    }

    public void setTgDt(String tgDt) {
        this.tgDt = tgDt;
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
}
