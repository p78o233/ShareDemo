package com.example.demo.controller;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.po.BuySellRecord;
import com.example.demo.entity.po.Stock;
import com.example.demo.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/shares/stock")
@Api(description = "后台模块")
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("测试链接")
    public void test() {
        stockService.testList();
    }

    @RequestMapping(value = "/daylyRecord", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("每天记录关注的")
    public void daylyRecord()throws IOException {
        stockService.daylyRecord();
    }

    @RequestMapping(value = "/noticeBuy", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("买")
    public void noticeBuy() {
        stockService.noticeBuy();
    }
    @RequestMapping(value = "/noticeSell", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("卖")
    public void noticeSell() {
        stockService.noticeSell();
    }
    @RequestMapping(value = "/insertStock",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("新增观察的数据")
    public void insertStock(@RequestBody List<Stock> stocks){
        stockService.insertStock(stocks);
    }
    @RequestMapping(value = "/insertBuyRecord",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("新增购买记录")
    public void insertBuyRecord(@RequestBody BuySellRecord buySellRecord){
        stockService.insertBuyRecord(buySellRecord);
    }
    @RequestMapping(value = "/updateSellRecord",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("记录卖情况")
    public void updateSellRecord(@RequestBody BuySellRecord buySellRecord){
        stockService.updateSellRecord(buySellRecord);
    }
    @RequestMapping(value = "/getAllBuyRecord", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取当前已经买到的")
    public void getAllBuyRecord() {
        stockService.getAllBuyRecord();
    }
}
