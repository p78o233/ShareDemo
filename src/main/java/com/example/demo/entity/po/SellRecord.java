package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2020/4/2
 */

import java.util.Date;
//卖出记录
public class SellRecord {
    private Integer id;
    private int buyId;
    private float sellPrice;
    private float profitAndLoss;
    private Date sellTime;
    private String stockNum;
    private String stockName;
    private int category;
    private int stockId;
    private int sellNum;
    private int userId;
    private int isdel;

    @Override
    public String toString() {
        return "SellRecord{" +
                "id=" + id +
                ", buyId=" + buyId +
                ", sellPrice=" + sellPrice +
                ", profitAndLoss=" + profitAndLoss +
                ", sellTime=" + sellTime +
                ", stockNum='" + stockNum + '\'' +
                ", stockName='" + stockName + '\'' +
                ", category=" + category +
                ", stockId=" + stockId +
                ", sellNum=" + sellNum +
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

    public int getBuyId() {
        return buyId;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(float profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
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

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
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

    public SellRecord() {
    }

    public SellRecord(Integer id, int buyId, float sellPrice, float profitAndLoss, Date sellTime, String stockNum, String stockName, int category, int stockId, int sellNum, int userId, int isdel) {
        this.id = id;
        this.buyId = buyId;
        this.sellPrice = sellPrice;
        this.profitAndLoss = profitAndLoss;
        this.sellTime = sellTime;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.category = category;
        this.stockId = stockId;
        this.sellNum = sellNum;
        this.userId = userId;
        this.isdel = isdel;
    }
}
