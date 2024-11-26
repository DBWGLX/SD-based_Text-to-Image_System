package com.llxx.controller;

import com.llxx.pojo.Result;
import com.llxx.pojo.History;
import com.llxx.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody History history) {
        return historyService.saveHistory(history);
    }

   //用户获取历史记录
    @GetMapping("/{userId}")
    public Result getHistoryByUser(@PathVariable Integer userId
                                    ,@RequestParam Integer page
                                    ,@RequestParam Integer size) {
        return historyService.getByUserId(userId,page,size);
    }

    //管理员获取所有历史记录
    @GetMapping("/admins")
    public Result getHistory(@RequestParam Integer page,@RequestParam Integer size) {
        return historyService.getByAdmint(page,size);
    }
    //用户批量删除历史记录
    @DeleteMapping("/{userId}/{historyIds}")
    public Result deleteHistoryByIds(@PathVariable Integer userId,@PathVariable List<Integer> historyIds) {
        return historyService.deleteHistoryByIds(userId,historyIds);
    }

    //管理员批量删除历史记录
    @DeleteMapping("/admins/{historyIds}")
    public Result deleteHistoryByAdmins(@PathVariable List<Integer> historyIds) {
        return historyService.deleteHistoryByAdmins(historyIds);
    }

}
