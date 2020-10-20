package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RcDeleteParam extends TbTemp{

    @ApiModelProperty(value = "条件")
    private List<ResultCell> conditions;

    public List<ResultCell> getConditions() {
        return conditions;
    }

    public void setConditions(List<ResultCell> conditions) {
        this.conditions = conditions;
    }

}
