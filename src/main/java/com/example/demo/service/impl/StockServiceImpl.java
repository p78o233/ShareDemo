package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.BuySellRecord;
import com.example.demo.entity.Stock;
import com.example.demo.entity.StockRecord;
import com.example.demo.mapper.StockMapper;
import com.example.demo.service.StockService;
import com.example.demo.utils.HttpUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
            HashMap<String, String> params = new HashMap<>();
            params.put("list", stock.getStockNum());
            String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
            String result[] = resultStr.split(",");
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
        buySellRecord.setProfitOrLoss(buySellRecord.getSellPrice() - buySellRecordOld.getBuyPrice());
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
        for (BuySellRecord buySellRecord : buyRecords) {
            HashMap<String, String> params = new HashMap<>();
            params.put("list", buySellRecord.getStockNum());
            String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
            String result[] = resultStr.split(",");
            if (Float.valueOf(result[3]) / buySellRecord.getBuyPrice() > 1.015) {
//                超过1.5%
            } else if (Float.valueOf(result[3]) / buySellRecord.getBuyPrice() < 0.98) {
//                跌超过2%
            } else {
                continue;
            }
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
        List<String> stockNums = new ArrayList<>();
        for(int i=0;i<dayList.size();i++){
            stockNums = stockMapper.getStockNums(dayList.get(i));
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
}
