注册功能：

![image-20241120085502138](https://raw.githubusercontent.com/Rosewwwfr/blog-imgs/main/blog/image-20241120085502138.png)


修改注册，数据库连接远程数据库，重新设计实体类，创建存储验证码的数据库表，优化邮件发送模板。

/verify ：

请求：

```json
{
    "email": "EMAIL"
}
```

响应:

```json
{
  "code": true,
  "msg": "success",
  "data": "邮件已发送"
}
```

/register

请求：

```json
{
    "username":"wyx",
    "password":"123456",
    "email":"112xxxx557@qq.com",
    "code":"code"
}
```

响应：

```json
{
  "code": true,
  "msg": "success",
  "data": "注册成功,id为：xxx"
}
```