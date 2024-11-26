package com.llxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llxx.pojo.History;
import com.llxx.pojo.Result;

import java.util.List;

public interface HistoryService extends IService<History> {


    Result saveHistory(History history);

    Result getByUserId(Integer userId, Integer page, Integer size);

    Result getByAdmint(Integer page, Integer size);

    Result deleteHistoryByIds(Integer userId, List<Integer> historyIds);

    Result deleteHistoryByAdmins(List<Integer> historyIds);
}
