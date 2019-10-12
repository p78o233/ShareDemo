package com.example.demo.entity.vo;/*
 * @author p78o2
 * @date 2019/9/11
 */

public class StockPriceVo {
    private String stockName;
    private String stockNum;
    private float heightPriceHis;
//    最近20天最大值
    private Float lastestTwenHeight;
//    最近10天最大值
    private Float lastestTenHeight;
    private float nowPrice;
//    最近10天最小值
    private Float lastestTenLow;
//    最近20天最小值
    private Float lastestTwenLow;
    private float lowPriceHis;
//    统计天数
    private int dayNums;
//    最高是多少天前
    private int heighDays;
//    最低是多少天前
    private int lowDays;
    public StockPriceVo() {
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public float getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(float nowPrice) {
        this.nowPrice = nowPrice;
    }

    public float getHeightPriceHis() {
        return heightPriceHis;
    }

    public void setHeightPriceHis(float heightPriceHis) {
        this.heightPriceHis = heightPriceHis;
    }

    public float getLowPriceHis() {
        return lowPriceHis;
    }

    public void setLowPriceHis(float lowPriceHis) {
        this.lowPriceHis = lowPriceHis;
    }

    public int getDayNums() {
        return dayNums;
    }

    public void setDayNums(int dayNums) {
        this.dayNums = dayNums;
    }

    public int getHeighDays() {
        return heighDays;
    }

    public void setHeighDays(int heighDays) {
        this.heighDays = heighDays;
    }

    public int getLowDays() {
        return lowDays;
    }

    public void setLowDays(int lowDays) {
        this.lowDays = lowDays;
    }

    public Float getLastestTwenHeight() {
        return lastestTwenHeight;
    }

    public void setLastestTwenHeight(Float lastestTwenHeight) {
        this.lastestTwenHeight = lastestTwenHeight;
    }

    public Float getLastestTenHeight() {
        return lastestTenHeight;
    }

    public void setLastestTenHeight(Float lastestTenHeight) {
        this.lastestTenHeight = lastestTenHeight;
    }

    public Float getLastestTenLow() {
        return lastestTenLow;
    }

    public void setLastestTenLow(Float lastestTenLow) {
        this.lastestTenLow = lastestTenLow;
    }

    public Float getLastestTwenLow() {
        return lastestTwenLow;
    }

    public void setLastestTwenLow(Float lastestTwenLow) {
        this.lastestTwenLow = lastestTwenLow;
    }

    public StockPriceVo(String stockName, String stockNum, float heightPriceHis, Float lastestTwenHeight, Float lastestTenHeight, float nowPrice, Float lastestTenLow, Float lastestTwenLow, float lowPriceHis, int dayNums, int heighDays, int lowDays) {
        this.stockName = stockName;
        this.stockNum = stockNum;
        this.heightPriceHis = heightPriceHis;
        this.lastestTwenHeight = lastestTwenHeight;
        this.lastestTenHeight = lastestTenHeight;
        this.nowPrice = nowPrice;
        this.lastestTenLow = lastestTenLow;
        this.lastestTwenLow = lastestTwenLow;
        this.lowPriceHis = lowPriceHis;
        this.dayNums = dayNums;
        this.heighDays = heighDays;
        this.lowDays = lowDays;
    }
}
