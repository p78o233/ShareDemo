package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface GobalMapper {
//    根据userId获取token
    @Select("select id from user where token = #{token} and isdel = 0 ")
    int getUserIdByToken(@Param("token")String token);

}
