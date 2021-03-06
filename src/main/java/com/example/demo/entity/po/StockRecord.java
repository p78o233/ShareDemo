package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2019/8/27
 */

import java.util.Date;

public class StockRecord {
    private Integer id;
    private float beginPrice;
    private float endPrice;
    private float highPrice;
    private float lowPrice;
    private Integer stockId;
    private String stockName;
    private int category;
    private String stockNum;
    private Date recordTime;
//    涨跌标志位 -1跌 0 平 1涨
    private int flag;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public StockRecord() {
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(float beginPrice) {
        this.beginPrice = beginPrice;
    }

    public float getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(float endPrice) {
        this.endPrice = endPrice;
    }

    public float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(float highPrice) {
        this.highPrice = highPrice;
    }

    public float getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(float lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
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

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getStockNum() {

        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public StockRecord(Integer id, float beginPrice, float endPrice, float highPrice, float lowPrice, Integer stockId, String stockName, int category, String stockNum, Date recordTime, int flag, int userId) {
        this.id = id;
        this.beginPrice = beginPrice;
        this.endPrice = endPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.stockId = stockId;
        this.stockName = stockName;
        this.category = category;
        this.stockNum = stockNum;
        this.recordTime = recordTime;
        this.flag = flag;
        this.userId = userId;
    }
}
