package com.example.demo.service.impl;/*
 * @author p78o2
 * @date 2020/7/13
 */

import com.example.demo.entity.po.Stock;
import com.example.demo.entity.po.StockRecord;
import com.example.demo.entity.vo.StockAvgVo;
import com.example.demo.entity.vo.StockPriceVo;
import com.example.demo.mapper.NowMapper;
import com.example.demo.service.NowService;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NowServiceImpl implements NowService {
    @Autowired
    private NowMapper nowMapper;

    @Override
    public List<StockPriceVo> checkNowPrice(int userId, int isWeitht, List<String> stockNums) {
        List<StockPriceVo> stockPriceVoList = new ArrayList<StockPriceVo>();
        if (stockNums.size() == 0) {
            if(isWeitht == 0) {
                stockNums = nowMapper.getAllStockNums(userId);
            }else{
                stockNums = nowMapper.getAllStockNumsByWeight(userId);
            }
        }
        for (String stockNum : stockNums) {
            StockPriceVo stockPriceVo = new StockPriceVo();
            stockPriceVo = nowMapper.getMaxHisHighLowPrice(stockNum);
//            try {
            if (stockPriceVo == null) {
                stockPriceVo = new StockPriceVo();
            }
//                昨天的记录
            StockRecord yesterdayRecord = nowMapper.getYesterdayRecord(stockNum);
            String result[] = getStockNowPrice(stockNum);
            if (yesterdayRecord != null) {
                DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String p = decimalFormat.format(((Float.valueOf(result[3]) / Float.valueOf(yesterdayRecord.getEndPrice())) - 1) * 100);//format 返回的是字符串
                stockPriceVo.setRate(p + "%");
            }
            stockPriceVo.setNowPrice(Float.valueOf(result[3]));
            if (nowMapper.getLowestRecord(stockNum) != null) {
                nowMapper.setRankNum();
                stockPriceVo.setLowDays(stockPriceVo.getDayNums() - nowMapper.getLowPriceRecordDay(stockNum));
                nowMapper.setRankNum();
                stockPriceVo.setHeighDays(stockPriceVo.getDayNums() - nowMapper.getHeightPriceRecordDay(stockNum));
            }
//            获取近10天以及20的最大最小值
            if (stockPriceVo.getDayNums() > 20) {
//                取20天最小值,最大值
//                stockPriceVo.setLastestTwenHeight(stockMapper.getStockLastestHigh(stockNum, stockPriceVo.getDayNums() - 20, 20));
//                stockPriceVo.setLastestTwenLow(stockMapper.getStockLastestlow(stockNum, stockPriceVo.getDayNums() - 20, 20));
                stockPriceVo.setLastestTwenHeight(nowMapper.getStockLastestHigh(stockNum, 0, 20));
                stockPriceVo.setLastestTwenLow(nowMapper.getStockLastestlow(stockNum, 0, 20));
            } else {
                stockPriceVo.setLastestTwenHeight(null);
                stockPriceVo.setLastestTwenLow(null);
            }
            if (stockPriceVo.getDayNums() > 10) {
//                取10天最小值，最大值
//                stockPriceVo.setLastestTenHeight(stockMapper.getStockLastestHigh(stockNum, stockPriceVo.getDayNums() - 10, 10));
//                stockPriceVo.setLastestTenLow(stockMapper.getStockLastestlow(stockNum, stockPriceVo.getDayNums() - 10, 10));
                stockPriceVo.setLastestTenHeight(nowMapper.getStockLastestHigh(stockNum, 0, 10));
                stockPriceVo.setLastestTenLow(nowMapper.getStockLastestlow(stockNum, 0, 10));
            } else {
                stockPriceVo.setLastestTenHeight(null);
                stockPriceVo.setLastestTenLow(null);
            }

//            获取当前价格在近段时间内处于什么位置 1、极低  2、偏低 3、较低 4、中 5、较高 6、偏高 7、极高
            if(stockPriceVo.getDayNums()>20){
                stockPriceVo.setNowStatus(getNowStatus(stockPriceVo.getLastestTwenHeight(),stockPriceVo.getLastestTwenLow(),stockPriceVo.getNowPrice()));
            }else if(stockPriceVo.getDayNums()>10){
                stockPriceVo.setNowStatus(getNowStatus(stockPriceVo.getLastestTenHeight(),stockPriceVo.getLastestTenLow(),stockPriceVo.getNowPrice()));
            }else{
                Float stockMinPrice = nowMapper.getLatestLowestPrice(stockPriceVo.getStockNum(),10);
                Float stockMaxPrice = nowMapper.getLatestHightPrice(stockPriceVo.getStockNum(),10);
                stockPriceVo.setNowStatus(getNowStatus(stockMaxPrice,stockMinPrice,stockPriceVo.getNowPrice()));
            }

            if (yesterdayRecord != null) {
                stockPriceVo.setYesterdayPrice(yesterdayRecord.getEndPrice());
                if (yesterdayRecord.getBeginPrice() > yesterdayRecord.getEndPrice())
                    stockPriceVo.setStauts("跌");
                else if (yesterdayRecord.getBeginPrice() < yesterdayRecord.getEndPrice())
                    stockPriceVo.setStauts("涨");
                else
                    stockPriceVo.setStauts("平");
            } else {
                stockPriceVo.setYesterdayPrice(0.0f);
                stockPriceVo.setStauts("未知");
            }


//                        获取平均值
            stockPriceVo = getAvg(stockPriceVo);
            stockPriceVoList.add(stockPriceVo);
//            } catch (Exception e) {
//                System.out.println(stockNum);
//            }

        }
        return stockPriceVoList;
    }

    //    单个查询
    public String[] getStockNowPrice(String stockNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("list", stockNum);
        String resultStr = HttpUtils.get("http://hq.sinajs.cn", params);
        String result[] = resultStr.split(",");
        return result;
    }


    public StockPriceVo getAvg(StockPriceVo stockPriceVo) {
        List<StockRecord> stockRecords = new ArrayList<>();
        stockRecords = nowMapper.getHistoryPrice(stockPriceVo.getStockNum());
//        连续升当前id
        int raiseId = -1;
        List<Integer> raiseList = new ArrayList<>();
        List<Integer> raiseDays = new ArrayList<>();
//        连续跌当前id
        int dropId = -1;
        List<Integer> dropList = new ArrayList<>();
        List<Integer> dropDays = new ArrayList<>();
//        连续的天数
        int continuity = 0;
//        上一个是什么
        int flag = 0;
        for (int i = 0; i < stockRecords.size(); i++) {
            if (stockRecords.get(i).getFlag() == 1) {
                if (flag == 1) {
                    raiseId = i;
                    continuity++;
                } else {
                    if (continuity > 1) {
                        if (dropId != -1) {
                            dropList.add(dropId);
                        }
                    }
                    raiseId = i;
                    dropDays.add(continuity);
                    continuity = 1;
                }
                flag = 1;
            } else if (stockRecords.get(i).getFlag() == -1) {
                if (flag == -1) {
                    dropId = i;
                    continuity++;
                } else {
                    if (continuity > 1) {
                        if (raiseId > -1) {
                            raiseList.add(raiseId);
                        }
                    }
                    dropId = i;
                    raiseDays.add(continuity);
                    continuity = 1;
                }
                flag = -1;
            }
        }
        if (flag == 1 && continuity > 1) {
            raiseList.add(raiseId);
            raiseDays.add(continuity);
        } else if (flag == -1 && continuity > 1) {
            dropList.add(dropId);
            dropDays.add(continuity);
        }
//        遍历涨跌平均间隔天数
        if (raiseList.size() > 1) {
            float raiseSum = 0.0f;
            for (int i = 1; i < raiseList.size(); i++) {
                raiseSum += raiseList.get(i) - raiseList.get(i - 1);
            }
            stockPriceVo.setAvgRaiseCycle(raiseSum / (raiseList.size() - 1));
        } else {
            stockPriceVo.setAvgRaiseCycle(null);
        }
        if (dropList.size() > 1) {
            float dropSum = 0.0f;
            for (int i = 1; i < dropList.size(); i++) {
                dropSum += dropList.get(i) - dropList.get(i - 1);
            }
            stockPriceVo.setAvgDropCycle(dropSum / (dropList.size() - 1));
        } else {
            stockPriceVo.setAvgDropCycle(null);
        }
//        遍历涨跌平均连续天数
        if (raiseDays.size() > 0) {
            float raiseDaySum = 0.0f;
            for (int i = 0; i < raiseDays.size(); i++) {
                raiseDaySum += raiseDays.get(i);
            }
            stockPriceVo.setAvgRaise(raiseDaySum / raiseDays.size());
        } else {
            stockPriceVo.setAvgRaise(null);
        }
        if (dropDays.size() > 0) {
            float dropDaySum = 0.0f;
            for (int i = 0; i < dropDays.size(); i++) {
                dropDaySum += dropDays.get(i);
            }
            stockPriceVo.setAvgDrop(dropDaySum / raiseDays.size());
        } else {
            stockPriceVo.setAvgDrop(null);
        }
        stockPriceVo.setLastDays(continuity);
        return stockPriceVo;
    }

    //    返回当前价格状态
    public String getNowStatus(Float hisHigh,Float hisLow,float nowPrice){
        String result = "";
        if(hisHigh!=null&&hisLow!=null) {
            if (nowPrice < hisLow)
                result = "极低";
            else if (nowPrice > hisHigh)
                result = "极高";
            else if ((nowPrice / hisLow) - 1 > 0.8) {
                result = "偏高";
            } else if ((nowPrice / hisLow) - 1 > 0.6) {
                result = "较高";
            } else if ((nowPrice / hisLow) - 1 > 0.45) {
                result = "中";
            } else if ((nowPrice / hisLow) - 1 > 0.25) {
                result = "较低";
            } else if ((nowPrice / hisLow) - 1 > 0) {
                result = "偏低";
            }
        }
        return result;
    }

    @Override
    public List<StockAvgVo> getStockAvg(List<String> stockNums) {
        if(stockNums.size()==0){
//            查询全部
            stockNums = nowMapper.getAllStockNum();
        }
        List<StockAvgVo> vos = new ArrayList<>();
        for(String stockNum : stockNums){
            StockAvgVo vo = new StockAvgVo();
            List<Float> endPriceList = new ArrayList<>();
            endPriceList = nowMapper.getAllEndPrice(stockNum);
            if(endPriceList.size()!=0){
                vo.setStockNum(stockNum);
                vo.setStockName(nowMapper.getStockName(stockNum));
                vo.setHisAvg(getAvg(endPriceList));
                vo.setHisVariance(variance(endPriceList));
                vo.setFiveAvg(getAvg(nowMapper.getPartEndPrice(stockNum,5)));
                vo.setFiveVariance(variance(nowMapper.getPartEndPrice(stockNum,5)));
                vo.setTenAvg(getAvg(nowMapper.getPartEndPrice(stockNum,10)));
                vo.setTenVariance(variance(nowMapper.getPartEndPrice(stockNum,10)));
                vo.setTwentyAvg(getAvg(nowMapper.getPartEndPrice(stockNum,20)));
                vo.setTwentyVariance(variance(nowMapper.getPartEndPrice(stockNum,20)));
                vos.add(vo);
            }
        }
        return vos;
    }
    public float getAvg(List<Float> x){
//        平均值函数
        int m=x.size();
        float sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x.get(i);
        }
        return sum/m;//求平均值
    }
    public float variance(List<Float> x) {
//        方差函数
        int m=x.size();
        float sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x.get(i);
        }
        float dAve=sum/m;//求平均值
        float dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x.get(i)-dAve)*(x.get(i)-dAve);
        }
        return dVar/m;
    }

    @Override
    public void get(List<String> stockNums) {
//        获取最近20的记录状况
        if(stockNums.size() == 0){
//            获取全部
            stockNums = nowMapper.getAllStock();
        }
        for(String stockNum : stockNums){
            List<StockRecord> stockRecords = new ArrayList<>();
            stockRecords = nowMapper.getStockRecordByStockNum(stockNum);
            int raiseSeries = 0;
            int dropSeries = 0;
            for(int i = 0;i<stockRecords.size();i++){

            }
        }
    }
}
