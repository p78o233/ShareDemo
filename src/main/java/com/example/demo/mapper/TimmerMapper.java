package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2020/7/10
 */

import com.example.demo.entity.po.Stock;
import com.example.demo.entity.po.StockRecord;
import com.example.demo.entity.vo.UserMailContentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimmerMapper {
//    获取全部股票记录
    @Select("select * from stock where isdel = 0")
    List<Stock> getAllStock();
//    查询某个股票的N天最低价
    @Select("select MIN(lowPrice) from  stock_record where stockNum = #{stockNum} order by id desc limit 0,#{pageSize}")
    float getLowestRectDay(@Param("stockNum")String stockNum,@Param("pageSize")int pageSize);
//    查询某个股票的N天最高价
    @Select("select MAX(highPrice) from stock_record where stockNum = #{stockNum} order by id desc limit 0,#{pageSize}")
    float getHeightRectDay(@Param("stockNum")String stockNum,@Param("pageSize")int pageSize);
//    获取历史最高价
    @Select("select * from stock_record where stockNum = #{stockNum} and highPrice =  (select MAX(highPrice) from stock_record where stockNum = #{stockNum} ) ORDER BY id desc limit 0,1;")
    StockRecord getHeightestPrice(@Param("stockNum")String stockNum);
//    获取历史最低价
    @Select("select * from stock_record where stockNum = #{stockNum} and lowPrice =  (select MIN(lowPrice) from stock_record where stockNum = #{stockNum} ) ORDER BY id desc limit 0,1;")
    StockRecord getLowestPrice(@Param("stockNum")String stockNum);
//    查询系统中全部用户列表
    @Select("select * from user where isdel = 0")
    List<UserMailContentVo> getAllUser();
//    查询观察了这个股票的用户
    @Select("select userId from stock_user where stockId = #{stockId}")
    List<Integer> getLookUserId(@Param("stockId")int stockId);
}
