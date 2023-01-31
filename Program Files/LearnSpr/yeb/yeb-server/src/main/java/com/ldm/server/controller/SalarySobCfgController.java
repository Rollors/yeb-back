package com.ldm.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ldm.server.pojo.Employee;
import com.ldm.server.pojo.RespBean;
import com.ldm.server.pojo.RespPageBean;
import com.ldm.server.pojo.Salary;
import com.ldm.server.service.IEmployeeService;
import com.ldm.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工账套
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService iSalaryService;
    @Autowired
    private IEmployeeService iEmployeeService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return iSalaryService.list();
    }

    @ApiOperation(value = "获取所有员工账套")
    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {
        return iEmployeeService.getEmployeeWithSalary(currentPage,size);
    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid,Integer sid) {
        if (iEmployeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",eid))) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
