package com.ldm.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ldm.server.pojo.Employee;
import com.ldm.server.pojo.RespBean;
import com.ldm.server.pojo.Salary;
import com.ldm.server.service.ISalaryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
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
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService iSalaryService;
    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalary() {
        return iSalaryService.list();
    }

    @ApiOperation(value = "添加工资账套")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if (iSalaryService.save(salary)) {
            return RespBean.success("添加工资账套成功");
        }
        return RespBean.error("添加工资账套失败");
    }
    @ApiOperation(value = "删除工资账套")
    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id) {
        if (iSalaryService.removeById(id)) {
            return RespBean.success("删除工资账套成功");
        }
        return RespBean.success("删除工资账套失败");
    }
    @ApiOperation(value = "更新工资账套")
    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary) {
        if (iSalaryService.updateById(salary)) {
            return RespBean.success("更新工资账套成功");
        }
        return RespBean.success("更新工资账套成功");
    }

    @ApiOperation(value = "导出工资账套信息")
    @GetMapping(value = "/export_salary",produces = "application/octet-stream")
    public void exportSalary(HttpServletResponse httpServletResponse) {
        List<Salary> salaryList = iSalaryService.getSalary(null);
        ExportParams exportParams = new ExportParams("工资账套表","工资账套表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Salary.class,salaryList);
        ServletOutputStream outputStream = null;
        try {
            // 流形式
            httpServletResponse.setHeader("content-type","application/octet-stream");
            // 防止中文乱码
            httpServletResponse.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("工资账套表.xls","UTF-8"));
            outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @ApiOperation(value = "导入工资账套")
    @ApiImplicitParams({@ApiImplicitParam(name="file",value = "上传文件",dataType = "MultipartFile")})
    @PostMapping("/import_salary")
    public RespBean importSalary(MultipartFile file) {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);
        try {
            List<Salary> list = ExcelImportUtil.importExcel(file.getInputStream(),Salary.class,importParams);
            if (iSalaryService.saveBatch(list)) {
                return RespBean.success("导入工资账套成功");
            }
            return RespBean.error("导入工资账套失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入工资账套失败");
    }
}
