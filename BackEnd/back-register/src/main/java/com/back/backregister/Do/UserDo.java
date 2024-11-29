package com.back.backregister.Do;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据对象，也就是何数据库表一一对应的。
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDo {
    @TableId
    private Integer userId;
    private String username;
    private String email;
    private String password;
    private String phone;

    // 用以验证的code,接收表单上传的
    @TableField(exist = false)
    private String code;
}
