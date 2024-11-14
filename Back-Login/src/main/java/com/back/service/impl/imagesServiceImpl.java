package com.back.service.impl;

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
    public void save(Integer userId, String imageUrl) {
        //iamge对象
        images image = new images();
        image.setImageUrl(imageUrl);
        image.setUserId(userId);
        image.setCreateAt(LocalDateTime.now());
        imagemapper.insert(image);
    }

    @Override
    public void save(images image) {

    }

    @Override
    public Integer selectByUserId(Integer userId) {
        return 0;
    }

    @Override
    public Integer selectByUrl(String imageUrl) {
        return imagemapper.selectByUrl(imageUrl);
    }
}
