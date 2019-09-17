package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2019/8/29
 */

import java.util.Date;

public class LowRecord {
    private Integer id;
    private int stockId;
    private String stockNum;
    private String stockName;
    private int category;
    private int recordDay;
    private float minPrice;
    private float recordPrice;
    private Date recordTime;
    private boolean isSend;
    private short trend;
    private float lowHis;
    private int dayBefore;

    public LowRecord() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(int recordDay) {
        this.recordDay = recordDay;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getRecordPrice() {
        return recordPrice;
    }

    public void setRecordPrice(float recordPrice) {
        this.recordPrice = recordPrice;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public short getTrend() {
        return trend;
    }

    public void setTrend(short trend) {
        this.trend = trend;
    }

    public float getLowHis() {
        return lowHis;
    }

    public void setLowHis(float lowHis) {
        this.lowHis = lowHis;
    }

    public int getDayBefore() {
        return dayBefore;
    }

    public void setDayBefore(int dayBefore) {
        this.dayBefore = dayBefore;
    }

    public LowRecord(Integer id, int stockId, String stockNum, String stockName, int category, int recordDay, float minPrice, float recordPrice, Date recordTime, boolean isSend, short trend, float lowHis, int dayBefore) {
        this.id = id;
        this.stockId = stockId;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.category = category;
        this.recordDay = recordDay;
        this.minPrice = minPrice;
        this.recordPrice = recordPrice;
        this.recordTime = recordTime;
        this.isSend = isSend;
        this.trend = trend;
        this.lowHis = lowHis;
        this.dayBefore = dayBefore;
    }
}
