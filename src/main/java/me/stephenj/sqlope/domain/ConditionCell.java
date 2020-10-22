package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

public class ConditionCell {
    @ApiModelProperty(value = "列名")
    private String name;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "符号（包括 =, !=, >, <, <=, >=）")
    private String symbol;

    @ApiModelProperty(value = "逻辑关系（0->AND, 1->OR")
    private int logic;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }
}
