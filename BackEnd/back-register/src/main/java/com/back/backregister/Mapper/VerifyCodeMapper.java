package com.back.backregister.Mapper;

import com.back.backregister.dto.VerifyCodeDto;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * @ author: rose
 * @ date: 2024-11-22
 * @ version: 1.0
 * @ description: 对verifycode表进行操作的接口
 */
@Mapper
public interface  VerifyCodeMapper {

    void insertCode(VerifyCodeDto verifyCodeDto);

    String getCode(String email);


    void deleteCode(@NotNull(message = "注册邮箱不能为空") String email);

    LocalDateTime getExperationTime(String email);
}
