package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2020/7/10
 */

import com.example.demo.entity.po.*;
import com.example.demo.entity.vo.UserMailContentVo;
import com.example.demo.mapper.TimmerMapper;
import com.example.demo.service.TimmerService;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
//            今天没有停牌
            if(Float.valueOf(result[1]) != 0.0f) {
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
        if(checkTimeNow()) {
            List<Stock> list = new ArrayList<Stock>();
            list = timmerMapper.getAllStock();
            for (Stock stock : list) {
//        获取昨天的数据
                StockRecord yesterdayRecord = timmerMapper.getYesterdayRecord(stock.getStockNum());
//        获取当前的数据
                String result[] = getStockNowPrice(stock.getStockNum());
                if ((Float.valueOf(result[3]) / yesterdayRecord.getEndPrice()) - 1 > 0.05) {
//                升超过5%
                    List<UserMailContentVo> userList = new ArrayList<UserMailContentVo>();
                    userList = timmerMapper.getUserByUserStock(stock.getId());
                    for (UserMailContentVo user : userList) {
                        user.setContentList(new ArrayList<String>());
                        String strResult = stock.getStockNum() + ":  :" + stock.getStockName() + ": :涨幅超过5%,当前幅度为："+ String.valueOf((Float.valueOf(result[3]) / yesterdayRecord.getEndPrice()) - 1);
                        user.getContentList().add(strResult);
                        MailUtils.sendSimpleMail(sender, user.getEmailAddress(), "涨幅", user.getContentList().toString());
                    }
                } else if ((Float.valueOf(result[3]) / yesterdayRecord.getEndPrice()) - 1 < -0.05) {
//                跌超过5%
                    List<UserMailContentVo> userList = new ArrayList<UserMailContentVo>();
                    userList = timmerMapper.getUserByUserStock(stock.getId());
                    for (UserMailContentVo user : userList) {
                        user.setContentList(new ArrayList<String>());
                        String strResult = stock.getStockNum() + ":  :" + stock.getStockName() + ": :跌幅超过5% ,当前幅度为："+String.valueOf((Float.valueOf(result[3]) / yesterdayRecord.getEndPrice()) - 1);;
                        user.getContentList().add(strResult);
                        MailUtils.sendSimpleMail(sender, user.getEmailAddress(), "跌幅", user.getContentList().toString());
                    }
                }
            }
        }
    }

    @Override
    public void noticeTarget() {
        if(checkTimeNow()) {
            List<Stock> stockList = new ArrayList<>();
            stockList = timmerMapper.getAllStock();
            for (Stock stock : stockList) {
//            获取当前价格
                String result[] = getStockNowPrice(stock.getStockNum());
//            根据股票编码对比设定价格与当前价格
//            买入列表
                List<BuySellNotice> buyList = new ArrayList<>();
                buyList = timmerMapper.getBuyNotice(stock.getStockNum(), Float.valueOf(result[3]));
//            卖出列表
                List<BuySellNotice> sellList = new ArrayList<>();
                sellList = timmerMapper.getSellNotice(stock.getStockNum(), Float.valueOf(result[3]));
//            买列表处理
                for (BuySellNotice notice : buyList) {
//                根据用户id查询用户邮件地址
                    User user = new User();
                    user = timmerMapper.getUserDetail(notice.getUserId());
                    timmerMapper.updateBuySellNoticeSendTimes(notice.getId(), new Date());
                    if (user != null) {
                        String sendStr = stock.getStockNum() + " 名称：" + stock.getStockName() + ",到达预定买入价格：" + notice.getPrice() + ",当前价格：" + result[3];
                        MailUtils.sendSimpleMail(sender, user.getEmailAddress(), "买入提醒", sendStr);
                    }
                }
//            卖列表处理
                for (BuySellNotice notice : sellList) {
//                根据用户id查询用户邮件地址
                    User user = new User();
                    user = timmerMapper.getUserDetail(notice.getUserId());
                    timmerMapper.updateBuySellNoticeSendTimes(notice.getId(), new Date());
                    if (user != null) {
                        String sendStr = stock.getStockNum() + " 名称：" + stock.getStockName() + ",到达预定卖出价格：" + notice.getPrice() + ",当前价格：" + result[3];
                        MailUtils.sendSimpleMail(sender, user.getEmailAddress(), "卖出提醒", sendStr);
                    }
                }
            }
        }
    }

    @Override
    public void theGapEachDay() {
        List<Stock> stockList = new ArrayList<>();
        if(checkTimeNow()) {
            stockList = timmerMapper.getAllStock();
            for (Stock stock : stockList) {
                String result[] = getStockNowPrice(stock.getStockNum());
//            不是停牌的数据
                if (Float.valueOf(result[3]) != 0.0f) {
//                获取昨天的数据
                    float yesterdayPrice = timmerMapper.getYesterdayPrice(stock.getId());
                    StockRate stockRate = new StockRate();
                    if (Float.valueOf(result[3]) < yesterdayPrice) {
//                    当前价格比昨天的低
                        float ratio = ((yesterdayPrice / Float.valueOf(result[3])) - 1) * 100;
                        DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                        String p = decimalFormat.format(ratio);
                        ratio = Float.valueOf(p);
                        stockRate.setStockId(stock.getId());
                        stockRate.setCate(-1);
                        stockRate.setCreateTime(new Time(new Date().getTime()));
                        stockRate.setRatio(ratio);
                    } else if (Float.valueOf(result[3]) == yesterdayPrice) {
                        stockRate.setStockId(stock.getId());
                        stockRate.setCate(0);
                        stockRate.setCreateTime(new Time(new Date().getTime()));
                        stockRate.setRatio(0.0f);
                    } else if (Float.valueOf(result[3]) > yesterdayPrice) {
                        float ratio = ((Float.valueOf(result[3]) / yesterdayPrice) - 1) * 100;
                        DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                        String p = decimalFormat.format(ratio);
                        ratio = Float.valueOf(p);
                        stockRate.setStockId(stock.getId());
                        stockRate.setCate(1);
                        stockRate.setCreateTime(new Time(new Date().getTime()));
                        stockRate.setRatio(ratio);
                    }
//                新增数据库
                    timmerMapper.insertStockRate(stockRate);
                }
            }
        }
    }

    public boolean checkTimeNow(){
//        确定是否交易时间
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        if((hour==9&&min>30)||(hour == 10)||(hour==11&&min<30)||(hour==13)||(hour == 14)) {
            return true;
        }else{
            return false;
        }
    }
}
