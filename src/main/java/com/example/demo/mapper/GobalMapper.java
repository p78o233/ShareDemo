package com.example.demo.mapper;

import com.example.demo.entity.po.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface GobalMapper {
//    根据userId获取token
    @Select("select id from user where token = #{token} and isdel = 0 ")
    int getUserIdByToken(@Param("token")String token);
//    根据股票编号获取股票详细信息
    @Select("select * from stock where stockNum = #{stockNum} and isdel")
    Stock getStockDetailByStockNum(@Param("stockNum")String stockNum);
}
