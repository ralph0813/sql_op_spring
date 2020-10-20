package me.stephenj.sqlope.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Dt implements Serializable {
    @ApiModelProperty(value = "数据序号(自增)")
    private Integer id;

    @ApiModelProperty(value = "数据表序号")
    private Integer tbId;

    @ApiModelProperty(value = "数据名")
    private String name;

    @ApiModelProperty(value = "外键")
    private Integer fk;

    @ApiModelProperty(value = "是否为主键")
    private Boolean pk;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTbId() {
        return tbId;
    }

    public void setTbId(Integer tbId) {
        this.tbId = tbId;
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

    public Boolean getPk() {
        return pk;
    }

    public void setPk(Boolean pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tbId=").append(tbId);
        sb.append(", name=").append(name);
        sb.append(", fk=").append(fk);
        sb.append(", pk=").append(pk);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}