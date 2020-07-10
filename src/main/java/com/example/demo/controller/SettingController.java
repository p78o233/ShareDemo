package com.example.demo.controller;/*
 * @author p78o2
 * @date 2020/7/8
 */

import com.example.demo.callback.R;
import com.example.demo.entity.po.BuySellNotice;
import com.example.demo.entity.po.UserSetting;
import com.example.demo.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/shares/setting")
@Api(description = "基础设置表")
public class SettingController {
    @Autowired
    private SettingService settingService;
    @ApiOperation("获取用户基础配置")
    @GetMapping(value = "/getUserBaseSetting")
    public R getUserBaseSetting(@RequestParam int userId){
        return new R(true,200,settingService.getUserBaseSetting(userId),"查询成功");
    }

    @ApiOperation("修改用户基本配置")
    @PostMapping("/editUserBaseSetting")
    public R editUserBaseSetting(@RequestBody UserSetting userSetting){
        int result = settingService.updateUserBaseSetting(userSetting);
        if(result>0){
            return new R(true,200,result,"操作成功");
        }else{
            return new R(false,500,result,"操作失败");
        }
    }

    @ApiOperation("分页获取买入卖出提示数据")
    @GetMapping("/getBuySellNotice")
    public R getBuySellNotice(@RequestParam int userId,String stockNum,String stockName,@RequestParam int cate,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int pageSize) {
        return new R(true,200,settingService.getBuySellNotice(userId,stockNum,stockName,cate,page,pageSize),"查询成功");
    }

    @ApiOperation("新增修改买入卖出提示数据")
    @PostMapping("/ioeBuySellNotice")
    public R ioeBuySellNotice(@RequestBody BuySellNotice buySellNotice){
        int result = settingService.ioeBuySellNotice(buySellNotice);
        if(result>0){
            return new R (true,200,result,"操作成功");
        }else{
            return new R(false,500,result,"操作失败");
        }
    }

    @ApiOperation("删除买入卖出提示数据")
    @PostMapping("/deleteBuySellNotice/{id}")
    public R deleteBuySellNotice(@PathVariable("id")int id){
        int result = settingService.deleteBuySellNotice(id);
        if(result>0){
            return new R(true,200,result,"操作成功");
        }else{
            return new R(false,500,result,"操作失败");
        }
    }
}
