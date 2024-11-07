package com.llxx.controller;

import com.llxx.pojo.Result;
import com.llxx.pojo.images;
import com.llxx.service.historyService;
import com.llxx.service.imagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class imagesController {
    @Autowired
    private imagesService imageservice;
    @Autowired
    private historyService historyservice;

    @PostMapping("/api/images/save")
    public Result save(@RequestBody images image) {
        imageservice.save(image);//存图片
        log.info("图片存储成功");
        Integer imageId = imageservice.selectByUrl(image.getImageUrl());//获得imageId
        log.info("图片存储ID{}",imageId);
        historyservice.save(image.getUserId(),imageId);
        log.info("历史记录存储成功");
        return Result.success();
    }
}
