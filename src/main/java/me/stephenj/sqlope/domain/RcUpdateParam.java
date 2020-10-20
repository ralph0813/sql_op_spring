package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @ClassName RcUpdateParam.java
 * @Description
 * @author 张润天
 * @Time 2020/10/21 02:51
 * @Field :
 */
public class RcUpdateParam extends TbTemp{

    @ApiModelProperty(value = "条件")
    private List<ResultCell> conditions;

    @ApiModelProperty(value = "数据记录")
    private List<ResultCell> row;

    public List<ResultCell> getConditions() {
        return conditions;
    }

    public void setConditions(List<ResultCell> conditions) {
        this.conditions = conditions;
    }

    public List<ResultCell> getRow() {
        return row;
    }

    public void setRow(List<ResultCell> row) {
        this.row = row;
    }
}
