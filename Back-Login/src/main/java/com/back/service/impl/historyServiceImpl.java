package com.back.service.impl;

import com.llxx.mapper.historyMapper;
import com.llxx.pojo.history;
import com.llxx.pojo.historyRes;
import com.llxx.service.historyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class historyServiceImpl implements historyService {
    @Autowired
    historyMapper historymapper;

    @Override
    public List<historyRes> listByUserId(Integer userId) {
        return List.of();
    }

    @Override
    public void save(Integer userId, Integer imageId) {
        history hy=new history();
        hy.setUserId(userId);
        hy.setImageId(imageId);
        hy.setCreateAt(LocalDateTime.now());
        historymapper.insert(hy);
    }

    @Override
    public List<historyRes> list() {
        return List.of();
    }

    @Override
    public void deleteById(List<Integer> historyIds, Integer userId) {

    }
}
