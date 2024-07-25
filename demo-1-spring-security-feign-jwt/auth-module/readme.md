```txt
项目目录结构：
./
├─HELP.md
├─pom.xml
├─readme.md
├─
│  src/
│  ├─
│  │  main/
│  │  ├─
│  │  │  java/
│  │  │  ├─TODO.md
│  │  │  ├─
│  │  │  │  com/
│  │  │  │  ├─
│  │  │  │  │  maizi/
│  │  │  │  │  ├─
│  │  │  │  │  │  author/
│  │  │  │  │  │  ├─AuthorApplication.java        该类配置了 Spring Boot 应用程序，并启用了 Feign 客户端、服务发现和组件扫描。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  config/
│  │  │  │  │  │  │  ├─LoginUserDetailsService.java        自定义用户详细信息服务类，用于登录时从数据库或其他来源加载用户信息以供 Spring Security 进行身份验证和授权。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  controller/
│  │  │  │  │  │  │  ├─LoginController.java        该控制器处理用户的登录和登出请求。
│  │  │  │  │  │  │  ├─TestAuthorizeController.java        使用 Spring Security 的注解来控制访问权限。
│  │  │  │  │  │  │  ├─TestFeignServiceController.java         这个控制器类用于处理与 Feign 服务相关的 HTTP 请求，并将请求转发到相应的 Feign 客户端接口。
│  │  │  │  │  │  │  ├─TestLocalServiceController.java        本地服务测试控制器
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  other/
│  │  │  │  │  │  │  │  ├─PermissionsController.java       
│  │  │  │  │  │  │  │  ├─RolePermissionsController.java       
│  │  │  │  │  │  │  │  ├─RolesController.java       
│  │  │  │  │  │  │  │  ├─UserRolesController.java       
│  │  │  │  │  │  │  │  ├─UsersController.java       
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  dao/
│  │  │  │  │  │  │  ├─PermissionsDao.java       
│  │  │  │  │  │  │  ├─RolePermissionsDao.java       
│  │  │  │  │  │  │  ├─RolesDao.java       
│  │  │  │  │  │  │  ├─UserRolesDao.java       
│  │  │  │  │  │  │  ├─UsersDao.java       
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  entity/
│  │  │  │  │  │  │  ├─PermissionsEntity.java       
│  │  │  │  │  │  │  ├─RolePermissionsEntity.java       
│  │  │  │  │  │  │  ├─RolesEntity.java       
│  │  │  │  │  │  │  ├─UserRolesEntity.java       
│  │  │  │  │  │  │  ├─UserWithRolesAndPermissions.java       
│  │  │  │  │  │  │  ├─UsersEntity.java       
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  feign/
│  │  │  │  │  │  │  ├─FeignService.java        Feign 客户端接口，用于与服务模块进行通信。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  service/
│  │  │  │  │  │  │  ├─LoginService.java        登录服务接口
│  │  │  │  │  │  │  ├─PermissionsService.java       
│  │  │  │  │  │  │  ├─RolePermissionsService.java       
│  │  │  │  │  │  │  ├─RolesService.java       
│  │  │  │  │  │  │  ├─UserRolesService.java       
│  │  │  │  │  │  │  ├─UsersService.java       
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  impl/
│  │  │  │  │  │  │  │  ├─LoginServiceImpl.java        登录服务实现类 处理用户登录、登出操作，并提供获取用户角色和权限的方法。
│  │  │  │  │  │  │  │  ├─PermissionsServiceImpl.java       
│  │  │  │  │  │  │  │  ├─RolePermissionsServiceImpl.java       
│  │  │  │  │  │  │  │  ├─RolesServiceImpl.java       
│  │  │  │  │  │  │  │  ├─UserRolesServiceImpl.java       
│  │  │  │  │  │  │  │  ├─UsersServiceImpl.java       
│  │  ├─
│  │  │  resources/
│  │  │  ├─application.yml 这是 Spring Boot 项目的配置文件 application.yml，包含了数据库连接、Redis 配置、MyBatis-Plus 配置、日志级别等设置。
│  │  │  ├─
│  │  │  │  mapper/
│  │  │  │  ├─
│  │  │  │  │  generator/
│  │  │  │  │  ├─PermissionsDao.xml
│  │  │  │  │  ├─RolePermissionsDao.xml
│  │  │  │  │  ├─RolesDao.xml
│  │  │  │  │  ├─UserRolesDao.xml
│  │  │  │  │  ├─UsersDao.xml
│  ├─


```