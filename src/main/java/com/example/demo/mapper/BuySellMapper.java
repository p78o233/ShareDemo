package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2020/5/15
 */

import com.example.demo.entity.po.BuyRecord;
import com.example.demo.entity.po.SellRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BuySellMapper {
//    根据条件获取购买记录
    @Select("<script>" +
            "select count(*) from buy_record where isdel = 0 and userId = #{userId}"+
            "<if test='stockNum != null'>"+
            " and stockNum like '%${stockNum}%'"+
            "</if>"+
            "<if test='beginTime != null'>"+
            " and buyTime &gt; #{beginTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " and buyTime &lt; #{endTime}"+
            "</if>"+
            "</script>")
    int countBuyRecord(@Param("userId")int userId,@Param("stockNum")String stockNum,@Param("beginTime") Date beginTime,@Param("endTime")Date endTime);
    @Select("<script>" +
            "select * from buy_record where isdel = 0 and userId = #{userId}"+
            "<if test='stockNum != null'>"+
            " and stockNum like '%${stockNum}%'"+
            "</if>"+
            "<if test='beginTime != null'>"+
            " and buyTime &gt; #{beginTime}"+
            "</if>"+
            "<if test='endTime != null'>"+
            " and buyTime &lt; #{endTime}"+
            "</if>"+
            " order by id desc limit #{start},#{pageSize}"+
            "</script>")
    List<BuyRecord> pageBuyRecord(@Param("userId")int userId, @Param("stockNum")String stockNum, @Param("beginTime") Date beginTime, @Param("endTime")Date endTime,@Param("start")int start,@Param("pageSize")int pageSize);
//    根据id获取买入记录详细
    @Select("select * from buy_record where id = #{id}")
    BuyRecord getBuyRecordDetailById(@Param("id")int id);
//    根据买入id获取卖出记录
    @Select("select * from sell_record where buyId = #{buyId} and isdel = 0 order by id desc")
    List<SellRecord> getSellRecordList(@Param("buyId")int buyId);
//    根据买入id获取卖出记录条数
    @Select("select count(*) from sell_record where buyId = #{buyId} and isdel = 0")
    int countSellRecord(@Param("buyId")int buyId);
//    新增购买记录
    @Insert("insert into buy_record (buyPrice,buyTime,stockNum,stockName,category,stockId,buyNum,userId) values " +
            "(#{b.buyPrice},#{b.buyTime},#{b.stockNum},#{b.stockName},#{b.category},#{b.stockId},#{b.buyNum},#{b.userId})")
    int insertBuyRecord(@Param("b")BuyRecord buyRecord);
//    更新购买记录
    @Update("update buy_record set buyPrice = #{b.buyPrice},buyTime = #{b.buyTime},buyNum = #{b.buyNum} where id = #{b.id}")
    int updateBuyRecord(@Param("b")BuyRecord buyRecord);

//    删除购买记录
    @Update("update buy_record set isdel = 1 where id = #{id}")
    int deleteBuyRecord(@Param("id")int id);
    @Update("update sell_record set isdel = 1 where buyId = #{buyId}")
    int deleteSellRecordByBuyId(@Param("buyId")int buyId);

//    新增卖出记录
    @Insert("insert into sell_record (buyId,sellPrice,profitAndLoss,sellTime,stockNum,stockName,category,stockId,sellNum,userId) values " +
            "(#{s.buyId},#{s.sellPrice},#{s.profitAndLoss},#{s.sellTime},#{s.stockNum},#{s.stockName},#{s.category},#{s.stockId},#{s.sellNum},#{s.userId})")
    int insertSellRecord(@Param("s")SellRecord sellRecord);
//    修改卖出记录
    @Update("update sell_record set sellPrice = #{s.sellPrice},profitAndLoss = #{s.profitAndLoss},sellTime = #{s.sellTime},sellNum = #{s.sellNum} where id = #{s.id}")
    int updateSellRecord(@Param("s")SellRecord sellRecord);
//    删除卖出记录
    @Update("update sell_record set isdel = 1 where id = #{id}")
    int deleteSellRecord(@Param("id")int id);
}
