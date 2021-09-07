package com.example.demo.controller;/*
 * @author p78o2
 * @date 2020/7/10
 */

import com.example.demo.service.TimmerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping("/shares/timmer")
@Api(description = "定时器模块")
public class TimmerController {
    @Autowired
    private TimmerService timmerService;


    @RequestMapping(value = "/noticeBuyFirst", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务提醒低价，10点30分，以及14点30分")
    @Scheduled(cron = "0 30 10,14 * * ? ")
    public void noticeBuyFirst() {
        timmerService.noticeBuy();
    }

    @RequestMapping(value = "/noticeBuySecond", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务提醒低价，11点，以及14点")
    @Scheduled(cron = "0 0 11,14 * * ? ")
    public void noticeBuySecond() {
        timmerService.noticeBuy();
    }

    @RequestMapping(value = "/noticeSellFirst", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务提醒高价，10点30分，以及14点30分")
    @Scheduled(cron = "0 30 10,14 * * ? ")
    public void noticeSellFirst() {
        timmerService.noticeSell();
    }

    @RequestMapping(value = "/noticeSellSecond", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务提醒高价，11点，以及14点")
    @Scheduled(cron = "0 0 11,14 * * ? ")
    public void noticeSellSecond() {
        timmerService.noticeSell();
    }

    @RequestMapping(value = "/insertDaylyRecord", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("定时任务，每天收盘记录数据")
    @Scheduled(cron = "0 5 15 * * ? ")
    public void insertDaylyRecord() {
        timmerService.insertDaylyRecord();
    }

    @GetMapping("/updateBankWeight")
    @ResponseBody
    @ApiOperation("定时任务，升降银行股权重")
    @Scheduled(cron = "0 0,10,20,30,40,50 9,10,11,13,14 * * ? ")
    public void updateBankWeight() {
        timmerService.updateBankWeight();
    }

    @GetMapping("/reminder")
    @ResponseBody
    @ApiOperation("定时任务，涨跌超过5%邮件推送,每10分钟执行一次")
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void reminder() {
        timmerService.reminder();
    }

    @GetMapping("/noticeTarget")
    @ResponseBody
    @ApiOperation("定时任务，每1分钟观察数据到达出售买入价格")
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void noticeTarget() {
        timmerService.noticeTarget();
    }

    @GetMapping("/theGapEachDay")
    @ResponseBody
    @ApiOperation("定时任务，每1分钟获取当前价格记录比率")
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void theGapEachDay(){
        timmerService.theGapEachDay();
    }

    @GetMapping("/downHillTypeFinish")
    @ResponseBody
    @ApiOperation("定时任务，每天9点15分获取下坡型预计可以买的股票")
    @Scheduled(cron = "0 10 15 * * ? ")
    public void downHillTypeFinish(){
        timmerService.downHillType();
    }

    @GetMapping("/downHillTypeNow")
    @ResponseBody
    @ApiOperation("定时任务，每天9点15分获取下坡型预计可以买的股票")
    @Scheduled(cron = "0 30 14 * * ? ")
    public void downHillTypeNow(){
        timmerService.downHillType();
    }
}
