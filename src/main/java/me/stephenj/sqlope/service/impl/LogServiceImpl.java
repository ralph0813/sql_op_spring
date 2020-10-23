package me.stephenj.sqlope.service.impl;

import cn.hutool.core.date.DateUtil;
import me.stephenj.sqlope.domain.LogParam;
import me.stephenj.sqlope.mbg.mapper.AdminMapper;
import me.stephenj.sqlope.mbg.mapper.LogsMapper;
import me.stephenj.sqlope.mbg.model.Admin;
import me.stephenj.sqlope.mbg.model.LogsExample;
import me.stephenj.sqlope.service.AdminService;
import me.stephenj.sqlope.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogsMapper logsMapper;
    @Autowired
    private AdminService adminService;
    @Override
    public List<String> getLogList(LogParam logParam) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LogsExample logsExample = new LogsExample();
        LogsExample.Criteria criteria = logsExample.createCriteria();
        Optional<Admin> adminOptional = Optional.ofNullable(adminService.getAdminByUsername(logParam.getUsername()));
        adminOptional.ifPresent(admin -> criteria.andUserIdEqualTo(admin.getId()));
        Optional<Date> beginDateOpt = Optional.ofNullable(DateUtil.parse(logParam.getBeginTime()));
        beginDateOpt.ifPresent(criteria::andTimeGreaterThanOrEqualTo);
        Optional<Date> endDateOpt = Optional.ofNullable(DateUtil.parse(logParam.getEndTime()));
        endDateOpt.ifPresent(criteria::andTimeLessThanOrEqualTo);
        return logsMapper.selectByExample(logsExample)
                .stream()
                .map(log -> log.getUsername() + "在" + sdf.format(log.getTime()) + ", " + log.getOperation())
                .collect(Collectors.toList());
    }
}
