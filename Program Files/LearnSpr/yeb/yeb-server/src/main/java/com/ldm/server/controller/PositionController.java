package com.ldm.server.controller;


import com.ldm.server.pojo.Position;
import com.ldm.server.pojo.RespBean;
import com.ldm.server.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService iPositionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return iPositionService.list();
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (iPositionService.save(position)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if (iPositionService.updateById(position)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        if (iPositionService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public RespBean deletePositionById(Integer[] ids){
        if (iPositionService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
