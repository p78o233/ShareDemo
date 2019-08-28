package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.BuySellRecord;
import com.example.demo.entity.Stock;
import com.example.demo.entity.StockRecord;
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

    @Update("update buy_sell_record set sellPrice = #{b.sellPrice},sellTime = #{b.sellTime},profitOrLoss = #{b.profitOrLoss},isFinish = 1")
    int updateSellRecord(@Param("b")BuySellRecord buySellRecord);

    @Select("select * from buy_sell_record where id = #{id}")
    BuySellRecord getOneBuySellRecord(@Param("id")int id);

    @Select("select * from buy_sell_record where isFinish = 0 and buyTime < #{buyTime} order by buyTime desc")
    List<BuySellRecord> getAllNowBuySellRecord(@Param("buyTime")Date buyTime);

    @Select("select stockNum from (select count(1) as num_count,stockNum  from stock_record GROUP BY stockId) t where num_count > #{dayNum} ")
    List<String> getStockNums(@Param("dayNum")int dayNum);
}
