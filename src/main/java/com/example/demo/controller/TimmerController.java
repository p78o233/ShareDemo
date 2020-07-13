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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
