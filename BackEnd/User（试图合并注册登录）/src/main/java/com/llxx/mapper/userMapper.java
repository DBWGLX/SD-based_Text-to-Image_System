package com.llxx.mapper;

import com.llxx.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface userMapper {
    //用户登录
    @Select("select * from users where username=#{username} and password=#{password}")
    Users login(Users u);
}
