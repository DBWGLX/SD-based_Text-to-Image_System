package com.back.backregister.Mapper;


import com.back.backregister.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ author: rose
 * @ date: 2024-11-14
 * @ version: 1.0
 * @ description:
 */
@Mapper
public interface UserMapper {

    // 根据username查询是否有用户
    User getByUserNameOrEmail(String username);

    void add(User user);

    Integer getIdByUserName(User user);


}
