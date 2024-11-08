package com.llxx.service.impl;

import com.llxx.mapper.historyMapper;
import com.llxx.mapper.imagesMapper;
import com.llxx.pojo.history;
import com.llxx.pojo.historyRes;
import com.llxx.service.historyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class historyServiceImpl implements historyService {
    @Autowired
    historyMapper historymapper;

    @Autowired
    imagesMapper imagesmapper;

    //获取用户所有历史记录
    @Override
    public List<historyRes> listByUserId(Integer userId) {
        return historymapper.listByUserId(userId);
    }

    //存储历史记录
    @Override
    public void save(Integer userId, Integer imageId) {
        history hy=new history();
        hy.setUserId(userId);
        hy.setImageId(imageId);
        hy.setCreateAt(LocalDateTime.now());
        historymapper.insert(hy);
    }

    //管理员获取所有历史记录
    @Override
    public List<historyRes> list() {
        return historymapper.list();
    }

    //用户删除历史记录
    @Override
    @Transactional
    public void deleteById(List<Integer>  historyIds, Integer userId) {
        log.info("historyIds:{}",historyIds);
        List<Integer> imageIds=new ArrayList<>();//获取图像ids
        for (Integer id : historyIds) {
            imageIds.add(historymapper.selectById(id,userId));
        }
        log.info("imageIds:{}",imageIds);
        historymapper.delete(historyIds);//删除历史记录
        imagesmapper.delete(imageIds);
    }
}
