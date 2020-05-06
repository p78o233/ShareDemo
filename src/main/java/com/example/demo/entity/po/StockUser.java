package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2020/3/31
 */

import java.util.Date;
//股票用户关联表
public class StockUser {
    private Integer id;
    private int userId;
    private int stockId;
    private Date createTime;
    private boolean isdel;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private int weight;


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

    public boolean isIsdel() {
        return isdel;
    }

    public void setIsdel(boolean isdel) {
        this.isdel = isdel;
    }

    public StockUser(Integer id, int userId, int stockId, Date createTime, boolean isdel, int weight) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.createTime = createTime;
        this.isdel = isdel;
        this.weight = weight;
    }
}
