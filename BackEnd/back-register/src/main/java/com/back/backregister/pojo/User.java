package com.back.backregister.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String username;
    private String email;
    private String password;

    // 用以验证的code,接收表单上传的
    private String code;
}
