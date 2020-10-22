package me.stephenj.sqlope.controller;

import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.common.utils.LogGenerator;
import me.stephenj.sqlope.domain.AdminLoginParam;
import me.stephenj.sqlope.mbg.model.Admin;
import me.stephenj.sqlope.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AdminController.java
 * @Description
 * @author 张润天
 * @Time 2020/10/23 05:25
 * @Field :
 */
@Controller
@Api(tags = "AdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private LogGenerator logGenerator;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Admin> register(@RequestBody Admin admin, BindingResult result) {
        Admin umsAdmin = adminService.register(admin);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        logGenerator.log(admin.getUsername(), "注册");
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody AdminLoginParam adminLoginParam, BindingResult result) {
        String token = adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        logGenerator.log(adminLoginParam.getUsername(), "登录");
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取用户角色（包括+-权限）")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getPermissionList(@PathVariable int adminId) {
        String role = adminService.getRole(adminId);
        return CommonResult.success(role);
    }
}
