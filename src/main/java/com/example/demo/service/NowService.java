package com.example.demo.service;/*
 * @author p78o2
 * @date 2020/7/13
 */

import com.example.demo.entity.vo.StockPriceVo;

import java.util.List;

public interface NowService {
    //    查询当前价格,以及记录最低价和记录最高价
    public List<StockPriceVo> checkNowPrice(int userId, int isWeitht, List<String> stockNums);
}
