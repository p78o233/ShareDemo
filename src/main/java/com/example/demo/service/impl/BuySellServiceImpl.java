package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2020/5/15
 */

import com.example.demo.callback.PageInfo;
import com.example.demo.entity.po.BuyRecord;
import com.example.demo.entity.po.SellRecord;
import com.example.demo.entity.po.Stock;
import com.example.demo.entity.vo.BuySellRecordVo;
import com.example.demo.mapper.BuySellMapper;
import com.example.demo.mapper.GobalMapper;
import com.example.demo.service.BuySellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BuySellServiceImpl implements BuySellService {
    @Autowired
    private BuySellMapper buySellMapper;
    @Autowired
    private GobalMapper gobalMapper;

    @Override
    public PageInfo<BuySellRecordVo> getBuySellRecordList(int userId, String stockNum, Date beginTime, Date endTime, int page, int pageSize) {
//        根据筛选条件获取买入记录
        stockNum = "".equals(stockNum) ? null : stockNum;
        beginTime = beginTime == null ? null : beginTime;
        endTime = endTime == null ? null : endTime;
        int count = 0;
        count = buySellMapper.countBuyRecord(userId, stockNum, beginTime, endTime);
        int start = (page - 1) * pageSize;
        List<BuyRecord> buyRecords = new ArrayList<>();
        buyRecords = buySellMapper.pageBuyRecord(userId, stockNum, beginTime, endTime, start, pageSize);
//        获取买入记录关联的卖出记录
        List<BuySellRecordVo> voList = new ArrayList<>();
        for (int i = 0; i < buyRecords.size(); i++) {
            List<SellRecord> sellRecordList = new ArrayList<>();
            sellRecordList = buySellMapper.getSellRecordList(buyRecords.get(i).getId());
            BuySellRecordVo vo = new BuySellRecordVo();
            vo.setBuyRecord(buyRecords.get(i));
            vo.setSellRecordList(sellRecordList);
            voList.add(vo);
        }
        PageInfo<BuySellRecordVo> pageInfo = new PageInfo<>();
        pageInfo.setCount(count);
        pageInfo.setList(voList);
        return pageInfo;
    }

    @Override
    public int ioeBuyRecord(BuyRecord buyRecord) {
        if (buyRecord.getId() == null) {
//            新增
            Stock stock = new Stock();
            stock = gobalMapper.getStockDetailByStockNum(buyRecord.getStockNum());
            if (stock == null)
//                股票代码错误或需要新增观察数据
                return 3;
            else {
                buyRecord.setStockId(stock.getId());
                buyRecord.setStockName(stock.getStockName());
                if (buySellMapper.insertBuyRecord(buyRecord) > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }

        } else {
//            修改
            if (buySellMapper.countSellRecord(buyRecord.getId()) > 0) {
                List<SellRecord> sellRecords = new ArrayList<>();
                sellRecords = buySellMapper.getSellRecordList(buyRecord.getId());
                for (SellRecord sellRecord : sellRecords) {
                    sellRecord.setProfitAndLoss((sellRecord.getSellPrice() - buyRecord.getBuyPrice()) * sellRecord.getSellNum());
                    buySellMapper.updateSellRecord(sellRecord);
                }
            }
            if (buySellMapper.updateBuyRecord(buyRecord) > 0)
                return 1;
            return 0;
        }
    }

    @Override
    @Transactional
    public int deleteBuyRecord(int id) {
        if (buySellMapper.countSellRecord(id) > 0) {
            if (buySellMapper.deleteBuyRecord(id) > 0 && buySellMapper.deleteSellRecordByBuyId(id) > 0)
                return 1;
            return 0;
        } else {
            if (buySellMapper.deleteBuyRecord(id) > 0)
                return 1;
            return 0;
        }
    }

    @Override
    public int ioeSellRecord(SellRecord sellRecord) {
        BuyRecord buyRecord = new BuyRecord();
        buyRecord = buySellMapper.getBuyRecordDetailById(sellRecord.getBuyId());
//        计算利润
        sellRecord.setProfitAndLoss((sellRecord.getSellPrice() - buyRecord.getBuyPrice()) * sellRecord.getSellNum());
        if (sellRecord.getId() == null) {
//            新增
            sellRecord.setStockId(buyRecord.getStockId());
            sellRecord.setStockName(buyRecord.getStockName());
            sellRecord.setStockNum(buyRecord.getStockNum());
            if (buySellMapper.insertSellRecord(sellRecord) > 0)
                return 1;
            return 0;
        } else {
//            修改
            if (buySellMapper.updateSellRecord(sellRecord) > 0)
                return 1;
            return 0;
        }
    }

    @Override
    public int deleteSellRecord(int id) {
        if (buySellMapper.deleteSellRecord(id) > 0)
            return 1;
        return 0;
    }
}
