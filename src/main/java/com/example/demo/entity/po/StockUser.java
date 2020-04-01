package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2020/3/31
 */

import java.util.Date;

public class StockUser {
    private Integer id;
    private int userId;
    private int stockId;
    private Date createTime;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int isdel;
    private int weight;

    @Override
    public String toString() {
        return "StockUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", stockId=" + stockId +
                ", createTime=" + createTime +
                ", isdel=" + isdel +
                ", weight=" + weight +
                '}';
    }

    public StockUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public StockUser(Integer id, int userId, int stockId, Date createTime, int isdel, int weight) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.createTime = createTime;
        this.isdel = isdel;
        this.weight = weight;
    }
}
