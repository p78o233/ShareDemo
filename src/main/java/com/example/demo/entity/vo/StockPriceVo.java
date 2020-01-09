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
//    上个交易日的价格
    private Float yesterdayPrice;
//    上个交易日涨跌情况
    private String stauts;
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
//    当前涨跌幅度
    private String rate;
//    平均涨周期
    private Float avgRaiseCycle;
//    平均跌周期
    private Float avgDropCycle;
    //    连续涨天数平均值
    private Float avgRaise;
    //    连续跌天数平均值
    private Float avgDrop;
//    已经持续当前状况多少天了，看stauts得出当前状况
    private int lastDays;
//    当前价格在近段时间内处于什么位置 1、极低  2、偏低 3、较低 4、中 5、较高 6、偏高 7、极高
    private String nowStatus;
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


    public Float getYesterdayPrice() {
        return yesterdayPrice;
    }

    public void setYesterdayPrice(Float yesterdayPrice) {
        this.yesterdayPrice = yesterdayPrice;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Float getAvgRaiseCycle() {
        return avgRaiseCycle;
    }

    public void setAvgRaiseCycle(Float avgRaiseCycle) {
        this.avgRaiseCycle = avgRaiseCycle;
    }

    public Float getAvgDropCycle() {
        return avgDropCycle;
    }

    public void setAvgDropCycle(Float avgDropCycle) {
        this.avgDropCycle = avgDropCycle;
    }

    public Float getAvgRaise() {
        return avgRaise;
    }

    public void setAvgRaise(Float avgRaise) {
        this.avgRaise = avgRaise;
    }

    public Float getAvgDrop() {
        return avgDrop;
    }

    public void setAvgDrop(Float avgDrop) {
        this.avgDrop = avgDrop;
    }

    public int getLastDays() {
        return lastDays;
    }

    public void setLastDays(int lastDays) {
        this.lastDays = lastDays;
    }

    public String getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(String nowStatus) {
        this.nowStatus = nowStatus;
    }

    public StockPriceVo(String stockName, String stockNum, float heightPriceHis, Float lastestTwenHeight, Float lastestTenHeight, Float yesterdayPrice, String stauts, float nowPrice, Float lastestTenLow, Float lastestTwenLow, float lowPriceHis, int dayNums, int heighDays, int lowDays, String rate, Float avgRaiseCycle, Float avgDropCycle, Float avgRaise, Float avgDrop, int lastDays, String nowStatus) {
        this.stockName = stockName;
        this.stockNum = stockNum;
        this.heightPriceHis = heightPriceHis;
        this.lastestTwenHeight = lastestTwenHeight;
        this.lastestTenHeight = lastestTenHeight;
        this.yesterdayPrice = yesterdayPrice;
        this.stauts = stauts;
        this.nowPrice = nowPrice;
        this.lastestTenLow = lastestTenLow;
        this.lastestTwenLow = lastestTwenLow;
        this.lowPriceHis = lowPriceHis;
        this.dayNums = dayNums;
        this.heighDays = heighDays;
        this.lowDays = lowDays;
        this.rate = rate;
        this.avgRaiseCycle = avgRaiseCycle;
        this.avgDropCycle = avgDropCycle;
        this.avgRaise = avgRaise;
        this.avgDrop = avgDrop;
        this.lastDays = lastDays;
        this.nowStatus = nowStatus;
    }
}
