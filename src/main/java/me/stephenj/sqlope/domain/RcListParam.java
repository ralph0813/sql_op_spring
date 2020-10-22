package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RcListParam extends TbTemp{

    @ApiModelProperty(value = "条件")
    private List<ConditionCell> conditions;

    public List<ConditionCell> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionCell> conditions) {
        this.conditions = conditions;
    }

}
