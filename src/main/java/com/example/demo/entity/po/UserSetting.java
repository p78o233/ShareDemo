package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2020/7/8
 */
//用户设置表
public class UserSetting {
    private Integer id;
    private int userId;
    private String setVal;
    private int setCate;

    @Override
    public String toString() {
        return "UserSetting{" +
                "id=" + id +
                ", userId=" + userId +
                ", setVal='" + setVal + '\'' +
                ", setCate=" + setCate +
                '}';
    }

    public UserSetting() {
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

    public String getSetVal() {
        return setVal;
    }

    public void setSetVal(String setVal) {
        this.setVal = setVal;
    }

    public int getSetCate() {
        return setCate;
    }

    public void setSetCate(int setCate) {
        this.setCate = setCate;
    }

    public UserSetting(Integer id, int userId, String setVal, int setCate) {
        this.id = id;
        this.userId = userId;
        this.setVal = setVal;
        this.setCate = setCate;
    }
}
