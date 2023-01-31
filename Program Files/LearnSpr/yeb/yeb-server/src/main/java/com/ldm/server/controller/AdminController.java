package com.ldm.server.controller;


import com.ldm.server.pojo.Admin;
import com.ldm.server.pojo.RespBean;
import com.ldm.server.pojo.Role;
import com.ldm.server.service.IAdminService;
import com.ldm.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ldm
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private IRoleService iRoleService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keywords){
        return iAdminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping("/")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        if (iAdminService.updateById(admin)) {
            return RespBean.success("更新操作员成功");
        }
        return RespBean.error("更新操作员失败");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(Integer id) {
        if (iAdminService.removeById(id)) {
            return RespBean.success("删除操作员成功");
        }
        return RespBean.error("删除操作员失败");
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return iRoleService.list();
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/roles")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids){
        return iAdminService.updateAdminRole(adminId,rids);
    }
}
