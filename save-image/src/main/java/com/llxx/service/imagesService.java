package com.llxx.service;

import com.llxx.pojo.images;

public interface imagesService {


    //保存图片
    void save(images image);
    //通过usrId查询imagId
    Integer selectByUserId(Integer userId);
    //通过url查询imagId
    Integer selectByUrl(String imageUrl);
}
