# 项目说明文档

## 1. Nacos 和 Redis 安装指南介绍

本项目依赖 Nacos 和 Redis 来管理服务发现与配置，以及实现高效的缓存机制。请按照以下步骤安装和配置 Nacos 和 Redis。

## 2. 安装 Nacos

Nacos 是一个开源的动态服务发现、配置管理和服务管理平台。下面是安装 Nacos 的步骤：

### 2.1 下载 Nacos

1. 访问 [Nacos GitHub Releases 页面](https://github.com/alibaba/nacos/releases)。
2. 下载最新版本的 Nacos，通常是 `.zip` 或 `.tar.gz` 文件。

### 2.2 解压并启动 Nacos

1. 解压下载的文件：

   ```sh
   unzip nacos-server-1.x.x.zip
   ```

   或者

   ```sh
   tar -zxvf nacos-server-1.x.x.tar.gz
   ```

2. 进入 Nacos 的目录：

   ```sh
   cd nacos/bin
   ```

3. 启动 Nacos（以开发模式启动）：

   ```sh
   sh startup.sh -m standalone
   ```

   这将启动 Nacos 的单机模式，适用于开发和测试。

### 2.3 访问 Nacos

- 打开浏览器并访问 [http://localhost:8848/nacos](http://localhost:8848/nacos)。
- 默认用户名和密码是 `nacos/nacos`。

### 2.4 配置 Nacos

- 如果需要在生产环境中使用 Nacos，请参考 [Nacos 官方文档](https://nacos.io/zh-cn/docs/quick-start.html) 进行集群配置。

## 3. 安装 Redis

Redis 是一个开源的内存数据结构存储系统，支持多种数据结构。以下是 Redis 的安装步骤：

### 3.1 下载 Redis

1. 访问 [Redis 官方网站](https://redis.io/download)。
2. 下载最新版本的 Redis。

### 3.2 编译和安装 Redis

对于 **Linux** 系统：

1. 解压下载的文件：

   ```sh
   tar xzf redis-6.x.x.tar.gz
   ```

2. 进入 Redis 的目录：

   ```sh
   cd redis-6.x.x
   ```

3. 编译 Redis：

   ```sh
   make
   ```

4. 安装 Redis：

   ```sh
   sudo make install
   ```

### 3.3 启动 Redis

1. 启动 Redis 服务器：

   ```sh
   redis-server
   ```

2. 启动 Redis 客户端（另开一个终端）：

   ```sh
   redis-cli
   ```

   在客户端中可以执行 Redis 命令，比如：

   ```sh
   SET key value
   GET key
   ```

### 3.4 配置 Redis

- Redis 默认配置文件在 `redis.conf`，可以根据需要进行修改。
- 启动 Redis 时可以指定配置文件：

   ```sh
   redis-server /path/to/redis.conf
   ```

## 4. 配置项目中的 `application.yml`

在 `demo-1-spring-security-feign-jwt` 子模块下的 `auth-module` 和 `service-module` 模块中，需修改 `application.yml` 文件，以配置 Nacos 服务器地址。

**1. 修改 `auth-module` 模块和 `service-module` 模块中的 `application.yml` 文件**

找到 `auth-module` 模块下的 `src/main/resources/application.yml` 文件，添加或修改以下配置：

```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 172.18.52.33:8848 # Nacos 服务器地址
```

确保两个模块中的 Nacos 服务器地址配置一致，以保证服务注册和发现功能正常。

## 5. 配置 MySQL

### 5.1 执行 SQL 脚本

在 MySQL 数据库中执行以下 SQL 脚本以创建 RBAC 权限表信息：

1. 打开 MySQL 命令行客户端或数据库管理工具（如 MySQL Workbench）。

2. 执行 `DB/demo-1-spring-security-feign-jwt.sql` 文件中的 SQL 语句：

   ```sql
   -- 如果数据库已存在，则删除数据库 aopquickstart
   DROP DATABASE IF EXISTS aopquickstart;

   -- 创建数据库 aopquickstart
   CREATE DATABASE IF NOT EXISTS aopquickstart;

   -- 使用 aopquickstart 数据库
   USE aopquickstart;

   -- 创建用户表
   CREATE TABLE `users`
   (
       `id`       INT AUTO_INCREMENT PRIMARY KEY, -- 用户ID，自动递增
       `username` VARCHAR(50)  NOT NULL,          -- 用户名，不允许为空
       `password` VARCHAR(100) NOT NULL           -- 密码，不允许为空
   );

   -- 创建角色表
   CREATE TABLE `roles`
   (
       `id`   INT AUTO_INCREMENT PRIMARY KEY, -- 角色ID，自动递增
       `name` VARCHAR(50) NOT NULL            -- 角色名称，不允许为空
   );

   -- 创建权限表
   CREATE TABLE `permissions`
   (
       `id`          INT AUTO_INCREMENT PRIMARY KEY, -- 权限ID，自动递增
       `name`        VARCHAR(50) NOT NULL,           -- 权限名称，不允许为空
       `description` VARCHAR(255)                    -- 权限描述
   );

   -- 创建用户角色关联表
   CREATE TABLE `user_roles`
   (
       `user_id` INT NOT NULL,                                              -- 用户ID
       `role_id` INT NOT NULL,                                              -- 角色ID
       PRIMARY KEY (`user_id`, `role_id`),                                  -- 复合主键
       FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE, -- 外键约束，删除用户时也删除关联
       FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE  -- 外键约束，删除角色时也删除关联
   );

   -- 创建角色权限关联表
   CREATE TABLE `role_permissions`
   (
       `role_id`       INT NOT NULL,                                                   -- 角色ID
       `permission_id` INT NOT NULL,                                                   -- 权限ID
       PRIMARY KEY (`role_id`, `permission_id`),                                       -- 复合主键
       FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,            -- 外键约束，删除角色时也删除关联
       FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE -- 外键约束，删除权限时也删除关联
   );

   -- 插入用户数据
   INSERT INTO `users` (`username`, `password`)
   VALUES ('alice', 'password1'),   -- 用户 alice
          ('bob', 'password2'),     -- 用户 bob
          ('charlie', 'password3'), -- 用户 charlie
          ('david', 'password4'),   -- 用户 david
          ('eve', 'password5');     -- 用户 eve

   -- 插入角色数据
   INSERT INTO `roles` (`name`)
   VALUES ('ADMIN'),     -- 角色 ADMIN
          ('USER'),      -- 角色 USER
          ('MODERATOR'), -- 角色 MODERATOR
          ('GUEST');     -- 角色 GUEST

   -- 插入权限数据
   INSERT INTO `permissions` (`name`, `description`)
   VALUES ('READ_PRIVILEGES', '可以读取数据'),   -- 权限 READ_PRIVILEGES
          ('WRITE_PRIVILEGES', '可以写入数据'),  -- 权限 WRITE_PRIVILEGES
          ('DELETE_PRIVILEGES', '可以删除数据'), -- 权限 DELETE_PRIVILEGES
          ('UPDATE_PRIVILEGES', '可以更新数据'); -- 权限 UPDATE_PRIVILEGES

   -- 插入用户角色关联数据
   INSERT INTO `user_roles` (`user_id`, `role_id`)
   VALUES (1, 1), -- 用户ID 1 (alice) 关联角色ID 1 (ADMIN)
          (2, 2), -- 用户ID 2 (bob) 关联角色ID 2 (USER)
          (3, 3), -- 用户ID 3 (charlie) 关联角色ID 3 (MODERATOR)
          (4, 4), -- 用户ID 4 (david) 关联角色ID 4 (GUEST)
          (5, 2); -- 用户ID 5 (eve) 关联角色ID 2 (USER)

   -- 插入角色权限关联数据
   INSERT INTO `role_permissions` (`role_id`, `permission_id`)
   VALUES (1, 1), -- 角色ID 1 (ADMIN) 拥有权限ID 1 (READ_PRIVILEGES)
          (1, 2), -- 角色ID 1 (ADMIN) 拥有权限ID 2 (WRITE_PRIVILEGES)
          (1, 3), -- 角色ID 1 (ADMIN) 拥有权限ID 3 (DELETE_PRIVILEGES)
          (1, 4), -- 角色ID 1 (ADMIN) 拥有权限ID 4 (UPDATE_PRIVILEGES)
          (2, 1), -- 角色ID 2 (USER) 拥有权限ID 1 (READ_PRIVILEGES)
          (3, 1), -- 角色ID 3 (MODERATOR) 拥有权限ID 1 (READ_PRIVILEGES)
          (3, 4), -- 角色ID 3 (MODERATOR) 拥有权限ID 4 (UPDATE_PRIVILEGES)
          (4, 1); -- 角色ID 4 (GUEST) 拥有权限ID 1 (READ_PRIVILEGES)
   ```

### 5.2 修改 `auth-module` 模块 和 `service-module` 模块中的 `application.yml`

   ```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database_name?useSSL=false&serverTimezone=UTC
    # 数据库连接 URL，`your_database_name` 替换为实际的数据库名
    username: your_database_username
    # 数据库用户名
    password: your_database_password
    # 数据库密码
    driver-class-name: com.mysql.cj.jdbc.Driver
    # MySQL 数据库驱动类名
  mybatis-plus:
    mapper-locations: classpath:/mapper/**/*.xml
    # MyBatis-Plus 的 Mapper XML 文件位置，请根据实际路径修改
    global-config:
      db-config:
        id-type: auto
        # ID 策略，`auto` 表示自增
        field-strategy: not_empty
        # 字段策略，`not_empty` 表示只对非空字段进行更新
        logic-delete-value: 1
        # 逻辑删除的值，`1` 表示已删除
        logic-not-delete-value: 0
        # 逻辑未删除的值，`0` 表示未删除
    configuration:
      log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
      # 日志实现类，`org.apache.ibatis.logging.stdout.StdOutImpl` 表示标准输出日志
  jpa:
    hibernate:
      ddl-auto: update
      # Hibernate 的 DDL 自动更新策略，`update` 表示自动更新数据库结构
    show-sql: true
    # 显示 SQL 语句
   ```

## 6. 配置项目中的 `application.yml`

在 `demo-1-spring-security-feign-jwt` 子模块下的 `auth-module` 和 `service-module` 模块中，需修改 `application.yml` 文件，以配置 Nacos 服务器地址。

**1. 修改 `auth-module` 模块和 `service-module` 模块中的 `application.yml` 文件**

找到 `auth-module` 模块下的 `src/main/resources/application.yml` 文件，添加或修改以下配置：

```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 172.18.52.33:8848 # Nacos 服务器地址
```

确保两个模块中的 Nacos 服务器地址配置一致，以保证服务注册和发现功能正常。

## 7. 运行测试

在确保所有服务和配置都已正确安装和配置后，可以运行 Maven 测试来验证项目的功能和稳定性。

### 7.1 执行测试

1. 打开终端并导航到项目的根目录。
2. 运行以下 Maven 命令来执行所有测试：

   ```sh
   mvn test
   ```

   该命令将编译项目并运行所有单元测试和集成测试，以确保项目的各个部分按照预期工作。

### 7.2 检查测试结果

- 测试结果将显示在终端中。
- 如果所有测试都通过，您将看到类似 `BUILD SUCCESS` 的信息。
- 如果有测试失败，检查终端中的错误信息，定位问题并进行修复。

确保在执行测试之前，Nacos 和 Redis 服务已经启动并运行，数据库也已经配置正确。

---

## 8. 运行 `demo-3-spring-authorization-server` 模块

该模块依赖于 Spring Boot 3.1.3，并需要 Java 17 支持。请按照以下步骤运行该模块：



### 8.1 切换 JDK 版本

确保您使用的 JDK 版本为 Java 17。如果当前 JDK 版本不是 Java 17，请切换到 Java 17。可以使用以下命令检查当前 JDK 版本：

```sh
java -version
```

### 8.2 配置 `application.yml`

1. 在 `demo-3-spring-authorization-server` 模块中，确保 `src/main/resources/application.yml` 文件包含必要的配置。该模块通常只需添加依赖和配置文件即可运行。

### 8.3 了解 OAuth2 的基本概念和认证流程

在运行 `demo-3-spring-authorization-server` 模块之前，请了解 OAuth2 的基本概念和认证流程，以便更好地理解和配置授权服务器。OAuth2 是一种授权框架，主要用于第三方应用程序安全访问用户的资源。常见的认证流程包括授权码模式、隐式模式、密码模式和客户端凭证模式。

#### 8.4 调整依赖

#### 8.4.1 让该模块依赖于 Spring Boot 3.1.3

 打开文件 [顶级父项目的pom文件](pom.xml)注释 <u>模块三运行步骤</u>，让该模块依赖于 Spring Boot 3.1.3

```xml
<!-- 1/3  模块三运行步骤：打开此处parent -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.3</version>
</parent>

<!-- 2/3  模块三运行步骤：注释此处parent -->
<!--    <parent>-->
<!--        <groupId>org.springframework.boot</groupId>-->
<!--        <artifactId>spring-boot-starter-parent</artifactId>-->
<!--        <version>2.6.13</version>-->
<!--    </parent>-->
```

### 8.4.1 让该模块被父项目管理

打开[demo-3-spring-authorization-server/pom.xml](demo-3-spring-authorization-server/pom.xml)注释

```xml
<!-- 3/3  模块三运行步骤 -->
<parent>
    <groupId>com.maizi</groupId>
    <artifactId>spring-security-quick-start</artifactId>
    <version>1.0-SNAPSHOT</version>
</parent>
```

#### 8.4.2 调整项目模块的java版本 ，IDEA的Java版本为 17。

运行！

## 9.测试

测照[demo-3-spring-authorization-server/readme.md](demo-3-spring-authorization-server/readme.md)