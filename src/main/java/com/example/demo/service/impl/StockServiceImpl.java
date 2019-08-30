package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.po.BuySellRecord;
import com.example.demo.entity.po.LowRecord;
import com.example.demo.entity.po.Stock;
import com.example.demo.entity.po.StockRecord;
import com.example.demo.entity.vo.StockRecordVo;
import com.example.demo.mapper.StockMapper;
import com.example.demo.service.StockService;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        return false;
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
        buySellRecord.setProfitOrLoss((buySellRecord.getSellPrice() - buySellRecordOld.getBuyPrice())*Float.valueOf(buySellRecordOld.getBuyNum()));
        buySellRecord.setSellTime(new Date());
        if (stockMapper.updateSellRecord(buySellRecord) > 0)
            return true;
        return false;
    }

    @Override
    public void noticeSell() {
        List<BuySellRecord> buyRecords = new ArrayList<BuySellRecord>();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        try {
            nowDate = sDateFormat.parse(sDateFormat.format(new Date()));
        } catch (ParseException px) {
            px.printStackTrace();
        }
        buyRecords = stockMapper.getAllNowBuySellRecord(nowDate);
        List<String> mailContent = new ArrayList<String>();
//        发邮件的id
        List<Integer> sendMailIds = new ArrayList<Integer>();
        for (BuySellRecord buySellRecord : buyRecords) {
            String result[] = getStockNowPrice(buySellRecord.getStockNum());
            String content = "";
            if (Float.valueOf(result[3]) / buySellRecord.getBuyPrice() > 1.015) {
//                超过1.5%发邮件
                content = "涨，出售名称："+buySellRecord.getStockName()+",编号："+buySellRecord.getStockNum()+",购入价格，"+buySellRecord.getBuyPrice()+
                        ",当前价格："+Float.valueOf(result[3])+"涨幅："+(Float.valueOf(result[3]) / buySellRecord.getBuyPrice()-1)*100+"%\n";
                mailContent.add(content);
                sendMailIds.add(buySellRecord.getId());
            } else if (Float.valueOf(result[3]) / buySellRecord.getBuyPrice() < 0.98) {
//                跌超过2%发邮件
                content = "跌，出售名称："+buySellRecord.getStockName()+",编号："+buySellRecord.getStockNum()+",购入价格，"+buySellRecord.getBuyPrice()+
                        ",当前价格："+Float.valueOf(result[3])+"跌幅："+(1-Float.valueOf(result[3]) / buySellRecord.getBuyPrice())*100+"%\n";
                mailContent.add(content);
                sendMailIds.add(buySellRecord.getId());
            } else {
                continue;
            }
        }
        if(mailContent.size()>0) {
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
                    String result[] = getStockNowPrice(stockRecordVo.getStockNum());
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date nowDate = new Date();
                    nowDate = sDateFormat.parse(sDateFormat.format(new Date()));
                    Date oldDate = new Date();
                    oldDate = sDateFormat.parse(sDateFormat.format(new Date().getTime() - dayList.get(i) *24* 60 * 60 * 1000));
                    float stockMinPrice = stockMapper.getLatestLowestPrice(oldDate, nowDate, stockRecordVo.getStockNum());
                    if (Float.valueOf(result[3]) < stockMinPrice) {
//                        小于dayList.get(i)日的最低价存入数据库
                        LowRecord lowRecord = new LowRecord();
                        lowRecord.setStockId(stockRecordVo.getStockId());
                        lowRecord.setStockNum(stockRecordVo.getStockNum());
                        lowRecord.setStockName(stockRecordVo.getStockName());
                        lowRecord.setCategory(stockRecordVo.getCategory());
                        lowRecord.setRecordDay(dayList.get(i));
                        lowRecord.setMinPrice(stockMinPrice);
                        lowRecord.setRecordPrice(Float.valueOf(result[3]));
                        lowRecord.setRecordTime(new Date());
//                        检查趋势暂时不做，暂时想不到
                        lowRecord.setTrend((short) 0);
                        stockMapper.insertLowRecord(lowRecord);
                    } else {
                        continue;
                    }
                } catch (ParseException px) {
                    px.printStackTrace();
                }
            }
        }
        //                获取全部没有发邮件的
        List<LowRecord> lowList = new ArrayList<LowRecord>();
        lowList = stockMapper.getAllLowRecordNotSend();
        List<String> mailContent = new ArrayList<String>();
        for(LowRecord low : lowList){
            String category = "";
//                    1 股票 2基金 3黄金 4期货
            switch (low.getCategory()){
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
            String content = "购买名称："+low.getStockName()+",编号："+low.getStockNum()+",类别："+category+","+low.getRecordDay()+"天最低价："+low.getMinPrice()+
                    ",当前记录价："+low.getRecordPrice()+"\n";
            mailContent.add(content);
        }
        if(mailContent.size()>0) {
            MailUtils.sendSimpleMail(sender, "953712390@qq.com", "买", mailContent.toString());
            stockMapper.updateLowRecordIsSend();
        }
    }

    @Override
    public List<BuySellRecord> getAllBuyRecord() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();
        try {
            nowDate = sDateFormat.parse(sDateFormat.format(new Date()));
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return stockMapper.getAllNowBuySellRecord(nowDate);
    }

    public String[] getStockNowPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("list", stockNum);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(",");
        return result;
    }
}