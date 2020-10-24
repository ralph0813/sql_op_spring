package me.stephenj.sqlope.service;

import me.stephenj.sqlope.mbg.model.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminService.java
 * @Description
 * @author 张润天
 * @Time 2020/10/23 05:25
 * @Field :
 */
public interface AdminService {
    /**
     * 根据用户名获取后台管理员
     */
    Admin getAdminByUsername(String username);

    Admin update(String password, HttpServletRequest request);

    /**
     * 注册功能
     */
    Admin register(Admin admin);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    String getRole(int adminId);
}