package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2020/7/10
 */

import com.example.demo.entity.po.Stock;
import com.example.demo.entity.po.StockRecord;
import com.example.demo.entity.po.User;
import com.example.demo.entity.vo.UserMailContentVo;
import com.example.demo.mapper.TimmerMapper;
import com.example.demo.service.TimmerService;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TimmerServiceImpl implements TimmerService {
    @Autowired
    private TimmerMapper timmerMapper;
    @Autowired
    private JavaMailSender sender;

    //    单个查询
    public String[] getStockNowPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("list", stockNum);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(",");
        return result;
    }

    @Override
    public void noticeBuy() {
//        天数列表
        ArrayList<Integer> dayNums = new ArrayList<Integer>() {{
            add(5);
            add(10);
            add(20);
            add(50);
        }};
//        1、获取当前要查看的记录历表,查看所有系统中的用户
        List<Stock> stockList = new ArrayList<>();
        stockList = timmerMapper.getAllStock();
//        2. 获取当前全部用户
        List<UserMailContentVo> userMailContentVoList = new ArrayList<>();
        userMailContentVoList = timmerMapper.getAllUser();
        for (int i = 0; i < stockList.size(); i++) {
//            当前价格
            String result[] = getStockNowPrice(stockList.get(i).getStockNum());
            float nowPrice = Float.valueOf(result[3]);
//            历史最高价
            StockRecord historyMaxStockRecord = timmerMapper.getHeightestPrice(stockList.get(i).getStockNum());
//            历史最低价
            StockRecord historyminStockRecord = timmerMapper.getLowestPrice(stockList.get(i).getStockNum());
//            查询天数列表最低价
            for (int dayNum : dayNums) {
//                某个天数范围内的最小值
                float minRectPrice = timmerMapper.getLowestRectDay(stockList.get(i).getStockNum(), dayNum);
//                某个天数的最大值
                float maxRectPrice = timmerMapper.getHeightRectDay(stockList.get(i).getStockNum(), dayNum);
                if(minRectPrice > nowPrice) {
                    userMailContentVoList = setUserMailContentVo(userMailContentVoList, stockList.get(i), dayNum, maxRectPrice, minRectPrice, nowPrice, historyminStockRecord, historyMaxStockRecord);
                }
            }
        }
        //                发送邮件
        for (UserMailContentVo vo : userMailContentVoList) {
            if(vo.getContentList() != null) {
                MailUtils.sendSimpleMail(sender, vo.getEmailAddress(), "买入建议", vo.getContentList().toString());
            }
        }
    }

    @Override
    public void noticeSell() {
//        天数列表
        ArrayList<Integer> dayNums = new ArrayList<Integer>() {{
            add(5);
            add(10);
            add(20);
            add(50);
        }};
//        1、获取当前要查看的记录历表,查看所有系统中的用户
        List<Stock> stockList = new ArrayList<>();
        stockList = timmerMapper.getAllStock();
//        2. 获取当前全部用户
        List<UserMailContentVo> userMailContentVoList = new ArrayList<>();
        userMailContentVoList = timmerMapper.getAllUser();
        for (int i = 0; i < stockList.size(); i++) {
//            当前价格
            String result[] = getStockNowPrice(stockList.get(i).getStockNum());
            float nowPrice = Float.valueOf(result[3]);
//            历史最高价
            StockRecord historyMaxStockRecord = timmerMapper.getHeightestPrice(stockList.get(i).getStockNum());
//            历史最低价
            StockRecord historyminStockRecord = timmerMapper.getLowestPrice(stockList.get(i).getStockNum());
//            查询天数列表最低价
            for (int dayNum : dayNums) {
//                某个天数范围内的最小值
                float minRectPrice = timmerMapper.getLowestRectDay(stockList.get(i).getStockNum(), dayNum);
//                某个天数的最大值
                float maxRectPrice = timmerMapper.getHeightRectDay(stockList.get(i).getStockNum(), dayNum);
                if(nowPrice > maxRectPrice) {
                    userMailContentVoList = setUserMailContentVo(userMailContentVoList, stockList.get(i), dayNum, maxRectPrice, minRectPrice, nowPrice, historyminStockRecord, historyMaxStockRecord);
                }
            }
        }
        //                发送邮件
        for (UserMailContentVo vo : userMailContentVoList) {
            if(vo.getContentList() != null) {
                MailUtils.sendSimpleMail(sender, vo.getEmailAddress(), "买入建议", vo.getContentList().toString());
            }
        }
    }
//    邮件编写
    public List<UserMailContentVo> setUserMailContentVo(List<UserMailContentVo> userMailContentVoList, Stock stock, int dayNum, float maxRectPrice, float minRectPrice, float nowPrice, StockRecord historyLowPriceStockRecord, StockRecord historyHightPriceStockRecord) {
        //                当前价格小于最近天数的最小值
        String title = "";
        if (nowPrice < minRectPrice) {
            title = "建议购买名称：";
        } else if (nowPrice > maxRectPrice) {
            title = "建议卖出名称：";
        }
        //                    查询观察了这个股票的用户
        List<Integer> lookUserIdList = new ArrayList<>();
        lookUserIdList = timmerMapper.getLookUserId(stock.getId());
//                    循环遍历添加请求数据
        for (Integer userId : lookUserIdList) {
            for (UserMailContentVo vo : userMailContentVoList) {
                if (vo.getId() == userId) {
                    String contentStr = title + stock.getStockName() + ",编号：" + stock.getStockNum() + ",类别：股票，" +
                            "," + dayNum + "天最低价：" + minRectPrice + ",当前价格：" + nowPrice + "," + dayNum + "天最高价：" + maxRectPrice + ",历史最低价："
                            + historyLowPriceStockRecord.getLowPrice()
                            + ",历史最高价：" + historyHightPriceStockRecord.getHighPrice()+"%\n";
                    if(vo.getContentList()!=null) {
                        vo.getContentList().add(contentStr);
                    }else{
                        vo.setContentList(new ArrayList<String>());
                        vo.getContentList().add(contentStr);
                    }
                }
            }
        }
        return userMailContentVoList;
    }

    @Override
    public void insertDaylyRecord() {
        List<Stock> list = new ArrayList<Stock>();
        list = timmerMapper.getAllStock();
        for (Stock stock : list) {
            String result[] = getStockNowPrice(stock.getStockNum());
            StockRecord sr = new StockRecord();
            sr.setBeginPrice(Float.valueOf(result[1]));
            sr.setEndPrice(Float.valueOf(result[3]));
            sr.setHighPrice(Float.valueOf(result[4]));
            sr.setLowPrice(Float.valueOf(result[5]));
            sr.setStockId(stock.getId());
            sr.setStockName(stock.getStockName());
            sr.setStockNum(stock.getStockNum());
            sr.setCategory(stock.getCategory());
            sr.setUserId(stock.getUserId());
            if (Float.valueOf(result[1]) > Float.valueOf(result[3]))
                sr.setFlag(-1);
            else if (Float.valueOf(result[1]) == Float.valueOf(result[3]))
                sr.setFlag(0);
            else
                sr.setFlag(1);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //加上时间
            //必须捕获异常
            try {
                Date date = sDateFormat.parse(result[30]);
                sr.setRecordTime(date);
            } catch (ParseException px) {
                px.printStackTrace();
            }
            timmerMapper.insertDaylyRecord(sr);
        }
    }

    @Override
    public void updateBankWeight() {
//        上证指数
        String shanghaiResult[] = getStockNowPrice("s_sh000001");
//        深圳指数
        String shenzhenResult[] = getStockNowPrice("s_sz399001");
        if(Float.valueOf(shanghaiResult[3]) > 0.3 && Float.valueOf(shenzhenResult[3]) > 0.3){
//            上海深圳指数上升大于0.3% 银行股下降权重
                timmerMapper.updateBankWeight(-100);
        }else{
//            银行股上升权重
            timmerMapper.updateBankWeight(100);
        }
    }

    @Override
    public void reminder() {
        List<Stock> list = new ArrayList<Stock>();
        list = timmerMapper.getAllStock();
        for(Stock stock : list) {
//        获取昨天的数据
            StockRecord yesterdayRecord = timmerMapper.getYesterdayRecord(stock.getStockNum());
//        获取当前的数据
            String result [] = getStockNowPrice(stock.getStockNum());
            if((Float.valueOf(result[3])/yesterdayRecord.getEndPrice())-1>0.05){
//                升超过5%
                List<UserMailContentVo> userList = new ArrayList<UserMailContentVo>();
                userList = timmerMapper.getUserByUserStock(stock.getId());
                for(UserMailContentVo user : userList){
                    user.setContentList(new ArrayList<String>());
                    String strResult = stock.getStockNum()+":  :"+stock.getStockName()+": :涨幅超过5%";
                    user.getContentList().add(strResult);
                    MailUtils.sendSimpleMail(sender, user.getEmailAddress(), "涨幅", user.getContentList().toString());
                }
            }else if((Float.valueOf(result[3])/yesterdayRecord.getEndPrice())-1 < -0.05){
//                跌超过5%
                List<UserMailContentVo> userList = new ArrayList<UserMailContentVo>();
                userList = timmerMapper.getUserByUserStock(stock.getId());
                for(UserMailContentVo user : userList){
                    user.setContentList(new ArrayList<String>());
                    String strResult = stock.getStockNum()+":  :"+stock.getStockName()+": :跌幅超过5%";
                    user.getContentList().add(strResult);
                    MailUtils.sendSimpleMail(sender, user.getEmailAddress(), "跌幅", user.getContentList().toString());
                }
            }
        }
    }
}
