package com.llxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxx.Dto.UserDto;
import com.llxx.mapper.UsersMapper;
import com.llxx.pojo.Result;
import com.llxx.pojo.Users;
import com.llxx.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Result getUserById(Integer userId) {
        Users user= usersMapper.selectById(userId);
        log.info("1");
        if(user==null){
            log.info("1");
            return Result.error("用户不存在");
        }
        log.info("1");
        UserDto userDto=new UserDto()
            .setUserId(userId)
            .setUsername(user.getUsername())
            .setEmail(user.getEmail())
            .setPhone(user.getPhone());
        log.info("1");
        return Result.success(userDto);
    }
}
