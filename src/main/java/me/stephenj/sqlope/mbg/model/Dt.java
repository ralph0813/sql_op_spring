package me.stephenj.sqlope.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Dt implements Serializable {
    @ApiModelProperty(value = "数据序号(自增)")
    private Integer id;

    @ApiModelProperty(value = "数据表序号")
    private Integer tbid;

    @ApiModelProperty(value = "数据名")
    private String name;

    @ApiModelProperty(value = "外键")
    private Integer fk;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTbid() {
        return tbid;
    }

    public void setTbid(Integer tbid) {
        this.tbid = tbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFk() {
        return fk;
    }

    public void setFk(Integer fk) {
        this.fk = fk;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tbid=").append(tbid);
        sb.append(", name=").append(name);
        sb.append(", fk=").append(fk);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}