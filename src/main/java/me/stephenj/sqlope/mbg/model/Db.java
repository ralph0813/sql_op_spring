package me.stephenj.sqlope.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Db implements Serializable {
    @ApiModelProperty(value = "数据库序号(自增)")
    private Integer id;

    @ApiModelProperty(value = "数据库名")
    private String name;

    @ApiModelProperty(value = "数据库状态(0->删除;1->可用)")
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}