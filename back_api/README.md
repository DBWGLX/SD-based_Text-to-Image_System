效果演示：
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

