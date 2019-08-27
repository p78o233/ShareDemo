package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2019/8/27
 */

import com.example.demo.entity.Stock;
import com.example.demo.entity.StockRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
}
