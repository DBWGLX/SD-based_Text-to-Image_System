登录页效果演示：

![image](https://github.com/user-attachments/assets/9abdeefd-656e-49bd-a578-fcc29892b699)


一.数据库设计
back.sql文件概述

表结构及数据

 表: `admins`

- **描述**: 该表用于存储系统管理员的信息。
- **字段**:
  - `id`: 主键, 自动递增, 大整型 (bigint).
  - `username`: 帐号, 字符串类型 (varchar), 最大长度为100.
  - `password`: 密码, 字符串类型 (varchar), 最大长度为100.
- **备注**:
  - 表注释: 管理员
  - 表引擎: InnoDB
  - 字符集: utf8mb4
  - 行格式: 动态
  - 默认值: id字段从2开始自动递增

 数据记录

- **示例数据**:
  - ID: 1
  - 用户名: admin
  - 密码: 12345678

使用说明

1. **导入前准备**:
   - 确保目标MySQL服务器已经启动，并且可以连接。
   - 根据需要调整数据库名称、用户名和密码等连接参数。
   - 如果存在同名数据库，请先备份或删除旧数据库，以免数据丢失。

2. **执行导入**:
   - 登录到MySQL命令行客户端或者使用图形界面工具如Navicat、phpMyAdmin等。
   - 创建一个新的数据库（如果还没有创建的话）。
   - 使用`source`命令执行该SQL文件，例如：
     ```sql
     source /path/to/your/sql/file.sql;
     ```
   - 或者在图形界面工具中选择运行SQL文件。

3. **验证结果**:
   - 完成导入后，可以通过查询`admins`表来验证数据是否正确无误地导入到了新的数据库中。
![image](https://github.com/user-attachments/assets/d0ce4093-682d-4fd6-ab4b-eb023798cb18)


二.
# MybatisConfigurer 配置类简介

## 功能概述

`MybatisConfigurer` 是一个Spring Boot配置类，主要用于配置MyBatis、Mapper和PageHelper。以下是其主要功能和代码结构的简要介绍。

## 主要功能

1. **SqlSessionFactory配置**
   - 设置数据源。
   - 设置实体类包。
   - 配置分页插件PageHelper。
   - 指定映射文件位置。

2. **MapperScannerConfigurer配置**
   - 设置SqlSessionFactory的Bean名称。
   - 设置Mapper接口包。
   - 配置通用Mapper属性。

## 代码结构

### SqlSessionFactory配置

```java
@Bean
public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    factory.setDataSource(dataSource);
    factory.setTypeAliasesPackage(ProjectConstant.MODEL_PACKAGE);

    PageHelper pageHelper = new PageHelper();
    Properties properties = new Properties();
    properties.setProperty("pageSizeZero", "true");
    properties.setProperty("reasonable", "true");
    properties.setProperty("supportMethodsArguments", "true");
    pageHelper.setProperties(properties);

    factory.setPlugins(new Interceptor[]{pageHelper});
    factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
    return factory.getObject();
}
```

### MapperScannerConfigurer配置

```java
@Bean
public MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
    mapperScannerConfigurer.setBasePackage(ProjectConstant.MAPPER_PACKAGE);

    Properties properties = new Properties();
    properties.setProperty("mappers", ProjectConstant.MAPPER_INTERFACE_REFERENCE);
    properties.setProperty("notEmpty", "false");
    properties.setProperty("IDENTITY", "MYSQL");
    mapperScannerConfigurer.setProperties(properties);

    return mapperScannerConfigurer;
}
```

## 关键点

- **数据源**：配置数据源连接。
- **实体类包**：设置实体类所在包。
- **分页插件**：配置PageHelper支持分页。
- **映射文件**：指定XML映射文件位置。
- **Mapper接口包**：设置Mapper接口所在包。
- **通用Mapper**：配置通用Mapper属性。
```

（未完）

```
N.
# 前端交互与后端接口

## 1. 对应登录组件 (Login.vue)

![Login.vue](https://github.com/user-attachments/assets/b97b85f9-fcb0-4016-950a-8f4733d7582b)

该代码段位于 `FrontEnd/vue-project/src/components/Login.vue` 文件中，主要实现了登录功能。以下是关键部分的详细说明：

### 解释

- **登录函数 (`login()`):**
  - 首先检查用户名和密码是否为空。
  - 如果不为空，则调用 `loginApi` 函数进行登录操作。

- **登录 API 调用 (`loginApi`):**
  - 发送包含用户名、密码和角色（role）的数据对象给服务器。
  
- **处理响应 (`then`):**
  - 成功时：
    - 从响应数据中提取 `token` 和 `user` 对象。
    - 将 `token` 存储在本地存储中，键为 `"accessToken"`。
    - 将登录状态设置为 `true` 并存储在本地存储中，键为 `'isAuthenticated'`。
    - 使用 Vue Router 的 `$router.push` 方法将用户重定向到 `/app` 页面。

- **错误处理 (`catch`):**
  - 失败时，将错误信息赋值给 `this.errorMessage` 变量。

通过以上步骤，可以实现一个基本的登录功能，并在登录成功后自动跳转至指定页面。

## 2. index.js 文件

![index.js](https://github.com/user-attachments/assets/174dc65a-b287-4941-9c85-1bd72e5bb983)

### 代码解释

这段代码是一个Vue.js项目的API模块中的登录接口实现。它使用了axios库来发起HTTP请求。

1. **导入request函数：**
   ```js
   import request from '@/utils/request';
   ```
   这里导入了一个名为`request`的函数，这个函数通常封装了axios的基本配置，用于简化HTTP请求的操作。

2. **定义loginApi函数：**
   ```js
   export function loginApi(data) {
     return request({
       url: '/auth/login',
       method: 'POST',
       params: data
     });
   }
   ```
   - **function loginApi(data)**: 定义了一个名为`loginApi`的导出函数，接受一个参数`data`，这个参数通常是包含了用户名和密码的对象。
   - **return request({ ... })**: 返回一个Promise对象，表示异步请求的结果。
   - **url**: 请求的目标URL是`/auth/login`，这是后端服务提供的登录接口地址。
   - **method**: 请求的方法是`POST`，因为登录通常需要提交表单数据。
   - **params**: 请求携带的参数，这里直接传入`data`参数，意味着前端会将用户的登录信息作为请求体的一部分发送给后端。

3. **用途：**
   这个`loginApi`函数主要用于向后端发送登录请求。当用户填写完登录表单并点击“登录”按钮时，前端会调用这个函数，传递用户的登录信息（如用户名和密码），然后等待后端返回的响应结果。

   
```

```
3.
# AuthController 代码简介


![ff353f7e75a63c9a832b582e3f28be1](https://github.com/user-attachments/assets/f76bfb4d-23db-4418-9053-9bbfeb931928)

## 类名：AuthController

这是一个Java控制器类，负责处理与认证相关的HTTP请求。

## 方法：login

### 描述
- 此方法处理POST请求，路径为`/login`。
- 接收两个参数：`username`（用户名）和`password`（密码）。

### 功能
1. **查询用户**：
   - 使用`adminService`查询数据库中是否存在对应的用户名。
   
2. **验证密码**：
   - 检查获取到的用户对象的密码是否与输入的密码相匹配。
   - 若用户名不存在或密码不匹配，返回失败结果。

3. **创建用户对象**：
   - 创建一个新的`User`对象，并将其ID和用户名设置为从数据库中获取的值。

4. **生成令牌**：
   - 使用`TokenUtil`工具类生成一个令牌（token），并将令牌添加到结果映射中。

5. **返回结果**：
   - 若一切正常，返回成功结果，并附带生成的令牌。
   - 若发生异常，捕获异常并打印堆栈跟踪。

### 代码示例
```
public class AuthController {

    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        Admins admins = adminService.findByUsername(username);
        if (admins == null || !admins.getPassword().equals(password)) {
            return ResultGenerator.genFailResult("用户名或密码错误");
        }

        User user = new User();
        user.setId(admins.getId());
        user.setUsername(admins.getUsername());

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("token", TokenUtil.createToken(user.getId() + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(map).setMessage("登录成功");
    }
}
```
