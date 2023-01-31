package com.ldm.server.controller;


import com.ldm.server.pojo.Admin;
import com.ldm.server.pojo.AdminLoginParam;
import com.ldm.server.pojo.RespBean;
import com.ldm.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 */
@RestController
public class LoginController {

    @Autowired
    private IAdminService iAdminService;
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return iAdminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    /**
     * principal：当前用户
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null==principal){
            return null;
        }
        // Principal用于获取全局SecurityContextHolder里面设置的数据
        String username = principal.getName();
        Admin admin = iAdminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(iAdminService.getRoles(admin.getId()));
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");
    }

}
