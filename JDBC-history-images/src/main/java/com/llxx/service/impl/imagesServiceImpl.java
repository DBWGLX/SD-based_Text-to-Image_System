package com.llxx.service.impl;

import com.llxx.mapper.imagesMapper;
import com.llxx.pojo.images;
import com.llxx.service.imagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class imagesServiceImpl implements imagesService {

    @Autowired
    private imagesMapper imagemapper;

    //保存图片
    @Override
    public void save(images image) {
        image.setCreateAt(LocalDateTime.now());
        imagemapper.insert(image);
    }

    @Override
    public Integer selectByUserId(Integer userId) {
        return imagemapper.selectByUserId(userId);
    }

    @Override
    public Integer selectByUrl(String imageUrl) {
        return imagemapper.selectByUrl(imageUrl);
    }
}
