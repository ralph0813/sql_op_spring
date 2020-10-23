package me.stephenj.sqlope.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @ClassName LogParam.java
 * @Description
 * @author 张润天
 * @Time 2020/10/23 23:11
 * @Field :
 */
public class LogParam {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "开始时间(格式：yyyy-MM-dd HH:mm:ss)")
    private String beginTime;

    @ApiModelProperty(value = "结束时间(格式：yyyy-MM-dd HH:mm:ss)")
    private String endTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
