package com.example.demo.mapper;
/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.po.*;
import com.example.demo.entity.vo.StockPriceVo;
import com.example.demo.entity.vo.StockRecordVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Repository
public interface StockMapper {

    @Select("select * from stock order by weight desc")
    List<Stock> getAllStock();

    @Select("select stockNum from stock where userId = #{userId} order by weight desc")
    List<String> getAllStockNums(@Param("userId")int userId);
    @Select("select stockNum from stock where userId = #{userId} and weight != 0 order by weight desc")
    List<String> getAllStockNumsByWeight(@Param("userId")int userId);

    @Select("select * from buy_sell_record where userId = #{userId} and isFinish = 0 and buyTime < #{buyTime} and sendTimes < 1 order by buyTime desc")
    List<BuySellRecord> getAllNowBuySellRecord(@Param("buyTime")Date buyTime,@Param("userId")int userId);

    @Update("<script>" +
            "update buy_sell_record set sendTimes = sendTimes + 1 where id in"+
            "<foreach collection='idList' item='item' open='(' separator=',' close=')'>" +
                "#{item} "+
            "</foreach>" +
            "</script>")
    int updateBuySellRecordSendTimes(@Param("idList")List<Integer>idList);

    @Select("select  min(lowPrice) from (select * from stock_record where stockNum = #{stockNum} order by recordTime desc limit 0,#{limitDay} )t;")
    Float getLatestLowestPrice(@Param("stockNum")String stockNum,@Param("limitDay")int limitDay);

    @Select("select  max(highPrice) from (select * from stock_record where stockNum = #{stockNum} order by recordTime desc limit 0,#{limitDay} )t;")
    Float getLatestHightPrice(@Param("stockNum")String stockNum,@Param("limitDay")int limitDay);

    @Select("select stockName,stockNum,MAX(highPrice) as heightPriceHis,MIN(lowPrice) as lowPriceHis,count(1) as dayNums from stock_record where stockNum = #{stockNum} GROUP BY stockNum")
    StockPriceVo getMaxHisHighLowPrice(@Param("stockNum")String stockNum);

    @Select("select * from stock_record where stockNum = #{stockNum} order by lowPrice asc limit 0,1")
    StockRecord getLowestRecord(@Param("stockNum")String stockNum);

    @Select("select * from stock_record where stockNum = #{stockNum} order by recordTime asc")
    List<StockRecord> getHistoryPrice(@Param("stockNum")String stockNum);

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

    @Select("select * from tag_buy_sell where isSend = 0 and flag = #{flag}")
    List<TagBuySell> getTagBuySellList(@Param("flag")boolean flag);
    @Update("update tag_buy_sell set isSend = 1 where id = #{id}")
    int updateIsSendBuySellTag(@Param("id")int id);



//    =====================================================================================================================================
    @Insert("insert into stock_user (userId,stockId,createTime,weight) values (#{s.userId},#{s.stockId},#{s.createTime},#{s.weight})")
    int test(@Param("s")StockUser stockUser);

    @Select("select count(*) from user where account = #{account}  and isdel = 0")
    int isExistAccount(@Param("account")String account);

    @Select("select * from user where account = #{account} and pwd = #{pwd} and isdel = 0")
    User login(@Param("account")String account,@Param("pwd")String pwd);

    @Insert("insert into stock (stockNum,stockName,createTime,category) values " +
            "(#{s.stockNum},#{s.stockName},#{s.createTime},#{s.category})")
    int insertStockN(@Param("s")Stock stock);
    @Update("update stock_user set weight = #{weight} where stockId = #{stockId} and userId = #{userId}")
    int editStockUser(@Param("weight")int weight,@Param("stockId")int stockId,@Param("userId")int userId);
    @Update("update stock_user set isdel = 1 where userId = #{userId} and stockId = #{stockId}")
    int deleteStockN(@Param("userId")int userId,@Param("stockId")int stockId);
    @Select("select count(*) from stock_user where stockId = #{stockId} and userId = #{userId}")
    int countInsertStock(@Param("stockId")int stockId,@Param("userId")int userId);

    @Select("<script>"+
            "select count(*) from stock_user where isdel = 0 and userId = #{userId}"+
            "<if test='stockIds.size() > 0'>"+
            " and stockId in "+
            "<foreach item='item' index='index' collection='stockIds' open='(' separator=',' close=')'> #{item} </foreach> "+
            "</if>"+
            "</script>")
    int getAllStockCount(@Param("userId")int userId,@Param("stockIds")List<Integer> stockIds);
    @Select("<script>"+
            "select * from stock_user where isdel = 0 and userId = #{userId}"+
            "<if test='stockIds.size() > 0'>"+
            " and stockId in "+
            "<foreach item='item' index='index' collection='stockIds' open='(' separator=',' close=')'> #{item} </foreach> "+
            "</if>"+
            " order by weight desc limit #{start},#{pageSize}"+
            "</script>")
    List<StockUser> getAllStockPage(@Param("userId")int userId,@Param("stockIds")List<Integer> stockIds,@Param("start")int start,@Param("pageSize")int pageSize);

    @Select("select * from stock where id = #{id}")
    Stock getStockById(@Param("id")int id);
    @Select("select * from stock where stockNum = #{stockNum}")
    Stock getStockByStockNum(@Param("stockNum")String stockNum);

    @Select("select count(*) from stock where stockNum = #{stockNum} and isdel = 0")
    int getStockByNumCount(@Param("stockNum")String stockNum);

    @Insert("insert into stock_user(userId,stockId,createTime,weight) values (#{s.userId},#{s.stockId},#{s.createTime},#{s.weight})")
    int insertStockUser(@Param("s")StockUser stockUser);

    @Select("select id from stock where stockNum like '%${stockNum}%' and isdel = 0")
    List<Integer> getStockIdByStockNum(@Param("stockNum")String stockNum);
    @Select("select id from stock where stockName like '%${stockName}%' and isdel = 0")
    List<Integer> getStockIdByStockName(@Param("stockName")String stockName);


//    根据股票号码获取最近走势
    @Select("select * from stock_record where stockNum = #{stockNum} and beginPrice != 0 order by id asc")
    List<StockRecord> getAllStockRecordByStockNum(@Param("stockNum")String stockNum);
    @Select("select * from stock_record where stockNum = #{stockNum} and beginPrice != 0 order by id asc limit 0,#{size}")
    List<StockRecord> getSizeStockRecordByStockNum(@Param("stockNum")String stockNum,@Param("size")int size);
}
