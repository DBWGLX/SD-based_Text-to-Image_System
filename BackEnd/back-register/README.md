注册功能：

![image-20241120085502138](https://raw.githubusercontent.com/Rosewwwfr/blog-imgs/main/blog/image-20241120085502138.png)

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