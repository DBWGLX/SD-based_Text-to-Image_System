package com.back.backregister.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//历史记录读取的封装类
public class HistoryRes {
    private Integer historyId;
    private String imageUrl;
    private LocalDateTime createAt;
}
