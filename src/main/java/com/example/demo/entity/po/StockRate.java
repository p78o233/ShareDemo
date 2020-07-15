package com.example.demo.entity.po;

import java.sql.Time;

/*
 * @author p78o2
 * @date 2020/7/15
 */
//当前时间涨跌幅表
public class StockRate {
    private Integer id;
    private int stockId;
    private int cate;
    private Time createTime;
    private float ratio;

    @Override
    public String toString() {
        return "StockRate{" +
                "id=" + id +
                ", stockId=" + stockId +
                ", cate=" + cate +
                ", createTime=" + createTime +
                ", ratio=" + ratio +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public StockRate() {
    }

    public StockRate(Integer id, int stockId, int cate, Time createTime, float ratio) {
        this.id = id;
        this.stockId = stockId;
        this.cate = cate;
        this.createTime = createTime;
        this.ratio = ratio;
    }
}
