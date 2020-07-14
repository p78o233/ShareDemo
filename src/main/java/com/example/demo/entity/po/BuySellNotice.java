package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2020/7/8
 */

import java.util.Date;

//买入卖出提示表
public class BuySellNotice {
    private Integer id;
    private String stockNum;
    private String stockName;
    private int userId;
    private float price;
    private int cate;
    private String content;
    private Date createTime;
    private Date modifyTime;
    private int isdel;
    private int isSend;

    public BuySellNotice() {
    }


    @Override
    public String toString() {
        return "BuySellNotice{" +
                "id=" + id +
                ", stockNum='" + stockNum + '\'' +
                ", stockName='" + stockName + '\'' +
                ", userId=" + userId +
                ", price=" + price +
                ", cate=" + cate +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", isdel=" + isdel +
                ", isSend=" + isSend +
                '}';
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public BuySellNotice(Integer id, String stockNum, String stockName, int userId, float price, int cate, String content, Date createTime, Date modifyTime, int isdel, int isSend) {
        this.id = id;
        this.stockNum = stockNum;
        this.stockName = stockName;
        this.userId = userId;
        this.price = price;
        this.cate = cate;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.isdel = isdel;
        this.isSend = isSend;
    }
}
