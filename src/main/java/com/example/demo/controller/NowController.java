package com.example.demo.controller;/*
 * @author p78o2
 * @date 2020/7/13
 */

import com.example.demo.callback.R;
import com.example.demo.service.NowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/shares/getNow")
@Api(description = "获取当前数据")
public class NowController {
    @Autowired
    private NowService nowService;

    @PostMapping("/checkNowPrice/{userId}/{weight}")
    @ResponseBody
    @ApiOperation("获取当前价格,权重不为零的数据,0查权重包含0的（即全部），1只查询权重不为0的")
    public R checkNowPrice(@PathVariable("userId")int userId, @PathVariable("weight")int weight, @RequestBody List<String> stockNums){
        return new R(true,200,nowService.checkNowPrice(userId,weight,stockNums),"查询成功");
    }
}
