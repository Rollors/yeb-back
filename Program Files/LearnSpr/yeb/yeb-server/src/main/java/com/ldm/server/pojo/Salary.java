package com.ldm.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ldm
 * @since 2022-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_salary")
@ApiModel(value="Salary对象", description="")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基本工资")
    @Excel(name = "基本工资")
    private Integer basicSalary;

    @ApiModelProperty(value = "奖金")
    @Excel(name = "奖金")
    private Integer bonus;

    @ApiModelProperty(value = "午餐补助")
    @Excel(name = "午餐补助")
    private Integer lunchSalary;

    @ApiModelProperty(value = "交通补助")
    @Excel(name = "交通补助")
    private Integer trafficSalary;

    @ApiModelProperty(value = "应发工资")
    @Excel(name = "应发工资")
    private Integer allSalary;

    @ApiModelProperty(value = "养老金基数")
    @Excel(name = "养老金基数")
    private Integer pensionBase;

    @ApiModelProperty(value = "养老金比率")
    @Excel(name = "养老金比率")
    private Float pensionPer;

    @ApiModelProperty(value = "启用时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "启用时间",width = 20,format = "yyyy-MM-dd")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "医疗基数")
    @Excel(name = "医疗基数")
    private Integer medicalBase;

    @ApiModelProperty(value = "医疗保险比率")
    @Excel(name = "医疗保险比率")
    private Float medicalPer;

    @ApiModelProperty(value = "公积金基数")
    @Excel(name = "公积金基数")
    private Integer accumulationFundBase;

    @ApiModelProperty(value = "公积金比率")
    @Excel(name = "公积金比率")
    private Float accumulationFundPer;

    @ApiModelProperty(value = "名称")
    @Excel(name = "名称")
    private String name;


}
