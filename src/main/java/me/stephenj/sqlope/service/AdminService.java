package me.stephenj.sqlope.service;

import me.stephenj.sqlope.mbg.model.Admin;

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

    /**
     * 注册功能
     */
    Admin register(Admin umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    String getRole(int adminId);
}