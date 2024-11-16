package com.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class images {
    private Integer imageId;
    private Integer userId;
    private String imageUrl;
    private LocalDateTime createAt;
}
