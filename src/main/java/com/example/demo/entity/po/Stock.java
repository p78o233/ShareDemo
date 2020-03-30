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
    private int isdel;

    public Stock() {
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockNum='" + stockNum + '\'' +
                ", stockName='" + stockName + '\'' +
                ", createTime=" + createTime +
                ", category=" + category +
                ", weight=" + weight +
                ", userId=" + userId +
                ", isdel=" + isdel +
                '}';
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public Stock(Integer id, String stockNum, String stockName, Date createTime, int category, int weight, int userId, int isdel) {
        this.id = id;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.createTime = createTime;
        this.category = category;
        this.weight = weight;
        this.userId = userId;
        this.isdel = isdel;
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
