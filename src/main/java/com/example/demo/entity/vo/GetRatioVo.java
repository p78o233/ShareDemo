package com.example.demo.entity.vo;/*
 * @author p78o2
 * @date 2020/7/15
 */
//获取涨跌比率的返回值
public class GetRatioVo {
    private String time;
    private float ratio;

    @Override
    public String toString() {
        return "GetRatioVo{" +
                "time='" + time + '\'' +
                ", ratio=" + ratio +
                '}';
    }

    public GetRatioVo() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public GetRatioVo(String time, float ratio) {
        this.time = time;
        this.ratio = ratio;
    }
}
