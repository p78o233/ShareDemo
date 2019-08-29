package com.example.demo.service;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.po.BuySellRecord;
import com.example.demo.entity.po.Stock;

import java.io.IOException;
import java.util.List;

public interface StockService {
    public List<Stock> testList();

//    新增观察的数据
    public boolean insertStock(List<Stock> stocks);

//    根据观察记录记录每天情况(定时任务)
    public boolean daylyRecord()  throws IOException;

//    记录购买情况
    public boolean insertBuyRecord(BuySellRecord buySellRecord);

//    记录卖情况
    public boolean updateSellRecord(BuySellRecord buySellRecord);

//    提示卖(定时任务)
    public void noticeSell();

//    提示买(定时任务)
    public void noticeBuy();

//    获取当前已经买了的
    public List<BuySellRecord> getAllBuyRecord();
}
