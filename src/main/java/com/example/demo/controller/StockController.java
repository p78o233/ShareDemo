package com.example.demo.controller;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.callback.R;
import com.example.demo.entity.po.BuySellRecord;
import com.example.demo.entity.po.Stock;
import com.example.demo.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
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
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        stockService.testList();
    }

    @RequestMapping(value = "/daylyRecord", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("每天记录关注的")
    @Scheduled(cron = "0 5 15 * * ? ")
    public void daylyRecord()throws IOException {
        stockService.daylyRecord();
    }

    @RequestMapping(value = "/noticeBuy", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("买")
    @Scheduled(cron = "0 0,30 11,14 * * ? ")
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
    @RequestMapping(value = "/getNowPrice", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询当前价格")
    public R getNowPrice(@RequestBody List<String>stockNums){
        return new R(true,200,stockService.checkNowPrice(stockNums),"");
    }
    @RequestMapping(value = "/getHistoryPrice",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取某个的历史")
    public R getHistoryPrice(@RequestParam String stockNum){
        return new R(true,200,stockService.getHistoryPrice(stockNum),"");
    }
    @RequestMapping(value = "/reminder", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("观察涨跌幅巨大的数据")
    @Scheduled(cron = "0 0,20,40 9,10,11,13,14 * * ? ")
    public void reminder() {
        stockService.reminder();
    }
    @RequestMapping(value = "/lookShangData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("升降银行权限")
    @Scheduled(cron = "0 0,10,20,30,40,50 9,10,11,13,14 * * ? ")
    public void lookShangData() {
        stockService.lookShangData();
    }
    @RequestMapping(value = "/testForm",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("测试接口")
    public void postForm(){
       stockService.test();
    }
}
