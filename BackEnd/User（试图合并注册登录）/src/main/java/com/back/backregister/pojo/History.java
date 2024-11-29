package com.back.backregister.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Integer historyId;
    private Integer userId;
    private Integer imageId;
    private LocalDateTime createAt;
}
