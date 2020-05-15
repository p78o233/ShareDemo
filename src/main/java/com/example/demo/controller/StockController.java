package com.example.demo.controller;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.callback.R;
import com.example.demo.entity.po.BuySellRecord;
import com.example.demo.entity.po.SellRecord;
import com.example.demo.entity.po.Stock;
import com.example.demo.entity.po.User;
import com.example.demo.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin
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
//    @Scheduled(cron = "0 5 15 * * ? ")
    public void daylyRecord()throws IOException {
        stockService.daylyRecord();
    }

    @RequestMapping(value = "/noticeBuy", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务提醒买入，10点30分，以及14点30分")
//    @Scheduled(cron = "0 30 10,14 * * ? ")
    public void noticeBuy() {
        stockService.noticeBuy();
    }

    @RequestMapping(value = "/noticeBuyClock", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务提醒买入，11点，以及14点")
//    @Scheduled(cron = "0 0 10,14 * * ? ")
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
//    @Scheduled(cron = "0 0,10,20,30,40,50 9,10,11,13,14 * * ? ")
    public void lookShangData() {
        stockService.lookShangData();
    }


    @RequestMapping(value = "/tagBuySell", method = RequestMethod.GET)
    @ResponseBody
//    @ApiOperation("0 0/5 9,10,11,12,13,14,15 * * ?")
//    @Scheduled(cron = "0 0,10,20,30,40,50 9,10,11,13,14 * * ? ")
    public void tagBuySell() {
        stockService.tagBuy();
        stockService.tagSell();
    }

    @RequestMapping(value = "/testForm",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("测试接口")
    public void postForm(){
        stockService.test();
    }



//    页面接口
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("登陆接口")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "User对象，用于登陆",name = "user",paramType = "body")
    })
    public R login(@RequestBody User user){
        return stockService.login(user.getAccount(),user.getPwd());
    }

    @RequestMapping(value = "/getAllStock", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("前端根据用户id获取全部观察数据")
    public R getAllStock(HttpServletRequest request,@RequestParam int userId,@RequestParam(defaultValue = "")String stockNum,@RequestParam (defaultValue = "")String stockName,@RequestParam int pageNum,@RequestParam int pageSize) {
        return new R(true,200,stockService.getAllStock(userId,stockNum,stockName,pageNum,pageSize),"");
    }

    @PostMapping(value = "/ioeStock")
    @ResponseBody
    @ApiOperation("新增或修改观察数据")
    public R ioeStock(@RequestBody Stock stock){
        int result = stockService.ioeStock(stock);
        if(result == 2){
            return new R (false,302,result,"已有相同编号的股票");
        }else if(result == 1){
            return new R (true,200,result,"操作成功");
        }else if(result == 0){
            return new R (false,303,result,"操作失败");
        }
        else
            return null;
    }
    @PostMapping(value = "/deleteStock")
    @ResponseBody
    @ApiOperation("删除观察数据")
    public R deleteStock(@RequestParam int userId,@RequestParam int stockId){
        int result = stockService.deleteStock(userId,stockId);
        if(result == 1){
            return new R (true,200,result,"操作成功");
        }else if(result == 0){
            return new R (false,303,result,"操作失败");
        }
        else
            return null;
    }

    @GetMapping(value = "/getStockNameByStockNum")
    @ResponseBody
    @ApiOperation("根据编号获取名称")
    public R getStockNameByStockNum(@RequestParam String stockNum){
        String result = stockService.getStockNameByStockNum(stockNum);
        return new R (true,200,result,"查询成功");
    }

    @GetMapping(value = "/getStockRecordList")
    @ResponseBody
    @ApiOperation("获取某个股票的走势")
    public R getStockRecordList(@RequestParam String stockNum,@RequestParam int size){
        return new R(true,200,stockService.getStockRecordList(stockNum,size),"查询成功");
    }

}
