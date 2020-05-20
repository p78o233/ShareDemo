package com.example.demo.entity.vo;/*
 * @author p78o2
 * @date 2020/5/15
 */

import com.example.demo.entity.po.BuyRecord;
import com.example.demo.entity.po.SellRecord;

import java.util.Date;
import java.util.List;

public class BuySellRecordVo extends BuyRecord {
    private List<SellRecord> sellRecordList;

    @Override
    public String toString() {
        return "BuySellRecordVo{" +
                "sellRecordList=" + sellRecordList +
                '}';
    }

    public List<SellRecord> getSellRecordList() {
        return sellRecordList;
    }

    public void setSellRecordList(List<SellRecord> sellRecordList) {
        this.sellRecordList = sellRecordList;
    }

    public BuySellRecordVo(List<SellRecord> sellRecordList) {
        this.sellRecordList = sellRecordList;
    }

    public BuySellRecordVo(Integer id, float buyPrice, Date buyTime, String stockNum, String stockName, int category, int stockId, int buyNum, int userId, int isdel, List<SellRecord> sellRecordList) {
        super(id, buyPrice, buyTime, stockNum, stockName, category, stockId, buyNum, userId, isdel);
        this.sellRecordList = sellRecordList;
    }
}
