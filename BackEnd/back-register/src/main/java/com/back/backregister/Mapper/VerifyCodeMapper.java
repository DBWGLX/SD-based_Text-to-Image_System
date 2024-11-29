package com.back.backregister.Mapper;

import com.back.backregister.dto.VerifyCodeDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
}
