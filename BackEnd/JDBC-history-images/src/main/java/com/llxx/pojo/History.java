package com.llxx.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="image_history")
public class History {
    @TableId
    private Integer id;
    private Integer userId;
    private String imageName;
    private LocalDateTime generationTime;
    private String prompt;
    private String negativePrompt;
    private Integer numInferenceSteps;
    private BigDecimal guidanceScale;
    private Integer width;
    private Integer height;
    private Integer seed;
}
