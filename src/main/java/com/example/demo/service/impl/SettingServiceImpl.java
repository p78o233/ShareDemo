package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2020/7/8
 */

import com.example.demo.callback.PageInfo;
import com.example.demo.entity.po.BuySellNotice;
import com.example.demo.entity.po.UserSetting;
import com.example.demo.mapper.SettingMapper;
import com.example.demo.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingMapper settingMapper;

    @Override
    public List<UserSetting> getUserBaseSetting(int userId) {
        return settingMapper.getUserBaseSetting(userId);
    }

    @Override
    public int updateUserBaseSetting(UserSetting userSetting) {
        if(settingMapper.updateUserBaseSetting(userSetting)>0)
            return 1;
        return 0;
    }

    @Override
    public PageInfo<BuySellNotice> getBuySellNotice(int userId, String stockNum, String stockName, int cate, int page, int pageSize) {
        int start = (page-1)*pageSize;
        int count = settingMapper.getBuySellNoticeCount(userId,stockNum,stockName,cate);
        List<BuySellNotice> list = new ArrayList<>();
        list = settingMapper.getBuySellNoticePage(userId,stockNum,stockName,cate,start,pageSize);
        PageInfo<BuySellNotice> info = new PageInfo<>();
        info.setCount(count);
        info.setList(list);
        return info;
    }

    @Override
    public int ioeBuySellNotice(BuySellNotice buySellNotice) {
        if(buySellNotice.getId()==null){
//            新增
            buySellNotice.setCreateTime(new Date());
            if(settingMapper.insertIntoBuySellNotice(buySellNotice)>0)
                return 1;
            return 0;
        }else{
//            修改
            buySellNotice.setModifyTime(new Date());
            if(settingMapper.updateBuySellNotice(buySellNotice)>0)
                return 1;
            return 0;
        }
    }

    @Override
    public int deleteBuySellNotice(int id) {
        if(settingMapper.deleteBuySellNotice(id)>0)
            return 1;
        return 0;
    }
}
