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
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
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
    @ApiOperation("定时任务提醒买入，10点30分，以及14点30分")
    @Scheduled(cron = "0 30 10,14 * * ? ")
    public void noticeBuy() {
        stockService.noticeBuy();
    }

    @RequestMapping(value = "/noticeBuyClock", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务提醒买入，11点，以及14点")
    @Scheduled(cron = "0 0 10,14 * * ? ")
    public void noticeBuyClock() {
        stockService.noticeBuy();
    }

    @RequestMapping(value = "/noticeSell", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("卖")
    public void noticeSell(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getHeader("userId"));
        stockService.noticeSell(userId);
    }
    @RequestMapping(value = "/insertStock",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("新增观察的数据")
    public R insertStock(@RequestBody List<Stock> stocks){
        return  new R (true,200,stockService.insertStock(stocks),"插入成功");
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
    public void getAllBuyRecord(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getHeader("userId"));
        stockService.getAllBuyRecord(userId);
    }
    @RequestMapping(value = "/getNowPrice/{isWeight}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询当前价格")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "是否只显示权重不为零的数据,0查权重包含0的（即全部），1只查询权重不为0的",paramType = "path")
    })
    public R getNowPrice(HttpServletRequest request,@PathVariable int isWeight,@RequestBody List<String>stockNums){
        int userId = Integer.valueOf(request.getHeader("userId"));
        return new R(true,200,stockService.checkNowPrice(userId,isWeight,stockNums),"");
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
//    @Scheduled(cron = "0 0,20,40 9,10,11,13,14 * * ? ")
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

    @RequestMapping(value = "/getAllStock", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("前端获取全部观察数据")
    public R getAllStock(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getHeader("userId"));
        return new R(true,200,stockService.getAllStock(userId),"");
    }

    @RequestMapping(value = "/tagBuySell", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("0 0/5 9,10,11,12,13,14,15 * * ?")
    @Scheduled(cron = "0 0,10,20,30,40,50 9,10,11,13,14 * * ? ")
    public void tagBuySell() {
        stockService.tagBuy();
        stockService.tagSell();
    }

    @RequestMapping(value = "/testForm",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("测试接口")
    public void postForm(){
        Long begin = new Date().getTime();
        Long sum = 0L;
        for(int i=1;i<=100000000;i++){
            sum += i;
        }
        Long end = new Date().getTime();
        System.out.println(end-begin);
    }

}
