package com.example.demo.service;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.alibaba.fastjson.JSONObject;
import com.example.demo.callback.PageInfo;
import com.example.demo.callback.R;
import com.example.demo.entity.po.BuySellRecord;
import com.example.demo.entity.po.Stock;
import com.example.demo.entity.vo.StockPriceVo;

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
    public void noticeSell(int userId);

//    提示买(定时任务)
    public void noticeBuy();

//    获取当前已经买了的
    public List<BuySellRecord> getAllBuyRecord(int userId);

//    查询当前价格,以及记录最低价和记录最高价
    public List<StockPriceVo> checkNowPrice(int userId,int isWeitht,List<String> stockNums);

//    历史数据图
    public JSONObject getHistoryPrice(String stockNum);

//    观察数据涨跌超过5%邮件提醒
    public void reminder();

//    观察上证指数,提高或者降低银行的权重
    public void lookShangData();

//    根据用户id,股票代码分页获取全部观察记录
    public PageInfo<Stock> getAllStock(int userId, String stockNum, String stockName,int page, int pageSize);
//    临时接口随时更改
    public void test();

//    定时任务提示在目标价格买入
    public void tagBuy();
//    定时任务提示在目标价格卖出
    public void tagSell();

//    系统登陆接口
    public R login(String account,String pwd);

//    新增或者保存观察数据
    public int ioeStock(Stock stock);

//    删除观察数据
    public int deleteStock(int id);
}
