package com.example.demo.service;/*
 * @author p78o2
 * @date 2020/5/15
 */

import com.example.demo.callback.PageInfo;
import com.example.demo.entity.po.BuyRecord;
import com.example.demo.entity.po.SellRecord;
import com.example.demo.entity.vo.BuySellRecordVo;

import java.util.Date;

public interface BuySellService {
//    分页分条件获取买入卖出记录数据
    PageInfo<BuySellRecordVo> getBuySellRecordList(int userId, String stockNum, Date beginTime,Date endTime,int page,int pageSize);
//    新增修改购买记录
    int ioeBuyRecord(BuyRecord buyRecord);
//    删除买入记录
    int deleteBuyRecord(int id);
//    新增或者修改卖出记录
    int ioeSellRecord(SellRecord sellRecord);
//    删除卖出记录
    int deleteSellRecord(int id);
}
