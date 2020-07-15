package com.example.demo.service;/*
 * @author p78o2
 * @date 2020/7/10
 */

public interface TimmerService {
    //    提示买(定时任务)
    public void noticeBuy();
//    提示卖（定时任务）
    public void noticeSell();
//    每天收盘记录数据
    public void insertDaylyRecord();
//    定时观察上证深证指数升降银行权重
    public void updateBankWeight();
//    观察涨跌超过5%，邮件警告
    public void reminder();
//    定时任务，用户到达设置买卖的点提醒出售
    public void noticeTarget();
//    定时任务获取股票的最大涨跌幅出现的时间
    public void theGapEachDay();
}
