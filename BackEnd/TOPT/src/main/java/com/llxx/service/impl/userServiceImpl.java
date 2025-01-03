package com.llxx.service.impl;

import com.llxx.mapper.userMapper;
import com.llxx.pojo.Users;
import com.llxx.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private userMapper usermapper;
    @Override
    public Users login(Users u) {
        return usermapper.login(u);
    }
}
