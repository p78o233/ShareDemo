package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2020/7/10
 */

import com.example.demo.entity.po.Stock;
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
//    查询系统中全部用户列表
    @Select("select * from user where isdel = 0")
    List<UserMailContentVo> getAllUser();
}
