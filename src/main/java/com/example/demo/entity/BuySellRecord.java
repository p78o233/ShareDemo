package com.example.demo.entity;/*
 * @author p78o2
 * @date 2019/8/27
 */

import java.util.Date;

public class BuySellRecord {
    private Integer id;
    private float buyPrice;
    private Date buyTime;
    private float sellPrice;
    private Date sellTime;
    private float profitOrLoss;
    private Integer stockId;
    private String stockName;
    private int category;
    private String stockNum;
    private boolean isfinish;
    private int buyNum;

    public BuySellRecord() {
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

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public float getProfitOrLoss() {
        return profitOrLoss;
    }

    public void setProfitOrLoss(float profitOrLoss) {
        this.profitOrLoss = profitOrLoss;
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

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public boolean isIsfinish() {
        return isfinish;
    }

    public void setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public BuySellRecord(Integer id, float buyPrice, Date buyTime, float sellPrice, Date sellTime, float profitOrLoss, Integer stockId, String stockName, int category, String stockNum, boolean isfinish, int buyNum) {
        this.id = id;
        this.buyPrice = buyPrice;
        this.buyTime = buyTime;
        this.sellPrice = sellPrice;
        this.sellTime = sellTime;
        this.profitOrLoss = profitOrLoss;
        this.stockId = stockId;
        this.stockName = stockName;
        this.category = category;
        this.stockNum = stockNum;
        this.isfinish = isfinish;
        this.buyNum = buyNum;
    }
}
