登录页效果演示：

![image](https://github.com/user-attachments/assets/9abdeefd-656e-49bd-a578-fcc29892b699)


### 程序处理逻辑及接口说明

#### 1. `AuthController` 类

**功能描述**：
`AuthController` 是一个处理用户认证的控制器，主要负责用户的登录操作。它通过接收前端传递的用户名和密码，验证用户身份，并生成一个JWT（JSON Web Token）返回给前端。

**接口**：
- **POST /auth/login**：
  - **请求参数**：
    - `username`（必需）：用户输入的用户名。
    - `password`（必需）：用户输入的密码。
  - **响应**：
    - 成功登录时，返回一个包含JWT的JSON对象，格式如下：
      ```json
      {
        "code": 200,
        "message": "登录成功",
        "data": {
          "token": "generated_token"
        }
      }
      ```
    - 登录失败时，返回相应的错误信息，例如：
      ```json
      {
        "code": 400,
        "message": "用户名或密码错误"
      }
      ```

**处理逻辑**：
1. 从请求中获取用户名和密码。
2. 调用 `AdminsService` 中的 `findByUsername` 方法查询数据库，查找对应的管理员用户。
3. 如果用户存在，验证密码是否正确。
4. 如果密码正确，创建一个 `User` 对象，包含用户的ID和用户名。
5. 使用 `TokenUtil` 生成一个JWT，并将其放入响应的 `data` 字段中。
6. 返回结果，包括状态码、消息和数据。

#### 2. `TokenInterceptor` 类

**功能描述**：
`TokenInterceptor` 是一个拦截器，用于在每个请求到达控制器之前验证请求中的JWT。如果验证通过，将用户信息设置到请求属性中；如果验证失败，返回相应的错误信息。

**处理逻辑**：
1. 从请求头中获取 `Authorization` 字段的值，即JWT。
2. 检查JWT是否为空，如果为空，返回 `TOKEN_ERROR` 错误信息。
3. 使用 `TokenUtil` 解析JWT，获取用户ID和过期时间。
4. 检查用户ID和过期时间是否为空，如果为空，返回 `TOKEN_ERROR` 错误信息。
5. 调用 `AdminsService` 中的 `findById` 方法查询数据库，查找对应的管理员用户。
6. 如果用户存在，创建一个 `User` 对象，包含用户的ID和用户名。
7. 检查当前时间是否在JWT的过期时间之后，如果是，返回 `TOKEN_ERROR` 错误信息。
8. 将用户信息设置到请求属性中，以便后续的控制器方法使用。
9. 记录用户登录信息。

**错误处理**：
- 如果JWT验证失败，调用 `retResponse` 方法返回错误信息，格式如下：
  ```json
  {
    "code": 401,
    "message": "Token无效或已过期"
  }
  ```

### 如何与前端进行交互

1. **登录流程**：
   - 前端发送一个包含用户名和密码的POST请求到 `/auth/login`。
   - 后端验证用户名和密码，生成JWT并返回给前端。
   - 前端收到JWT后，将其存储在本地（例如，浏览器的 `localStorage` 或 `sessionStorage`）。

2. **后续请求**：
   - 前端在每次发送请求时，将JWT作为 `Authorization` 头字段的一部分发送。
   - 后端的 `TokenInterceptor` 拦截器验证JWT的有效性，如果验证通过，继续处理请求；如果验证失败，返回相应的错误信息。

通过这种方式，前后端实现了基于JWT的用户认证和授权机制。



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

```

```
4.
# TokenInterceptor 代码简要介绍


<img width="624" alt="72f8b172035be863a482e7dce28afb0" src="https://github.com/user-attachments/assets/30a761da-e7ee-4fd4-a7d8-82e835da7f5e">



## 

## 类名：TokenInterceptor

### 注解

- `@Component`: 标记为Spring管理的Bean。
- `@Slf4j`: 引入Lombok的日志功能。

### 字段

- `@Resource private AdminsService adminsService;`: 通过Spring注入`AdminsService`服务。

### 方法

#### `preHandle`

- **描述**:
  - 在请求处理之前执行，用于验证请求头中的令牌。
  - 如果令牌无效或过期，返回错误响应。
  - 如果令牌有效，将用户信息设置到请求属性中。

- **逻辑**:
  1. 获取请求头中的`Authorization`字段。
  2. 检查令牌是否为空。
  3. 从令牌中解析用户ID和过期时间。
  4. 检查用户ID和过期时间是否为空。
  5. 查询数据库以获取用户信息。
  6. 检查用户是否存在。
  7. 比较当前时间和令牌的过期时间。
  8. 如果令牌有效，将用户信息设置到请求属性中。
  9. 记录用户登录信息。

- **代码示例**:
  ```java
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      String token = request.getHeader("Authorization");
      if (StringUtils.isEmpty(token)) {
          retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
          return false;
      }
      String userId = TokenUtil.getAcUserId(token);
      Date expTime = TokenUtil.getExpTime(token);
      if (userId == null || expTime == null) {
          retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
          return false;
      }
      User tuUser = null;
      Admins admins = adminsService.findById(userId);
      if (admins != null) {
          tuUser = new User();
          tuUser.setId(admins.getId());
          tuUser.setUsername(admins.getUsername());
      }
      if (tuUser == null) {
          retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
          return false;
      }
      int compare = DateUtils.truncatedCompareTo(new Date(), expTime, Calendar.MILLISECOND);
      if (compare == 1 || compare == 0) {
          retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
          return false;
      }
      request.setAttribute("loginUser", tuUser);
      log.info("已登录,用户信息为{}", tuUser.getUsername());
      return true;
  }
  ```

#### `retResponse`

- **描述**:
  - 用于构建并返回错误响应。

- **逻辑**:
  1. 创建一个`Result`对象，设置状态码和消息。
  2. 设置响应的字符编码和内容类型。
  3. 将结果转换为JSON字符串并写入响应。

- **代码示例**:
  ```java
  private void retResponse(HttpServletResponse response, ResultCode code, String message) throws IOException {
      Result result = new Result();
      result.setCode(code).setMessage(message);
      response.setCharacterEncoding("UTF-8");
      response.setHeader("Content-type", "application/json;charset=UTF-8");
      response.setStatus(200);
      response.getWriter().write(JSON.toJSONString(result));
  }
  ```


```
```
![63c1fef92559bd63a97f4a34c78d722](https://github.com/user-attachments/assets/1d4b91c8-70bf-4df9-8307-b50698e1f0f3)
```
