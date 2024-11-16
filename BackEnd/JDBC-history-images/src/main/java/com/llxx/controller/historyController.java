package com.llxx.controller;

import com.llxx.pojo.Result;
import com.llxx.pojo.historyRes;
import com.llxx.service.historyService;
import com.llxx.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class historyController {

    @Autowired
    private historyService historyservice;

    /*//用户获取历史记录
    @GetMapping("/api/history")
    public Result getHistoryByUser() {
        log.info("获取历史记录");
        //1.获取操作人的id
        String jwt = request.getHeader("token");//获取jwt
        Claims claims = JwtUtils.parseJWT(jwt);//获取自定义类型
        Integer userId = (Integer) claims.get("userId");//操作者id
        userid=userId;//这个操作是方便下面删除
        log.info("操作者id：{}",userId);
        List<historyRes> hys=historyservice.listByUserId(userId);
        return Result.success(hys);
    }*/

    //用户获取历史记录
    @GetMapping("/api/history")
    public Result getHistoryByUser(@RequestHeader String token) {
        log.info("获取历史记录");
        //1.获取操作人的id
        String jwt = token;//获取jwt
        Claims claims = JwtUtils.parseJWT(jwt);//获取自定义类型
        Integer userId = (Integer) claims.get("userId");//操作者id
        log.info("操作者id：{}",userId);
        List<historyRes> hys=historyservice.listByUserId(userId);
        return Result.success(hys);
    }





    //管理员获取所有历史记录
    @GetMapping("/api/history/admins")
    public Result getHistory() {
        List<historyRes> hys=historyservice.list();
        return Result.success(hys);
    }

   /* //获取userid，在前后端联调时，这个可以删除
    @GetMapping("/api/history/userId")
    public Result getUserId() {
        String jwt = request.getHeader("token");//获取jwt
        Claims claims = JwtUtils.parseJWT(jwt);//获取自定义类型
        userid = (Integer) claims.get("userId");//操作者id
        return Result.success(userid);
    }*/

    //用户批量删除历史记录,先获取userid，上面那个
    @DeleteMapping("/api/history/{historyIds}")
    public Result deleteHistoryByUser(@RequestHeader String token,@PathVariable List<Integer> historyIds) {
        //1.获取操作人的id
        String jwt = token;//获取jwt
        Claims claims = JwtUtils.parseJWT(jwt);//获取自定义类型
        Integer userId = (Integer) claims.get("userId");//操作者id
        log.info("操作者id：{}",userId);
        historyservice.deleteById(historyIds,userId);
        log.info("{}删除历史记录：{}",userId,historyIds);
        return Result.success();
    }
}
