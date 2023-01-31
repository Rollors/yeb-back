package com.ldm.server.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.ldm.server.pojo.Joblevel;
import com.ldm.server.pojo.RespBean;
import com.ldm.server.service.IJoblevelService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Arrays;
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
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService iJoblevelService;

    @ApiModelProperty(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJobLevel(){
        return iJoblevelService.list();
    }

    @ApiModelProperty(value = "添加职称")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (iJoblevelService.save(joblevel)) {
            return RespBean.success("添加职称成功");
        }
        return RespBean.error("添加职称失败");
    }

    @ApiModelProperty(value = "更新职称")
    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody Joblevel joblevel) {
        if (iJoblevelService.updateById(joblevel)) {
            return RespBean.success("更新职称成功");
        }
        return RespBean.error("更新职称失败");
    }

    @ApiModelProperty(value = "删除职称")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(Integer id) {
        if (iJoblevelService.removeById(id)) {
            return RespBean.success("删除职称成功");
        }
        return RespBean.error("删除职称失败");
    }

    @ApiModelProperty(value = "批量删除职称")
    @DeleteMapping("/")
    public RespBean deleteJobLevelById(Integer[] ids) {
        if (iJoblevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("批量删除职称成功");
        }
        return RespBean.error("批量删除职称失败");
    }
}
