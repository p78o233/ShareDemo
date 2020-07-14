package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2020/7/13
 */

import com.example.demo.entity.po.StockRecord;
import com.example.demo.entity.vo.StockPriceVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NowMapper {

    @Select("select * from stock_record where stockNum = #{stockNum} order by recordTime asc")
    List<StockRecord> getHistoryPrice(@Param("stockNum")String stockNum);
    @Select("select stockNum from stock where id in (select stockId from stock_user where userId = #{userId} order by weight desc)")
    List<String> getAllStockNums(@Param("userId")int userId);
    @Select("select stockNum from stock where id in (select stockId from stock_user where userId = #{userId} and weight != 0 order by weight desc)")
    List<String> getAllStockNumsByWeight(@Param("userId")int userId);
    @Select("select stockName,stockNum,MAX(highPrice) as heightPriceHis,MIN(lowPrice) as lowPriceHis,count(1) as dayNums from stock_record where stockNum = #{stockNum} GROUP BY stockNum")
    StockPriceVo getMaxHisHighLowPrice(@Param("stockNum")String stockNum);
    @Select("select * from stock_record where stockNum = #{stockNum} order by id desc limit 0,1")
    StockRecord getYesterdayRecord(@Param("stockNum")String stockNum);
    @Select("select * from stock_record where stockNum = #{stockNum} order by lowPrice asc limit 0,1")
    StockRecord getLowestRecord(@Param("stockNum")String stockNum);
    @Select("set @rank = 0;")
    void setRankNum();
    @Select("select rank_no from (select id,@rank:=@rank + 1 AS rank_no from stock_record where stockNum = #{stockNum})t " +
            "where id = (select id from stock_record where lowPrice = " +
            "(select min(lowPrice) as lowPrice from  stock_record where stockNum = #{stockNum} ) and stockNum = #{stockNum} order by id desc limit 0,1);")
    int getLowPriceRecordDay(@Param("stockNum")String stockNum);
    @Select("select rank_no from (select id,@rank:=@rank + 1 AS rank_no from stock_record where stockNum = #{stockNum})t " +
            "where id = (select id from stock_record where highPrice = " +
            "(select max(highPrice) as highprice from  stock_record where stockNum = #{stockNum} ) and stockNum = #{stockNum} order by id desc limit 0,1);")
    int getHeightPriceRecordDay(@Param("stockNum")String stockNum);
    @Select("select max(highPrice) from (select highPrice from stock_record where stockNum = #{stockNum} order by id desc limit #{begin},#{end} )t ")
    Float getStockLastestHigh(@Param("stockNum")String stockNum,@Param("begin")int begin,@Param("end")int end);

    @Select("select min(lowPrice) from (select lowPrice from stock_record where stockNum = #{stockNum} order by id desc limit #{begin},#{end} )t")
    Float getStockLastestlow(@Param("stockNum")String stockNum,@Param("begin")int begin,@Param("end")int end);
    @Select("select  min(lowPrice) from (select * from stock_record where stockNum = #{stockNum} order by recordTime desc limit 0,#{limitDay} )t;")
    Float getLatestLowestPrice(@Param("stockNum")String stockNum,@Param("limitDay")int limitDay);

    @Select("select  max(highPrice) from (select * from stock_record where stockNum = #{stockNum} order by recordTime desc limit 0,#{limitDay} )t;")
    Float getLatestHightPrice(@Param("stockNum")String stockNum,@Param("limitDay")int limitDay);
}
