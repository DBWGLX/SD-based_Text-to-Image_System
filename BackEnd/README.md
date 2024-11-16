# DATABASE

```
//我的mysql版本是： C:mysql.exe  Ver 8.0.11 for Win64 on x86_64 (MySQL Community Server - GPL)

// MySQL 数据库的连接信息
    String url = "jdbc:mysql://localhost:3306/SD";  // URL 格式: jdbc:mysql://主机:端口/数据库名
    String user = "root";  // 数据库用户名
    String password = "123456";  // 数据库密码
```

### 用户信息表：

这里是用户的信息：账号，密码，邮箱，电话。

密码用mysql自带的password()函数加密后再插入/匹配。

![image](https://github.com/user-attachments/assets/8752565f-4216-433d-9178-f370b1538826)

### 图像历史表：

这里是用户生成图像操作的记录：谁，什么时候，用什么生成了什么。

![image](https://github.com/user-attachments/assets/aab67239-8688-4469-9f57-58a335120ed9)
