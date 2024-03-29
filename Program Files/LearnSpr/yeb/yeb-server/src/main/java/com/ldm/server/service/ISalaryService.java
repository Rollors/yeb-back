package com.ldm.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ldm.server.pojo.Salary;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ldm
 * @since 2022-11-22
 */
public interface ISalaryService extends IService<Salary> {

    List<Salary> getSalary(Integer id);
}
