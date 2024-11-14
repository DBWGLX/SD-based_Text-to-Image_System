package com.back.web;


import com.back.model.Result;
import com.back.service.historyService;
import com.back.service.imagesService;
import com.back.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class imagesController {
    @Autowired
    private imagesService imageService;
    @Autowired
    private historyService historyservice;

    @Autowired
    private HttpServletRequest request;//请求对象

    //存储图片
    @PostMapping("/api/images/save")
    public Result save(@PathVariable String imageUrl) {
        //1.获取操作人的id
        String jwt = request.getHeader("token");//获取jwt
        Claims claims = JwtUtils.parseJWT(jwt);//获取自定义类型
        Integer userId = (Integer) claims.get("userId");
        imageService.save(userId,imageUrl);//存图片
        Integer imageId=imageService.selectByUrl(imageUrl);//获取图片id
        historyservice.save(userId,imageId);//存历史记录
        return Result.success();
    }
}
