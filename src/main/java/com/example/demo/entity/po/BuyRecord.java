package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2020/5/15
 */

import java.util.Date;

//买入记录表
public class BuyRecord {
    private Integer id;
    private float buyPrice;
    private Date buyTime;
    private String stockNum;
    private String stockName;
    private int category;
    private int stockId;
    private int buyNum;
    private int userId;
    private int isdel;

    @Override
    public String toString() {
        return "BuyRecord{" +
                "id=" + id +
                ", buyPrice=" + buyPrice +
                ", buyTime=" + buyTime +
                ", stockNum='" + stockNum + '\'' +
                ", stockName='" + stockName + '\'' +
                ", category=" + category +
                ", stockId=" + stockId +
                ", buyNum=" + buyNum +
                ", userId=" + userId +
                ", isdel=" + isdel +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
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

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public BuyRecord() {
    }

    public BuyRecord(Integer id, float buyPrice, Date buyTime, String stockNum, String stockName, int category, int stockId, int buyNum, int userId, int isdel) {
        this.id = id;
        this.buyPrice = buyPrice;
        this.buyTime = buyTime;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.category = category;
        this.stockId = stockId;
        this.buyNum = buyNum;
        this.userId = userId;
        this.isdel = isdel;
    }
}
