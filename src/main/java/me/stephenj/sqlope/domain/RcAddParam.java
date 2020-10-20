package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RcAddParam extends TbTemp{

    @ApiModelProperty(value = "数据记录")
    private List<ResultCell> row;

    public List<ResultCell> getRow() {
        return row;
    }

    public void setRow(List<ResultCell> row) {
        this.row = row;
    }
}
