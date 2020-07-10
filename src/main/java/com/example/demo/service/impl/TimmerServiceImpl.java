package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2020/7/10
 */

import com.example.demo.entity.po.Stock;
import com.example.demo.entity.vo.UserMailContentVo;
import com.example.demo.mapper.TimmerMapper;
import com.example.demo.service.TimmerService;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TimmerServiceImpl implements TimmerService {
    @Autowired
    private TimmerMapper timmerMapper;

    //    单个查询
    public String[] getStockNowPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("list", stockNum);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(",");
        return result;
    }

    @Override
    public void noticeBuy() {
//        天数列表
        ArrayList<Integer> dayNums = new ArrayList<Integer>() {{
            add(5);
            add(10);
            add(20);
            add(50);
        }};
//        1、获取当前要查看的记录历表,查看所有系统中的用户
        List<Stock> stockList = new ArrayList<>();
        stockList = timmerMapper.getAllStock();
        List<UserMailContentVo> userMailContentVoList = new ArrayList<>();
        userMailContentVoList = timmerMapper.getAllUser();
        for(int i = 0;i<stockList.size();i++){
//            当前价格
            String result[] = getStockNowPrice(stockList.get(i).getStockNum());
            float nowPrice = Float.valueOf(result[3]);
//            查询天数列表最低价
            for(int dayNum : dayNums){
                if(nowPrice<timmerMapper.getLowestRectDay(stockList.get(i).getStockNum(),dayNum)){

                }
            }
        }
//        2、根据记录查询当前价格，并且与历史记录5、10、20、50天记录进行比较
//        3、如果低于历史价格则查询出相应信息，并查询有观察这个股票的人，并发送邮件

    }
}
