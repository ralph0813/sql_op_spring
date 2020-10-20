package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

public class ResultCell {

    @ApiModelProperty(value = "列名")
    private String name;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "数据类型")
    private String type;

    public ResultCell() {
    }

    public ResultCell(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
