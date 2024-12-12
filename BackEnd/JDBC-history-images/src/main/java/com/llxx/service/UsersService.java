package com.llxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llxx.pojo.Result;
import com.llxx.pojo.Users;

public interface UsersService extends IService<Users> {

    Result getUserById(Integer userId);
}
