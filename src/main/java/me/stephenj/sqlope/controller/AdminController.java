package me.stephenj.sqlope.controller;

import me.stephenj.sqlope.Exception.UserExistException;
import me.stephenj.sqlope.common.api.CommonResult;
import me.stephenj.sqlope.common.utils.LogGenerator;
import me.stephenj.sqlope.domain.AdminInfo;
import me.stephenj.sqlope.domain.AdminLoginParam;
import me.stephenj.sqlope.mbg.model.Admin;
import me.stephenj.sqlope.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @PreAuthorize("hasAuthority('admin')")
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Admin> register(@RequestBody Admin admin, BindingResult result) {
        try {
            Admin AdminInfo = adminService.register(admin);
            logGenerator.log(admin.getUsername(), "注册");
            return CommonResult.success(AdminInfo);
        } catch (UserExistException e) {
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation(value = "更新密码")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Admin> register(@RequestParam String password, HttpServletRequest request) {
        Admin admin = adminService.update(password, request);
        if (admin == null) {
            return CommonResult.failed("更新秘密失败");
        }
        logGenerator.log(admin.getUsername(), "注册");
        return CommonResult.success(admin);
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

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info(HttpServletRequest request) {
        Optional<AdminInfo> adminInfo = Optional.ofNullable(adminService.info(request));
        if (adminInfo.isPresent()) {
            return CommonResult.success(adminInfo.get());
        } else {
            return CommonResult.failed("用户未登录或Token已过期");
        }
    }

    @ApiOperation("获取用户角色")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getPermissionList(@PathVariable int adminId) {
        String role = adminService.getRole(adminId);
        return CommonResult.success(role);
    }
}
