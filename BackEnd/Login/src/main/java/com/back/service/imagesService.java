package com.back.service;

public interface imagesService {
    //保存图片
    void save(Integer userId, String imageUrl);

    //通过url查询imagId
    Integer selectByUrl(String imageUrl);
}
