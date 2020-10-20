package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

public class DtDomain {

    @ApiModelProperty(value = "数据列名称")
    private String name;

    @ApiModelProperty(value = "数据列类型")
    private String type;

    @ApiModelProperty(value = "true->自增;false->不自增")
    private boolean autoIncrement;

    @ApiModelProperty(value = "true->有外键;false->无外键")
    private boolean foreignkey;

    @ApiModelProperty(value = "外键指向的数据表")
    private String tgTb;

    @ApiModelProperty(value = "外键指向的数据列")
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
