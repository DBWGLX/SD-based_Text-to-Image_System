package com.llxx.service.impl;

import com.llxx.mapper.historyMapper;
import com.llxx.pojo.history;
import com.llxx.service.historyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class historyServiceImpl implements historyService {
    @Autowired
    historyMapper historymapper;
    @Override
    public void save(Integer userId, Integer imageId) {
        history hy=new history();
        hy.setUserId(userId);
        hy.setImageId(imageId);
        hy.setCreateAt(LocalDateTime.now());
        historymapper.insert(hy);
    }
}
