package com.example.demo.entity.po;/*
 * @author p78o2
 * @date 2019/12/26
 */

public class User {
    private int id;
    private String account;
    private String pwd;
    private String userName;
    private String token;
    private int isdel;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                ", isdel=" + isdel +
                '}';
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }

    public User(int id, String account, String pwd, String userName, String token, int isdel) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.userName = userName;
        this.token = token;
        this.isdel = isdel;
    }
}
