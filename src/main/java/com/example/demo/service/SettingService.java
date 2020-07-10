package com.example.demo.service;/*
 * @author p78o2
 * @date 2020/7/8
 */

import com.example.demo.callback.PageInfo;
import com.example.demo.entity.po.BuySellNotice;
import com.example.demo.entity.po.UserSetting;

import java.util.List;

public interface SettingService {
    public List<UserSetting> getUserBaseSetting(int userId);

    public int updateUserBaseSetting(UserSetting userSetting);

    public PageInfo<BuySellNotice> getBuySellNotice(int userId,String stockNum,String stockName,int cate,int page,int pageSize);

    public int ioeBuySellNotice(BuySellNotice buySellNotice);

    public int deleteBuySellNotice(int id);
}
