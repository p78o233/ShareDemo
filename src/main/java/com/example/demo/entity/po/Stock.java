package com.example.demo.entity.po;/*
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
    private int weight;
    private int userId;

    public Stock() {
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Stock(Integer id, String stockNum, String stockName, Date createTime, int category, int weight, int userId) {
        this.id = id;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.createTime = createTime;
        this.category = category;
        this.weight = weight;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
