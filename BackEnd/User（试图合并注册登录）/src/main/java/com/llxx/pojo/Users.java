package com.llxx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="users")
public class Users {
    @TableId
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    @TableField(exist = false)
    private String otp;
}
