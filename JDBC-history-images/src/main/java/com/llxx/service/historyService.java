package com.llxx.service;

import com.llxx.pojo.history;
import com.llxx.pojo.historyRes;

import java.util.List;

public interface historyService {

    //获取用户历史记录
    List<historyRes> listByUserId(Integer userId); ;

    //存储历史记录
    void save(Integer userId, Integer imageId);

    //管理员获取所有历史记录
    List<historyRes> list();

    //用户删除历史记录
    void deleteById(List<Integer>  historyIds, Integer userId);
}
