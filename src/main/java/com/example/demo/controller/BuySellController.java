package com.example.demo.controller;/*
 * @author p78o2
 * @date 2020/5/19
 */

import com.example.demo.callback.R;
import com.example.demo.entity.po.BuyRecord;
import com.example.demo.entity.po.SellRecord;
import com.example.demo.service.BuySellService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@Controller
@RequestMapping("/shares/buysell")
@Api(description = "买卖模块")
public class BuySellController {
    @Autowired
    private BuySellService buySellService;

    @ApiOperation("分页获取买入卖出记录")
    @GetMapping("/getBuySellRecordList")
    public R getBuySellRecordList(@RequestParam int userId, String stockNum, Date beginTime,Date endTime,@RequestParam int page,@RequestParam int pageSize){
        return new R (true,200,buySellService.getBuySellRecordList(userId,stockNum,beginTime,endTime,page,pageSize),"查询成功");
    }

    @ApiOperation("新增或修改买入记录")
    @PostMapping("/ioeBuyRecord")
    public R ioeBuyRecord(@RequestBody BuyRecord buyRecord){
        int result = buySellService.ioeBuyRecord(buyRecord);
        switch (result){
            case 3:
                return new R(false,300,null,"股票代码错误或需要新增观察数据");
            case 0:
                return new R(false,303,null,"操作失败");
            case 1:
                return new R (true,200,null,"操作成功");
            default:
                return new R(false,303,null,"操作失败");
        }
    }
    @ApiOperation("删除买入数据")
    @PostMapping("/deleteBuyRecord/{id}")
    public R deleteBuyRecord(@PathVariable("id")int id){
        int result =  buySellService.deleteBuyRecord(id);
        if(result==1){
            return new R (true,200,null,"操作成功");
        }else{
            return new R(false,303,null,"操作失败");
        }
    }
    @ApiOperation("新增或修改卖出记录")
    @PostMapping("/ioeSellRecord")
    public R ioeSellRecord(@RequestBody SellRecord sellRecord){
        int result =  buySellService.ioeSellRecord(sellRecord);
        if(result==1){
            return new R (true,200,null,"操作成功");
        }else{
            return new R(false,303,null,"操作失败");
        }
    }
    @ApiOperation("删除卖出记录")
    @PostMapping("/deleteSellRecord/{id}")
    public R deleteSellRecord(@PathVariable("id")int id){
        int result =  buySellService.deleteSellRecord(id);
        if(result==1){
            return new R (true,200,null,"操作成功");
        }else{
            return new R(false,303,null,"操作失败");
        }
    }
}
