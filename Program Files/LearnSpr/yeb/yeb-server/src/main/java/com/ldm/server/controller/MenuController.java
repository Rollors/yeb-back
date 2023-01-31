package com.ldm.server.controller;


import com.ldm.server.pojo.Menu;
import com.ldm.server.service.IAdminService;
import com.ldm.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/system/config")
public class MenuController {
    @Autowired
    private IMenuService iMenuService;

    @ApiOperation(value="通过用户id查询用户列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId(){
        return iMenuService.getMenuByAdminId();
    }
}
