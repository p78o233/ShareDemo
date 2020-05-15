package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2020/5/15
 */

import com.example.demo.callback.PageInfo;
import com.example.demo.entity.po.BuyRecord;
import com.example.demo.entity.po.SellRecord;
import com.example.demo.entity.vo.BuySellRecordVo;
import com.example.demo.service.BuySellService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BuySellServiceImpl implements BuySellService {
    @Override
    public PageInfo<BuySellRecordVo> getBuySellRecordList(int userId, String stockNum, Date beginTime, Date endTime, int page, int pageSize) {
        return null;
    }

    @Override
    public int ioeBuyRecord(BuyRecord buyRecord) {
        return 0;
    }

    @Override
    public int deleteBuyRecord(int id) {
        return 0;
    }

    @Override
    public int ioeSellRecord(SellRecord sellRecord) {
        return 0;
    }

    @Override
    public int deleteSellRecord(int id) {
        return 0;
    }
}
