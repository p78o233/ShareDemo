package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2020/1/10
 */

public class TagBuySell {
    private Integer id;
    private String stockNum;
    private String stockName;
    private int category;
    private boolean flag;
    private boolean isSend;
    private float tagPrice;

    public TagBuySell() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public float getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(float tagPrice) {
        this.tagPrice = tagPrice;
    }

    public TagBuySell(Integer id, String stockNum, String stockName, int category, boolean flag, boolean isSend, float tagPrice) {
        this.id = id;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.category = category;
        this.flag = flag;
        this.isSend = isSend;
        this.tagPrice = tagPrice;
    }
}
