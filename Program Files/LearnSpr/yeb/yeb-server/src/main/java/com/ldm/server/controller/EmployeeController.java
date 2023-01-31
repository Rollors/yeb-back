package com.ldm.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ldm.server.pojo.*;
import com.ldm.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.Na;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService iEmployeeService;
    @Autowired
    private IPoliticsStatusService iPoliticsStatusService;
    @Autowired
    private IJoblevelService iJoblevelService;
    @Autowired
    private INationService iNationService;
    @Autowired
    private IDepartmentService iDepartmentService;
    @Autowired
    private IPositionService iPositionService;

    @ApiOperation(value = "获取所有员工(分页)")
    @GetMapping("/")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size, Employee employee, LocalDate[] beginDateScope){
        return iEmployeeService.getEmployeeByPage(current,size,employee,beginDateScope);
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/poloticsstatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return iPoliticsStatusService.list();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJobLevels() {
        return iJoblevelService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return iNationService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return iPositionService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return iDepartmentService.getAllDepartments();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID() {
        return iEmployeeService.maxWorkID();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        return iEmployeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (iEmployeeService.updateById(employee)) {
            return RespBean.success("更新员工信息成功");
        }
        return RespBean.error("更新员工信息失败");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {
        if (iEmployeeService.removeById(id)) {
            return RespBean.success("删除员工成功");
        }
        return RespBean.error("删除员工失败");
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> employees = iEmployeeService.getEmployee(null);
        ExportParams exportParams = new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,Employee.class,employees);
        ServletOutputStream outputStream = null;
        try {
            // 流形式
            response.setHeader("content-type","application/octet-stream");
            // 防止中文乱码
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @ApiOperation(value = "导入员工数据")
    @ApiImplicitParams({@ApiImplicitParam(name="file",value = "上传文件",dataType = "MultipartFile")})
    @PostMapping("/import")
    public RespBean importEmployee(MultipartFile file) {
        ImportParams importParams = new ImportParams();
        // 如果存在标题行，则去掉标题行，从第一行开始，第0行不要
        importParams.setTitleRows(1);
        List<Nation> nationList = iNationService.list();
        List<PoliticsStatus> politicsStatuses = iPoliticsStatusService.list();
        List<Position> positions = iPositionService.list();
        List<Department> departments = iDepartmentService.list();
        List<Joblevel> joblevels = iJoblevelService.list();
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(),Employee.class,importParams);
            list.forEach(employee -> {
                System.out.println("索引"+nationList.indexOf(new Nation(employee.getNation().getName()))+":"+politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))+":"+positions.indexOf(new Position(employee.getPosition().getName()))+":"+departments.indexOf(new Department(employee.getDepartment().getName()))+":"+joblevels.indexOf(new Joblevel(employee.getJoblevel().getName())));
                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setPoliticId(politicsStatuses.get(politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                employee.setPosId(positions.get(positions.indexOf(new Position(employee.getPosition().getName()))).getId());
                employee.setDepartmentId(departments.get(departments.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setJobLevelId(joblevels.get(joblevels.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
            });
            if (iEmployeeService.saveBatch(list)) {
                return RespBean.success("导入成功");
            }
            return RespBean.error("导入失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败");
    }
}
