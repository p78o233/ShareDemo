package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.BuySellRecord;
import com.example.demo.entity.Stock;
import com.example.demo.entity.StockRecord;
import com.example.demo.mapper.StockMapper;
import com.example.demo.service.StockService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
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
    public boolean daylyRecord() throws IOException{
        List<Stock> list = new ArrayList<Stock>();
        list = stockMapper.getAllStock();
        for(Stock stock : list){
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://hq.sinajs.cn/list="+stock.getStockNum())
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();
            String resultStr = response.body().string();
            String result [] = resultStr.split(",");
            StockRecord sr = new StockRecord();
            sr.setBeginPrice(Float.valueOf(result[1]));
            sr.setEndPrice(Float.valueOf(result[3]));
            sr.setHighPrice(Float.valueOf(result[4]));
            sr.setLowPrice(Float.valueOf(result[5]));
            sr.setStockId(stock.getId());
            sr.setStockName(stock.getStockName());
            sr.setStockNum(stock.getStockNum());
            sr.setCategory(stock.getCategory());
            sr.setRecordTime(Date.valueOf(result[30]));
            stockMapper.daylyRecord(sr);
        }
        return true;
    }

    @Override
    public boolean insertBuyRecord(BuySellRecord buySellRecord) {
        return false;
    }

    @Override
    public boolean updateSellRecord(BuySellRecord buySellRecord) {
        return false;
    }

    @Override
    public void noticeSell() {

    }

    @Override
    public void noticeBuy() {

    }
}
