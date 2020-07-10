package com.example.demo.mapper;/*
 * @author p78o2
 * @date 2020/7/8
 */

import com.example.demo.entity.po.BuySellNotice;
import com.example.demo.entity.po.UserSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingMapper {

    @Select("select * from user_setting where userId = #{userId} order by setCate asc ")
    List<UserSetting> getUserBaseSetting(@Param("userId")int userId);

    @Update("update user_setting set setVal = #{u.setVal} where userId = #{u.userId} and setCate = #{u.setCate}")
    int updateUserBaseSetting(@Param("u")UserSetting userSetting);


    @Select("<script>"+
            "select count(*) from buy_sell_notice where isdel = 0  and userId = #{userId}"+
            "<if test='stockNum != null'>"+
                " and stockNum = #{stockNum}"+
            "</if>"+
            "<if test='stockName != null'>"+
            " and stockName like '%${stockName}%'"+
            "</if>"+
            "<if test='cate != 0'>"+
            " and cate = #{cate}'"+
            "</if>"+
            "</script>")
    int getBuySellNoticeCount(@Param("userId")int userId,@Param("stockNum")String stockNum,@Param("stockName")String stockName,@Param("cate")int cate);

    @Select("<script>"+
            "select * from buy_sell_notice where isdel = 0  and userId = #{userId}"+
            "<if test='stockNum != null'>"+
            " and stockNum = #{stockNum}"+
            "</if>"+
            "<if test='stockName != null'>"+
            " and stockName like '%${stockName}%'"+
            "</if>"+
            "<if test='cate != 0'>"+
            " and cate = #{cate}'"+
            "</if>"+
            " order by id desc limit #{start},#{pageSize}"+
            "</script>")
    List<BuySellNotice> getBuySellNoticePage(@Param("userId")int userId, @Param("stockNum")String stockNum, @Param("stockName")String stockName, @Param("cate")int cate,@Param("start")int start,@Param("pageSize")int pageSize);

    @Insert("insert into buy_sell_notice (stockNum,stockName,userId,price,cate,content,createTime) values (#{b.stockNum},#{b.stockName}," +
            "#{b.userId},#{b.price},#{b.cate},#{b.content},#{b.createTime})")
    int insertIntoBuySellNotice(@Param("b")BuySellNotice buySellNotice);

    @Update("update buy_sell_notice set stockNum = #{b.stockNum},stockName = #{b.stockName},price = #{b.price},cate = #{b.cate},content = #{b.content},modifyTime = #{b.modifyTime} where id = #{b.id}")
    int updateBuySellNotice(@Param("b")BuySellNotice buySellNotice);

    @Update("update buy_sell_notice set isdel = 1 where id = #{id}")
    int deleteBuySellNotice(@Param("id")int id);
}
