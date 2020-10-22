package me.stephenj.sqlope.common.utils;

import me.stephenj.sqlope.mbg.mapper.LogsMapper;
import me.stephenj.sqlope.mbg.model.Admin;
import me.stephenj.sqlope.mbg.model.Logs;
import me.stephenj.sqlope.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * @ClassName LogGenerator.java
 * @Description
 * @author 张润天
 * @Time 2020/10/23 06:21
 * @Field :
 */
@Service
public class LogGenerator {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminService adminService;
    @Autowired
    private LogsMapper logsMapper;

    public void log(HttpServletRequest request, String logs){
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            log(username, logs);
        }
    }

    public void log(String username, String logs){
        Admin admin = adminService.getAdminByUsername(username);
        Logs log = new Logs();
        log.setUserId(admin.getId());
        log.setUsername(admin.getUsername());
        log.setOperation(logs);
        log.setTime(new Date(System.currentTimeMillis()));
        logsMapper.insert(log);
    }
}
