package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.po.BuySellRecord;
import com.example.demo.entity.po.LowRecord;
import com.example.demo.entity.po.Stock;
import com.example.demo.entity.po.StockRecord;
import com.example.demo.entity.vo.StockRecordVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockMapper {
    @Select("select * from stock")
    List<Stock> getAllStock();

    @Insert("insert into stock (stockNum,stockName,createTime,category) values (#{s.stockNum},#{s.stockName},#{s.createTime},#{s.category})")
    int insertStock(@Param("s")Stock stock);

    @Insert("insert into stock_record (beginPrice,endPrice,highPrice,lowPrice,stockId,stockNum,stockName,category,recordTime) values " +
            "(#{s.beginPrice},#{s.endPrice},#{s.highPrice},#{s.lowPrice},#{s.stockId},#{s.stockNum},#{s.stockName},#{s.category},#{s.recordTime})")
    int daylyRecord(@Param("s")StockRecord stockRecord);

    @Insert("insert into buy_sell_record (buyPrice,buyTime,stockNum,stockName,category,stockId,buyNum) values (#{b.buyPrice},#{b.buyTime}" +
            ",#{b.stockNum},#{b.stockName},#{b.category},#{b.stockId},#{b.buyNum})")
    int insertBuyRecord(@Param("b")BuySellRecord buySellRecord);

    @Update("update buy_sell_record set sellPrice = #{b.sellPrice},sellTime = #{b.sellTime},profitOrLoss = #{b.profitOrLoss},isFinish = 1 where id = #{b.id}")
    int updateSellRecord(@Param("b")BuySellRecord buySellRecord);

    @Select("select * from buy_sell_record where id = #{id}")
    BuySellRecord getOneBuySellRecord(@Param("id")int id);

    @Select("select * from buy_sell_record where isFinish = 0 and buyTime < #{buyTime} and sendTimes < 1 order by buyTime desc")
    List<BuySellRecord> getAllNowBuySellRecord(@Param("buyTime")Date buyTime);

    @Update("<script>" +
            "update buy_sell_record set sendTimes = sendTimes + 1 where id in"+
            "<foreach collection='idList' item='item' open='(' separator=',' close=')'>" +
                "#{item} "+
            "</foreach>" +
            "</script>")
    int updateBuySellRecordSendTimes(@Param("idList")List<Integer>idList);

    @Select("select stockNum,stockId,stockName,category from (select count(1) as num_count,stockNum,stockId,stockName,category from stock_record GROUP BY stockId) t where num_count > #{dayNum} ")
    List<StockRecordVo> getStockNums(@Param("dayNum")int dayNum);

    @Select("select min(lowPrice) from stock_record where recordTime between #{oldDay} and #{nowDay} and stockNum = #{stockNum}")
    float getLatestLowestPrice(@Param("oldDay")Date oldDay,@Param("nowDay")Date nowDay,@Param("stockNum")String stockNum);

    @Insert("insert into low_record (stockId,stockNum,stockName,category,recordDay,minPrice,recordPrice,recordTime,trend) values " +
            "(#{l.stockId},#{l.stockNum},#{l.stockName},#{l.category},#{l.recordDay},#{l.minPrice},#{l.recordPrice},#{l.recordTime},#{l.trend})")
    int insertLowRecord(@Param("l")LowRecord lowRecord);

    @Select("select * from low_record where isSend = 0")
    List<LowRecord> getAllLowRecordNotSend();

    @Update("update low_record set isSend = 1 where isSend = 0")
    int updateLowRecordIsSend();
}