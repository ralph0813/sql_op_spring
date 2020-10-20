package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName DtParam.java
 * @Description
 * @author 张润天
 * @Time 2020/10/20 11:08
 * @Field :
 */
public class DtParam {

    @ApiModelProperty(value = "数据表序号")
    private int tbId;

    @ApiModelProperty(value = "数据列")
    private DtDomain dtDomain;

    public int getTbId() {
        return tbId;
    }

    public void setTbId(int tbId) {
        this.tbId = tbId;
    }

    public DtDomain getDtDomain() {
        return dtDomain;
    }

    public void setDtDomain(DtDomain dtDomain) {
        this.dtDomain = dtDomain;
    }
}
