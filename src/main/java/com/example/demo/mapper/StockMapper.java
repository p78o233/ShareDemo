package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.po.*;
import com.example.demo.entity.vo.StockPriceVo;
import com.example.demo.entity.vo.StockRecordVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Repository
public interface StockMapper {
    @Select("<script>"+
                "select count(*) from stock where isdel = 0 and userId = #{userId}"+
            "<if test='stockNum!=null'>"
            + "stockNum like '%${stockNum}%'"
            + "</if>"+
            "<if test='stockName!=null'>"
            + "stockName like '%${stockName}%'"
            + "</if>"+
            "</script>")
    int getAllStockCount(@Param("userId")int userId,@Param("stockNum")String stockNum,@Param("stockName")String stockName);
    @Select("<script>"+
            "select * from stock where isdel = 0 and userId = #{userId}"+
            "<if test='stockNum!=null'>"
            + "stockNum like '%${stockNum}%'"
            + "</if>"+
            "<if test='stockName!=null'>"
            + "stockName like '%${stockName}%'"
            + "</if>"+
            " order by weight desc limit #{start},#{pageSize}"+
            "</script>")
    List<Stock> getAllStockPage(@Param("userId")int userId,@Param("stockNum")String stockNum,@Param("stockName")String stockName,@Param("start")int start,@Param("pageSize")int pageSize);

    @Select("select * from stock order by weight desc")
    List<Stock> getAllStock();

    @Select("select stockNum from stock where userId = #{userId} order by weight desc")
    List<String> getAllStockNums(@Param("userId")int userId);
    @Select("select stockNum from stock where userId = #{userId} and weight != 0 order by weight desc")
    List<String> getAllStockNumsByWeight(@Param("userId")int userId);

    @Insert("insert into stock (stockNum,stockName,createTime,category,userId) values (#{s.stockNum},#{s.stockName},#{s.createTime},#{s.category},#{s.userId})")
    int insertStock(@Param("s")Stock stock);

    @Insert("insert into stock_record (beginPrice,endPrice,highPrice,lowPrice,stockId,stockNum,stockName,category,recordTime,flag,userId) values " +
            "(#{s.beginPrice},#{s.endPrice},#{s.highPrice},#{s.lowPrice},#{s.stockId},#{s.stockNum},#{s.stockName},#{s.category},#{s.recordTime},#{s.flag},#{s.userId})")
    int daylyRecord(@Param("s")StockRecord stockRecord);

    @Insert("insert into buy_sell_record (buyPrice,buyTime,stockNum,stockName,category,stockId,buyNum,userId) values (#{b.buyPrice},#{b.buyTime}" +
            ",#{b.stockNum},#{b.stockName},#{b.category},#{b.stockId},#{b.buyNum},#{b.userId})")
    int insertBuyRecord(@Param("b")BuySellRecord buySellRecord);

    @Update("update buy_sell_record set sellPrice = #{b.sellPrice},sellTime = #{b.sellTime},profitOrLoss = #{b.profitOrLoss},isFinish = 1 where id = #{b.id}")
    int updateSellRecord(@Param("b")BuySellRecord buySellRecord);

    @Select("select * from buy_sell_record where id = #{id}")
    BuySellRecord getOneBuySellRecord(@Param("id")int id);

    @Select("select * from buy_sell_record where userId = #{userId} and isFinish = 0 and buyTime < #{buyTime} and sendTimes < 1 order by buyTime desc")
    List<BuySellRecord> getAllNowBuySellRecord(@Param("buyTime")Date buyTime,@Param("userId")int userId);

    @Update("<script>" +
            "update buy_sell_record set sendTimes = sendTimes + 1 where id in"+
            "<foreach collection='idList' item='item' open='(' separator=',' close=')'>" +
                "#{item} "+
            "</foreach>" +
            "</script>")
    int updateBuySellRecordSendTimes(@Param("idList")List<Integer>idList);

    @Select("select stockNum,stockId,stockName,category from (select count(1) as num_count,stockNum,stockId,stockName,category from stock_record GROUP BY stockId) t where num_count >= #{dayNum} ")
    List<StockRecordVo> getStockNums(@Param("dayNum")int dayNum);

//    @Select("select min(lowPrice) from stock_record where recordTime between #{oldDay} and #{nowDay} and stockNum = #{stockNum}")
//    Float getLatestLowestPrice(@Param("oldDay")Date oldDay,@Param("nowDay")Date nowDay,@Param("stockNum")String stockNum);
    @Select("select  min(lowPrice) from (select * from stock_record where stockNum = #{stockNum} order by recordTime desc limit 0,#{limitDay} )t;")
    Float getLatestLowestPrice(@Param("stockNum")String stockNum,@Param("limitDay")int limitDay);

    @Select("select  max(highPrice) from (select * from stock_record where stockNum = #{stockNum} order by recordTime desc limit 0,#{limitDay} )t;")
    Float getLatestHightPrice(@Param("stockNum")String stockNum,@Param("limitDay")int limitDay);

//    @Select("select * from stock_record where recordTime between #{oldDay} and #{nowDay} and stockNum = #{stockNum}")
//    List<StockRecord> getLatestRate(@Param("oldDay")Date oldDay,@Param("nowDay")Date nowDay,@Param("stockNum")String stockNum);
    @Select("select * from stock_record where stockNum = #{stockNum} order by recordTime desc limit #{start},#{end}")
    List<StockRecord> getLatestRate(@Param("stockNum")String stockNum,@Param("start")int start,@Param("end")int end);


    @Insert("insert into low_record (stockId,stockNum,stockName,category,recordDay,minPrice,recordPrice,recordTime,trend,lowHis,dayBefore,highHis,dayBeforeH,maxPrice) values " +
            "(#{l.stockId},#{l.stockNum},#{l.stockName},#{l.category},#{l.recordDay},#{l.minPrice},#{l.recordPrice},#{l.recordTime},#{l.trend},#{l.lowHis},#{l.dayBefore}," +
            "#{l.highHis},#{l.dayBeforeH},#{l.maxPrice})")
    int insertLowRecord(@Param("l")LowRecord lowRecord);

    @Select("select * from low_record where isSend = 0")
    List<LowRecord> getAllLowRecordNotSend();

    @Update("update low_record set isSend = 1 where isSend = 0")
    int updateLowRecordIsSend();

    @Select("select stockName,stockNum,MAX(highPrice) as heightPriceHis,MIN(lowPrice) as lowPriceHis,count(1) as dayNums from stock_record where stockNum = #{stockNum} GROUP BY stockNum")
    StockPriceVo getMaxHisHighLowPrice(@Param("stockNum")String stockNum);

    @Select("select * from stock_record where stockNum = #{stockNum} order by lowPrice asc limit 0,1")
    StockRecord getLowestRecord(@Param("stockNum")String stockNum);

    @Select("select * from stock_record where stockNum = #{stockNum} order by highPrice desc limit 0,1")
    StockRecord getHighestRecord(@Param("stockNum")String stockNum);

    @Select("select * from stock_record where stockNum = #{stockNum} order by recordTime asc")
    List<StockRecord> getHistoryPrice(@Param("stockNum")String stockNum);

//    @Select("select max(highPrice) from (select highPrice from stock_record where stockNum = #{stockNum} order by id asc limit #{begin},#{end} )t ")
//    Float getStockLastestHigh(@Param("stockNum")String stockNum,@Param("begin")int begin,@Param("end")int end);
//
//    @Select("select min(lowPrice) from (select lowPrice from stock_record where stockNum = #{stockNum} order by id asc limit #{begin},#{end} )t")
//    Float getStockLastestlow(@Param("stockNum")String stockNum,@Param("begin")int begin,@Param("end")int end);

    @Select("select max(highPrice) from (select highPrice from stock_record where stockNum = #{stockNum} order by id desc limit #{begin},#{end} )t ")
    Float getStockLastestHigh(@Param("stockNum")String stockNum,@Param("begin")int begin,@Param("end")int end);

    @Select("select min(lowPrice) from (select lowPrice from stock_record where stockNum = #{stockNum} order by id desc limit #{begin},#{end} )t")
    Float getStockLastestlow(@Param("stockNum")String stockNum,@Param("begin")int begin,@Param("end")int end);

    @Select("select * from stock_record where stockNum = #{stockNum} order by id desc limit 0,1")
    StockRecord getYesterdayRecord(@Param("stockNum")String stockNum);

    @Select("set @rank = 0;")
    void setRankNum();
    @Select("select rank_no from (select id,@rank:=@rank + 1 AS rank_no from stock_record where stockNum = #{stockNum})t " +
            "where id = (select id from stock_record where highPrice = " +
            "(select max(highPrice) as highprice from  stock_record where stockNum = #{stockNum} ) and stockNum = #{stockNum} order by id desc limit 0,1);")
    int getHeightPriceRecordDay(@Param("stockNum")String stockNum);
    @Select("select rank_no from (select id,@rank:=@rank + 1 AS rank_no from stock_record where stockNum = #{stockNum})t " +
            "where id = (select id from stock_record where lowPrice = " +
            "(select min(lowPrice) as lowPrice from  stock_record where stockNum = #{stockNum} ) and stockNum = #{stockNum} order by id desc limit 0,1);")
    int getLowPriceRecordDay(@Param("stockNum")String stockNum);

    @Update("update stock set weight = #{weight} where stockName like '%银行%' and category = 1")
    int changeBankStockWeight(@Param("weight")int weight);

    @Select("select * from stock_record")
    List<StockRecord> getAllStockRecord();
    @Update("<script>"+"update stock_record set flag = #{flag} where id in "
            + "<foreach item='item' index='index' collection='idList' open='(' separator=',' close=')'>"
                + "#{item}"
            + "</foreach>"
            + "</script>")
    int updateFlag(@Param("flag")int flag,@Param("idList")List<Integer>idList);

    @Select("select * from tag_buy_sell where isSend = 0 and flag = #{flag}")
    List<TagBuySell> getTagBuySellList(@Param("flag")boolean flag);
    @Update("update tag_buy_sell set isSend = 1 where id = #{id}")
    int updateIsSendBuySellTag(@Param("id")int id);

    @Select("select count(*) from user where account = #{account}  and isdel = 0")
    int isExistAccount(@Param("account")String account);

    @Select("select * from user where account = #{account} and pwd = #{pwd} and isdel = 0")
    User login(@Param("account")String account,@Param("pwd")String pwd);

    @Insert("insert into stock (stockNum,stockName,createTime,category,weight,userId) values " +
            "(#{s.stockNum},#{s.stockName},#{s.createTime},#{s.category},#{s.weight},#{s.userId})")
    int insertStockN(@Param("s")Stock stock);
    @Update("update stock set stockNum = #{s.stockNum},stockName = #{s.stockName},category = #{s.category},weight = #{s.weight} where id = #{s.id}")
    int editStockN(@Param("s")Stock stock);
    @Update("update stock set isdel = 0 where id = #{id}")
    int deleteStockN(@Param("id")int id);
    @Select("select count(*) from stock where stockNum = #{stockNum} and userId = #{userId}")
    int countInsertStock(@Param("stockNum")String stockNum,@Param("userId")int userId);
    @Select("select count(*) from stock where stockNum = #{stockNum} and userId = #{userId} and id != #{id}")
    int countEditStock(@Param("stockNum")String stockNum,@Param("userId")int userId,@Param("id")int id);

}
