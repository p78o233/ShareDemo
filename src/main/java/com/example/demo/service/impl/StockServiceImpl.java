package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.po.*;
import com.example.demo.entity.vo.StockPriceVo;
import com.example.demo.entity.vo.StockRecordVo;
import com.example.demo.mapper.StockMapper;
import com.example.demo.service.StockService;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.MailUtils;
import com.example.demo.utils.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private JavaMailSender sender;

    @Override
    public List<Stock> testList() {
        return null;
    }

    @Override
    public boolean insertStock(List<Stock> stocks) {
        for (Stock stock : stocks) {
            stock.setCreateTime(new Date());
            stockMapper.insertStock(stock);
        }
        return true;
    }

    @Override
    public boolean daylyRecord() throws IOException {
        List<Stock> list = new ArrayList<Stock>();
        list = stockMapper.getAllStock();
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
            stockMapper.daylyRecord(sr);
        }
        return true;
    }

    @Override
    public boolean insertBuyRecord(BuySellRecord buySellRecord) {
        buySellRecord.setBuyTime(new Date());
        if (stockMapper.insertBuyRecord(buySellRecord) > 0)
            return true;
        return false;
    }

    @Override
    public boolean updateSellRecord(BuySellRecord buySellRecord) {
        BuySellRecord buySellRecordOld = new BuySellRecord();
        buySellRecordOld = stockMapper.getOneBuySellRecord(buySellRecord.getId());
        buySellRecord.setProfitOrLoss((buySellRecord.getSellPrice() - buySellRecordOld.getBuyPrice()) * Float.valueOf(buySellRecordOld.getBuyNum()));
        buySellRecord.setSellTime(new Date());
        if (stockMapper.updateSellRecord(buySellRecord) > 0)
            return true;
        return false;
    }

    @Override
    public void noticeSell(int userId) {
        List<BuySellRecord> buyRecords = new ArrayList<BuySellRecord>();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        try {
            nowDate = sDateFormat.parse(sDateFormat.format(new Date()));
        } catch (ParseException px) {
            px.printStackTrace();
        }
        buyRecords = stockMapper.getAllNowBuySellRecord(nowDate,userId);
        List<String> mailContent = new ArrayList<String>();
//        发邮件的id
        List<Integer> sendMailIds = new ArrayList<Integer>();
        for (BuySellRecord buySellRecord : buyRecords) {
            String result[] = getStockNowPrice(buySellRecord.getStockNum());
            String content = "";
            if (Float.valueOf(result[3]) / buySellRecord.getBuyPrice() > 1.015) {
//                超过1.5%发邮件
                content = "涨，出售名称：" + buySellRecord.getStockName() + ",编号：" + buySellRecord.getStockNum() + ",购入价格，" + buySellRecord.getBuyPrice() +
                        ",当前价格：" + Float.valueOf(result[3]) + "涨幅：" + (Float.valueOf(result[3]) / buySellRecord.getBuyPrice() - 1) * 100 + "%\n";
                mailContent.add(content);
                sendMailIds.add(buySellRecord.getId());
            } else if (Float.valueOf(result[3]) / buySellRecord.getBuyPrice() < 0.98) {
//                跌超过2%发邮件
                content = "跌，出售名称：" + buySellRecord.getStockName() + ",编号：" + buySellRecord.getStockNum() + ",购入价格，" + buySellRecord.getBuyPrice() +
                        ",当前价格：" + Float.valueOf(result[3]) + "跌幅：" + (1 - Float.valueOf(result[3]) / buySellRecord.getBuyPrice()) * 100 + "%\n";
                mailContent.add(content);
                sendMailIds.add(buySellRecord.getId());
            } else {
                continue;
            }
        }
        if (mailContent.size() > 0) {
            MailUtils.sendSimpleMail(sender, "953712390@qq.com", "卖", mailContent.toString());
            stockMapper.updateBuySellRecordSendTimes(sendMailIds);
        }
    }

    @Override
    public void noticeBuy() {
//        获取符合一定天数范围内的stockNum
        ArrayList<Integer> dayList = new ArrayList<Integer>() {{
            add(5);
            add(10);
            add(20);
            add(50);
        }};
        List<StockRecordVo> stockRecordVos = new ArrayList<>();
        for (int i = 0; i < dayList.size(); i++) {
            stockRecordVos = stockMapper.getStockNums(dayList.get(i));
            for (StockRecordVo stockRecordVo : stockRecordVos) {
                try {
//                    当前价格
                    String result[] = getStockNowPrice(stockRecordVo.getStockNum());
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Float stockMinPrice = stockMapper.getLatestLowestPrice(stockRecordVo.getStockNum(),dayList.get(i));
                    Float stockMaxPrice = stockMapper.getLatestHightPrice(stockRecordVo.getStockNum(),dayList.get(i));
                    if (stockMinPrice != null && stockMaxPrice != null) {
                        if (Float.valueOf(result[3]) < stockMinPrice) {
//                        小于dayList.get(i)日的最低价存入数据库
                            LowRecord lowRecord = new LowRecord();
                            lowRecord.setStockId(stockRecordVo.getStockId());
                            lowRecord.setStockNum(stockRecordVo.getStockNum());
                            lowRecord.setStockName(stockRecordVo.getStockName());
                            lowRecord.setCategory(stockRecordVo.getCategory());
                            lowRecord.setRecordDay(dayList.get(i));
                            lowRecord.setMinPrice(stockMinPrice);
                            lowRecord.setMaxPrice(stockMaxPrice);
                            lowRecord.setRecordPrice(Float.valueOf(result[3]));
                            lowRecord.setRecordTime(new Date());
                            lowRecord.setLowHis(stockMapper.getLowestRecord(stockRecordVo.getStockNum()).getLowPrice());
                            lowRecord.setDayBefore((int) ToolsUtils.differentDaysByMillisecond(stockMapper.getLowestRecord(stockRecordVo.getStockNum()).getRecordTime(), new Date()));
                            lowRecord.setHighHis(stockMapper.getHighestRecord(stockRecordVo.getStockNum()).getHighPrice());
                            lowRecord.setDayBeforeH((int)ToolsUtils.differentDaysByMillisecond(stockMapper.getHighestRecord(stockRecordVo.getStockNum()).getRecordTime(),new Date()));
//                        检查趋势，若是小于10天则检查全部，若是20天看10天 50天看20天
                            List<StockRecord> stockRecords = new ArrayList<StockRecord>();
                            if (dayList.get(i) <= 10) {
                                stockRecords = stockMapper.getLatestRate(stockRecordVo.getStockNum(),0,dayList.get(i));
                            } else if (dayList.get(i) == 20) {
                                stockRecords = stockMapper.getLatestRate(stockRecordVo.getStockNum(),0,10);
                            } else if (dayList.get(i) == 50) {
                                stockRecords = stockMapper.getLatestRate(stockRecordVo.getStockNum(),0,20);
                            }
//                        结束价钱小于起始价钱天数
                            float collectData = 0.0f;
                            for (StockRecord stockRecord : stockRecords) {
                                if (stockRecord.getEndPrice() < stockRecord.getBeginPrice()) {
                                    collectData += 1.0f;
                                } else {
                                    continue;
                                }
                            }
                            if (collectData / stockRecords.size() > 0.6) {
                                lowRecord.setTrend((short) -1);
                            } else if (collectData / stockRecords.size() < 0.4) {
                                lowRecord.setTrend((short) 1);
                            } else {
                                lowRecord.setTrend((short) 0);
                            }
                            stockMapper.insertLowRecord(lowRecord);
                        } else {
                            continue;
                        }
                    }
                } catch (Exception px) {
                    px.printStackTrace();
                }
            }
        }
        //                获取全部没有发邮件的
        List<LowRecord> lowList = new ArrayList<LowRecord>();
        lowList = stockMapper.getAllLowRecordNotSend();
        List<String> mailContent = new ArrayList<String>();
        for (LowRecord low : lowList) {
            String category = "";
//                    1 股票 2基金 3黄金 4期货
            switch (low.getCategory()) {
                case 1:
                    category = "股票";
                    break;
                case 2:
                    category = "基金";
                    break;
                case 3:
                    category = "黄金";
                    break;
                case 4:
                    category = "期货";
                    break;
            }
            String trend = "";
            if (low.getTrend() == (short) -1) {
                trend = "下跌";
            } else if (low.getTrend() == (short) 1) {
                trend = "上升";
            } else {
                trend = "震荡";
            }
            String content = "购买名称：" + low.getStockName() + ",编号：" + low.getStockNum() + ",类别：" + category + "," + low.getRecordDay() + "天最低价：" + low.getMinPrice() +
                    ",当前记录价：" + low.getRecordPrice() + ",历史最低价" + low.getLowHis() + ",距今天" + low.getDayBefore() + "天    "
                    + low.getRecordDay() + "天最高价：" + low.getMaxPrice() +
                    ",当前记录价：" + low.getRecordPrice() + ",历史最高价" + low.getHighHis() + ",距今天" + low.getDayBeforeH() + "天    "
                    + "趋势：  " + trend + "\n";
            mailContent.add(content);
        }
        if (mailContent.size() > 0) {
            MailUtils.sendSimpleMail(sender, "953712390@qq.com", "买", mailContent.toString());
            stockMapper.updateLowRecordIsSend();
        }
    }

    @Override
    public List<BuySellRecord> getAllBuyRecord(int userId) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        try {
            nowDate = sDateFormat.parse(sDateFormat.format(new Date()));
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return stockMapper.getAllNowBuySellRecord(nowDate,userId);
    }

    //    单个查询
    public String[] getStockNowPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("list", stockNum);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(",");
        return result;
    }

    //    批量查询
    public String[] getStocksAllNowPrice(List<Stock> stocks) {
        HashMap<String, String> params = new HashMap<>();
        String stockNumStr = "";
        for (Stock stock : stocks) {
            stockNumStr += stock.getStockNum() + ",";
        }
        stockNumStr = stockNumStr.substring(0, stockNumStr.length() - 1);
        params.put("list", stockNumStr);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(";");
        return result;
    }

    @Override
    public List<StockPriceVo> checkNowPrice(int userId,List<String> stockNums) {
        List<StockPriceVo> stockPriceVoList = new ArrayList<StockPriceVo>();
        if (stockNums.size() == 0) {
            stockNums = stockMapper.getAllStockNums(userId);
        }
        for (String stockNum : stockNums) {
            StockPriceVo stockPriceVo = new StockPriceVo();
            stockPriceVo = stockMapper.getMaxHisHighLowPrice(stockNum);
//            try {
            if (stockPriceVo == null) {
                stockPriceVo = new StockPriceVo();
            }
//                昨天的记录
            StockRecord yesterdayRecord = stockMapper.getYesterdayRecord(stockNum);
            String result[] = getStockNowPrice(stockNum);
            if (yesterdayRecord != null) {
                DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String p = decimalFormat.format(((Float.valueOf(result[3]) / Float.valueOf(yesterdayRecord.getEndPrice())) - 1) * 100);//format 返回的是字符串
                stockPriceVo.setRate(p + "%");
            }
            stockPriceVo.setNowPrice(Float.valueOf(result[3]));
            if (stockMapper.getLowestRecord(stockNum) != null) {
                stockMapper.setRankNum();
                stockPriceVo.setLowDays(stockPriceVo.getDayNums() - stockMapper.getLowPriceRecordDay(stockNum));
                stockMapper.setRankNum();
                stockPriceVo.setHeighDays(stockPriceVo.getDayNums() - stockMapper.getHeightPriceRecordDay(stockNum));
            }
//            获取近10天以及20的最大最小值
            if (stockPriceVo.getDayNums() > 20) {
//                取20天最小值,最大值
//                stockPriceVo.setLastestTwenHeight(stockMapper.getStockLastestHigh(stockNum, stockPriceVo.getDayNums() - 20, 20));
//                stockPriceVo.setLastestTwenLow(stockMapper.getStockLastestlow(stockNum, stockPriceVo.getDayNums() - 20, 20));
                stockPriceVo.setLastestTwenHeight(stockMapper.getStockLastestHigh(stockNum, 0, 20));
                stockPriceVo.setLastestTwenLow(stockMapper.getStockLastestlow(stockNum, 0, 20));
            } else {
                stockPriceVo.setLastestTwenHeight(null);
                stockPriceVo.setLastestTwenLow(null);
            }
            if (stockPriceVo.getDayNums() > 10) {
//                取10天最小值，最大值
//                stockPriceVo.setLastestTenHeight(stockMapper.getStockLastestHigh(stockNum, stockPriceVo.getDayNums() - 10, 10));
//                stockPriceVo.setLastestTenLow(stockMapper.getStockLastestlow(stockNum, stockPriceVo.getDayNums() - 10, 10));
                stockPriceVo.setLastestTenHeight(stockMapper.getStockLastestHigh(stockNum, 0, 10));
                stockPriceVo.setLastestTenLow(stockMapper.getStockLastestlow(stockNum, 0, 10));
            } else {
                stockPriceVo.setLastestTenHeight(null);
                stockPriceVo.setLastestTenLow(null);
            }

//            获取当前价格在近段时间内处于什么位置 1、极低  2、偏低 3、较低 4、中 5、较高 6、偏高 7、极高
            if(stockPriceVo.getDayNums()>20){
                stockPriceVo.setNowStatus(getNowStatus(stockPriceVo.getLastestTwenHeight(),stockPriceVo.getLastestTwenLow(),stockPriceVo.getNowPrice()));
            }else if(stockPriceVo.getDayNums()>10){
                stockPriceVo.setNowStatus(getNowStatus(stockPriceVo.getLastestTenHeight(),stockPriceVo.getLastestTenLow(),stockPriceVo.getNowPrice()));
            }else{
                Float stockMinPrice = stockMapper.getLatestLowestPrice(stockPriceVo.getStockNum(),10);
                Float stockMaxPrice = stockMapper.getLatestHightPrice(stockPriceVo.getStockNum(),10);
                stockPriceVo.setNowStatus(getNowStatus(stockMaxPrice,stockMinPrice,stockPriceVo.getNowPrice()));
            }

            if (yesterdayRecord != null) {
                stockPriceVo.setYesterdayPrice(yesterdayRecord.getEndPrice());
                if (yesterdayRecord.getBeginPrice() > yesterdayRecord.getEndPrice())
                    stockPriceVo.setStauts("跌");
                else if (yesterdayRecord.getBeginPrice() < yesterdayRecord.getEndPrice())
                    stockPriceVo.setStauts("涨");
                else
                    stockPriceVo.setStauts("平");
            } else {
                stockPriceVo.setYesterdayPrice(0.0f);
                stockPriceVo.setStauts("未知");
            }


//                        获取平均值
            stockPriceVo = getAvg(stockPriceVo);
            stockPriceVoList.add(stockPriceVo);
//            } catch (Exception e) {
//                System.out.println(stockNum);
//            }

        }
        return stockPriceVoList;
    }

    @Override
    public JSONObject getHistoryPrice(String stockNum) {
        List<StockRecord> list = new ArrayList<StockRecord>();
        list = stockMapper.getHistoryPrice(stockNum);
        StockPriceVo stockPriceVo = new StockPriceVo();
        stockPriceVo = stockMapper.getMaxHisHighLowPrice(stockNum);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("historyHighPrice", stockPriceVo.getHeightPriceHis());
        jsonObject.put("historyLowPrice", stockPriceVo.getLowPriceHis());
        return jsonObject;
    }

    @Override
    public void reminder() {
        List<Stock> stocks = new ArrayList<>();
        stocks = stockMapper.getAllStock();
        String[] result = getStocksAllNowPrice(stocks);
        List<String> mailContent = new ArrayList<String>();
        for (String resultItemStr : result) {
            if (!resultItemStr.equals("\n")) {
                String[] resultItem = resultItemStr.split(",");
                if ((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1 > 0.05)) {
//                升超过5%
                    mailContent.add(resultItem[0] + "升幅超过5%，当前为：" + String.valueOf((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1)) + "\n");
                } else if ((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1 < -0.05)) {
//                跌超过5%
                    if ((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1 != -1.0)) {
//                        停牌的不看
                        mailContent.add(resultItem[0] + "跌幅超过5%，当前为：" + String.valueOf((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1)) + "\n");
                    }
                } else if ((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1 > 0.03)) {
//                升超过3%
                    mailContent.add(resultItem[0] + "升幅超过3%，当前为：" + String.valueOf((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1)) + "\n");
                } else if ((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1 < -0.03)) {
//                跌超过3%
                    mailContent.add(resultItem[0] + "跌幅超过5%，当前为：" + String.valueOf((Float.valueOf(resultItem[1]) / Float.valueOf(resultItem[3]) - 1)) + "\n");
                }
            }
        }
        if (mailContent.size() > 0) {
            MailUtils.sendSimpleMail(sender, "953712390@qq.com", "涨跌幅", mailContent.toString());
        }
    }

    @Override
    public void lookShangData() {
        String result[] = getStockNowPrice("s_sh000001");
        if (Float.valueOf(result[3]) > 0.1) {
//            银行类的降权重
            stockMapper.changeBankStockWeight(-100);
        } else {
//            银行类的升权重
            stockMapper.changeBankStockWeight(100);
        }
    }

    @Override
    public void test() {
        List<StockRecord> records = new ArrayList<>();
        records = stockMapper.getAllStockRecord();
        List<Integer> raise = new ArrayList<>();
        List<Integer> equles = new ArrayList<>();
        List<Integer> drop = new ArrayList<>();
        for (StockRecord record : records) {
            if (record.getBeginPrice() > record.getEndPrice()) {
                drop.add(record.getId());
            } else if (record.getBeginPrice() == record.getEndPrice())
                equles.add(record.getId());
            else {
                raise.add(record.getId());
            }
        }
        stockMapper.updateFlag(1, raise);
        stockMapper.updateFlag(0, equles);
        stockMapper.updateFlag(-1, drop);
    }

    @Override
    public List<Stock> getAllStock(int userId) {
        return stockMapper.getAllStock();
    }

    @Override
    public void tagBuy() {
        List<TagBuySell> list = stockMapper.getTagBuySellList(false);
        List<String> mailContent = new ArrayList<String>();
        for(TagBuySell tagBuySell : list){
            String result[] = getStockNowPrice(tagBuySell.getStockNum());
            if(Float.valueOf(result[3])<tagBuySell.getTagPrice()){
                mailContent.add("编号："+tagBuySell.getStockNum()+"，名称："+tagBuySell.getStockName()+"，当前价格："+Float.valueOf(result[3])+"，目标价格："+tagBuySell.getTagPrice()+"\n");
            }
        }
        if (mailContent.size() > 0) {
            MailUtils.sendSimpleMail(sender, "953712390@qq.com", "目标买", mailContent.toString());
        }
    }

    @Override
    public void tagSell() {
        List<TagBuySell> list = stockMapper.getTagBuySellList(true);
        List<String> mailContent = new ArrayList<String>();
        for(TagBuySell tagBuySell : list){
            String result[] = getStockNowPrice(tagBuySell.getStockNum());
            if(Float.valueOf(result[3])>tagBuySell.getTagPrice()){
                mailContent.add("编号："+tagBuySell.getStockNum()+"，名称："+tagBuySell.getStockName()+"，当前价格："+Float.valueOf(result[3])+"，目标价格："+tagBuySell.getTagPrice()+"\n");
            }
        }
        if (mailContent.size() > 0) {
            MailUtils.sendSimpleMail(sender, "953712390@qq.com", "目标卖", mailContent.toString());
        }
    }

    public StockPriceVo getAvg(StockPriceVo stockPriceVo) {
        List<StockRecord> stockRecords = new ArrayList<>();
        stockRecords = stockMapper.getHistoryPrice(stockPriceVo.getStockNum());
//        连续升当前id
        int raiseId = -1;
        List<Integer> raiseList = new ArrayList<>();
        List<Integer> raiseDays = new ArrayList<>();
//        连续跌当前id
        int dropId = -1;
        List<Integer> dropList = new ArrayList<>();
        List<Integer> dropDays = new ArrayList<>();
//        连续的天数
        int continuity = 0;
//        上一个是什么
        int flag = 0;
        for (int i = 0; i < stockRecords.size(); i++) {
            if (stockRecords.get(i).getFlag() == 1) {
                if (flag == 1) {
                    raiseId = i;
                    continuity++;
                } else {
                    if (continuity > 1) {
                        if (dropId != -1) {
                            dropList.add(dropId);
                        }
                    }
                    raiseId = i;
                    dropDays.add(continuity);
                    continuity = 1;
                }
                flag = 1;
            } else if (stockRecords.get(i).getFlag() == -1) {
                if (flag == -1) {
                    dropId = i;
                    continuity++;
                } else {
                    if (continuity > 1) {
                        if (raiseId > -1) {
                            raiseList.add(raiseId);
                        }
                    }
                    dropId = i;
                    raiseDays.add(continuity);
                    continuity = 1;
                }
                flag = -1;
            }
        }
        if (flag == 1 && continuity > 1) {
            raiseList.add(raiseId);
            raiseDays.add(continuity);
        } else if (flag == -1 && continuity > 1) {
            dropList.add(dropId);
            dropDays.add(continuity);
        }
//        遍历涨跌平均间隔天数
        if (raiseList.size() > 1) {
            float raiseSum = 0.0f;
            for (int i = 1; i < raiseList.size(); i++) {
                raiseSum += raiseList.get(i) - raiseList.get(i - 1);
            }
            stockPriceVo.setAvgRaiseCycle(raiseSum / (raiseList.size() - 1));
        } else {
            stockPriceVo.setAvgRaiseCycle(null);
        }
        if (dropList.size() > 1) {
            float dropSum = 0.0f;
            for (int i = 1; i < dropList.size(); i++) {
                dropSum += dropList.get(i) - dropList.get(i - 1);
            }
            stockPriceVo.setAvgDropCycle(dropSum / (dropList.size() - 1));
        } else {
            stockPriceVo.setAvgDropCycle(null);
        }
//        遍历涨跌平均连续天数
        if (raiseDays.size() > 0) {
            float raiseDaySum = 0.0f;
            for (int i = 0; i < raiseDays.size(); i++) {
                raiseDaySum += raiseDays.get(i);
            }
            stockPriceVo.setAvgRaise(raiseDaySum / raiseDays.size());
        } else {
            stockPriceVo.setAvgRaise(null);
        }
        if (dropDays.size() > 0) {
            float dropDaySum = 0.0f;
            for (int i = 0; i < dropDays.size(); i++) {
                dropDaySum += dropDays.get(i);
            }
            stockPriceVo.setAvgDrop(dropDaySum / raiseDays.size());
        } else {
            stockPriceVo.setAvgDrop(null);
        }
        stockPriceVo.setLastDays(continuity);
        return stockPriceVo;
    }
//    返回当前价格状态
    public String getNowStatus(float hisHigh,float hisLow,float nowPrice){
        String result = "";
        if(nowPrice<hisLow)
            result = "极低";
        else if(nowPrice>hisHigh)
            result = "极高";
        else if((nowPrice/hisLow)-1>0.8){
            result = "偏高";
        } else if((nowPrice/hisLow)-1>0.6){
            result = "较高";
        }else if((nowPrice/hisLow)-1>0.45){
            result = "中";
        }else if((nowPrice/hisLow)-1>0.25){
            result = "较低";
        }else if((nowPrice/hisLow)-1>0){
            result = "偏低";
        }
        return result;
    }
}
