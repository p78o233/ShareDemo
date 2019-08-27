package com.example.demo.entity;/*
 * @author p78o2
 * @date 2019/8/27
 */

import java.util.Date;

public class Stock {
    private Integer id;
    private String stockNum;
    private String stockName;
    private Date createTime;
    private int category;

    public Stock() {
    }

    public Stock(Integer id, String stockNum, String stockName, Date createTime, int category) {

        this.id = id;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.createTime = createTime;
        this.category = category;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
