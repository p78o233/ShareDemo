package com.example.demo.entity.vo;/*
 * @author p78o2
 * @date 2020/7/22
 */
//股票列表获取历史平均值，最近平均值，方差
public class StockAvgVo {
    private String stockNum;
    private String stockName;
//    历史平均值
    private float hisAvg;
//    近5天
    private float fiveAvg;
//    近10天
    private float tenAvg;
//    近20天
    private float twentyAvg;
//    方差
    private float hisVariance;
    private float fiveVariance;
    private float tenVariance;
    private float twentyVariance;

    @Override
    public String toString() {
        return "StockAvgVo{" +
                "stockNum='" + stockNum + '\'' +
                ", stockName='" + stockName + '\'' +
                ", hisAvg=" + hisAvg +
                ", fiveAvg=" + fiveAvg +
                ", tenAvg=" + tenAvg +
                ", twentyAvg=" + twentyAvg +
                ", hisVariance=" + hisVariance +
                ", fiveVariance=" + fiveVariance +
                ", tenVariance=" + tenVariance +
                ", twentyVariance=" + twentyVariance +
                '}';
    }

    public StockAvgVo() {
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

    public float getHisAvg() {
        return hisAvg;
    }

    public void setHisAvg(float hisAvg) {
        this.hisAvg = hisAvg;
    }

    public float getFiveAvg() {
        return fiveAvg;
    }

    public void setFiveAvg(float fiveAvg) {
        this.fiveAvg = fiveAvg;
    }

    public float getTenAvg() {
        return tenAvg;
    }

    public void setTenAvg(float tenAvg) {
        this.tenAvg = tenAvg;
    }

    public float getTwentyAvg() {
        return twentyAvg;
    }

    public void setTwentyAvg(float twentyAvg) {
        this.twentyAvg = twentyAvg;
    }

    public float getHisVariance() {
        return hisVariance;
    }

    public void setHisVariance(float hisVariance) {
        this.hisVariance = hisVariance;
    }

    public float getFiveVariance() {
        return fiveVariance;
    }

    public void setFiveVariance(float fiveVariance) {
        this.fiveVariance = fiveVariance;
    }

    public float getTenVariance() {
        return tenVariance;
    }

    public void setTenVariance(float tenVariance) {
        this.tenVariance = tenVariance;
    }

    public float getTwentyVariance() {
        return twentyVariance;
    }

    public void setTwentyVariance(float twentyVariance) {
        this.twentyVariance = twentyVariance;
    }

    public StockAvgVo(String stockNum, String stockName, float hisAvg, float fiveAvg, float tenAvg, float twentyAvg, float hisVariance, float fiveVariance, float tenVariance, float twentyVariance) {
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.hisAvg = hisAvg;
        this.fiveAvg = fiveAvg;
        this.tenAvg = tenAvg;
        this.twentyAvg = twentyAvg;
        this.hisVariance = hisVariance;
        this.fiveVariance = fiveVariance;
        this.tenVariance = tenVariance;
        this.twentyVariance = twentyVariance;
    }
}
