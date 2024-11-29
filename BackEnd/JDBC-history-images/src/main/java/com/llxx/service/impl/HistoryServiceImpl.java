package com.llxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxx.mapper.HistoryMapper;
import com.llxx.pojo.History;
import com.llxx.pojo.Result;
import com.llxx.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Autowired
    HistoryMapper historyMapper;

    @Override
    public Result saveHistory(History history) {
        if(history==null) {
            return Result.error("存储失败");
        }
        history.setGenerationTime(LocalDateTime.now());
        historyMapper.insert(history);
        return Result.success();
    }

    @Override
    public Result getByUserId(Integer userId, Integer page, Integer size) {
        // 创建分页对象，传入当前页码和每页显示的记录数
        Page<History> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<History>()
                .eq(History::getUserId, userId);
        // 使用MyBatis Plus的分页查询方法，传入分页对象和查询条件
        IPage<History> historyIPage = historyMapper.selectPage(pageInfo, queryWrapper);
        // 获取分页查询结果中的记录列表
        List<History> historyList = historyIPage.getRecords();
        return Result.success(historyList);
    }

    @Override
    public Result getByAdmint(Integer page, Integer size) {
        // 创建分页对象，传入当前页码和每页显示的记录数
        Page<History> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<History>();
        // 使用MyBatis Plus的分页查询方法，传入分页对象和查询条件
        IPage<History> historyIPage = historyMapper.selectPage(pageInfo, queryWrapper);
        // 获取分页查询结果中的记录列表
        List<History> historyList = historyIPage.getRecords();
        return Result.success(historyList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteHistoryByIds(Integer userId, List<Integer> historyIds) {
        if(historyIds==null || historyIds.size()==0) {
            return Result.error("删除失败，请添加删除元素");
        }
        LambdaQueryWrapper<History>wrapper = new LambdaQueryWrapper<History>()
                .in(History::getId, historyIds)
                .eq(History::getUserId, userId);
        int total=historyMapper.delete(wrapper);
        if(total!=historyIds.size()) {
            return Result.error("删除失败");
        }
        return Result.success();
    }

    @Override
    public Result deleteHistoryByAdmins(List<Integer> historyIds) {
        if(historyIds==null || historyIds.size()==0) {
            return Result.error("删除失败，请添加删除元素");
        }
        LambdaQueryWrapper<History>wrapper = new LambdaQueryWrapper<History>()
                .in(History::getId, historyIds);
        int total=historyMapper.delete(wrapper);
        if(total!=historyIds.size()) {
            return Result.error("删除失败");
        }
        return Result.success();
    }
}
