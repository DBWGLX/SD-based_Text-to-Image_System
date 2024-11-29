# DATABASE

```
//我的mysql版本是： C:mysql.exe  Ver 8.0.11 for Win64 on x86_64 (MySQL Community Server - GPL)

// MySQL 数据库的连接信息
    String url = "jdbc:mysql://localhost:3306/SD";  // URL 格式: jdbc:mysql://主机:端口/数据库名
    String user = "root";  // 数据库用户名
    String password = "123456";  // 数据库密码
```

### 用户信息表：

这里是用户的信息：用户名，密码，邮箱，电话。（id是唯一标识，在图像历史表中代表用户）

密码用mysql自带的password()函数加密后再插入/匹配。

![image](https://github.com/user-attachments/assets/8752565f-4216-433d-9178-f370b1538826)

### 图像历史表：

这里是用户生成图像操作的记录：谁，什么时候，用什么生成了什么。

![image](https://github.com/user-attachments/assets/aab67239-8688-4469-9f57-58a335120ed9)

<br><br>

#### 建表语句：

```
CREATE DATABASE SD
USE SD

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `image_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID（外键）',
  `image_name` varchar(255) DEFAULT NULL COMMENT '图片名',
  `generation_time` datetime DEFAULT NULL,
  `prompt` text COMMENT '激励（图片的描述）',
  `negative_prompt` text COMMENT '负面激励',
  `num_inference_steps` int(11) DEFAULT NULL COMMENT '推理步数（次数越多，效果更好）',
  `guidance_scale` decimal(5,2) DEFAULT NULL COMMENT '引导规模（与原来变化差异程度）',
  `width` int(11) DEFAULT NULL COMMENT '宽度',
  `height` int(11) DEFAULT NULL COMMENT '高度',
  `seed` int(11) DEFAULT NULL COMMENT '随机种子',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `image_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

