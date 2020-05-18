package com.example.demo.entity.vo;/*
 * @author p78o2
 * @date 2020/5/15
 */

import com.example.demo.entity.po.BuyRecord;
import com.example.demo.entity.po.SellRecord;

import java.util.Date;
import java.util.List;

public class BuySellRecordVo  {
    private BuyRecord buyRecord;
    private List<SellRecord> sellRecordList;

    @Override
    public String toString() {
        return "BuySellRecordVo{" +
                "buyRecord=" + buyRecord +
                ", sellRecordList=" + sellRecordList +
                '}';
    }

    public BuySellRecordVo() {
    }

    public BuyRecord getBuyRecord() {
        return buyRecord;
    }

    public void setBuyRecord(BuyRecord buyRecord) {
        this.buyRecord = buyRecord;
    }

    public List<SellRecord> getSellRecordList() {
        return sellRecordList;
    }

    public void setSellRecordList(List<SellRecord> sellRecordList) {
        this.sellRecordList = sellRecordList;
    }

    public BuySellRecordVo(BuyRecord buyRecord, List<SellRecord> sellRecordList) {
        this.buyRecord = buyRecord;
        this.sellRecordList = sellRecordList;
    }
}
