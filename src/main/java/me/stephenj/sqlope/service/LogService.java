package me.stephenj.sqlope.service;

import me.stephenj.sqlope.domain.LogParam;

import java.util.List;

/**
 * @InterfaceName LogService.java
 * @Description
 * @author 张润天
 * @Time 2020/10/23 22:59
 * @Field :
 */
public interface LogService {
    List<String> getLogList(LogParam logParam);
}
