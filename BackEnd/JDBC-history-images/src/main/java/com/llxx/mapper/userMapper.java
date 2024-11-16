package com.llxx.mapper;

import com.llxx.pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface userMapper {
    //用户登录
    @Select("select * from user where username=#{username} and password=#{password}")
    user login(user u);
}
