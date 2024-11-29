package com.back.backregister.Mapper;



import com.back.backregister.Do.UserDo;
import com.back.backregister.dto.LoginDto;
import com.back.backregister.dto.RegisterDto;
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
    UserDo getByUserNameOrEmail(String username);

    void add(RegisterDto registerDto);

    Integer getIdByUserName(RegisterDto registerDto);


    UserDo checkLogin(LoginDto loginDto);
}
