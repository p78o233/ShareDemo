package com.example.demo.entity.vo;/*
 * @author p78o2
 * @date 2019/9/11
 */

public class StockPriceVo {
    private String stockName;
    private String stockNum;
    private float heightPriceHis;
    private float nowPrice;
    private float lowPriceHis;
    private int dayNums;

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

    public StockPriceVo(String stockName, String stockNum, float heightPriceHis, float nowPrice, float lowPriceHis, int dayNums) {
        this.stockName = stockName;
        this.stockNum = stockNum;
        this.heightPriceHis = heightPriceHis;
        this.nowPrice = nowPrice;
        this.lowPriceHis = lowPriceHis;
        this.dayNums = dayNums;
    }
}
