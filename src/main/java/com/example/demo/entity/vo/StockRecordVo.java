package com.example.demo.entity.vo;/*
 * @author p78o2
 * @date 2019/8/29
 */

public class StockRecordVo {
    private Integer stockId;
    private String stockName;
    private int category;
    private String stockNum;

    public StockRecordVo() {
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

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public StockRecordVo(Integer stockId, String stockName, int category, String stockNum) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.category = category;
        this.stockNum = stockNum;
    }
}
