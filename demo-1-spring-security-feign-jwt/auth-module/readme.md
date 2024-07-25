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
│  │  │  │  │  │  ├─AuthorApplication.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  config/
│  │  │  │  │  │  │  ├─LoginUserDetailsService.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  controller/
│  │  │  │  │  │  │  ├─LoginController.java
│  │  │  │  │  │  │  ├─TestAuthorizeController.java
│  │  │  │  │  │  │  ├─TestFeignServiceController.java
│  │  │  │  │  │  │  ├─TestLocalServiceController.java
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
│  │  │  │  │  │  │  ├─FeignService.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  service/
│  │  │  │  │  │  │  ├─LoginService.java
│  │  │  │  │  │  │  ├─PermissionsService.java
│  │  │  │  │  │  │  ├─RolePermissionsService.java
│  │  │  │  │  │  │  ├─RolesService.java
│  │  │  │  │  │  │  ├─UserRolesService.java
│  │  │  │  │  │  │  ├─UsersService.java
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  impl/
│  │  │  │  │  │  │  │  ├─LoginServiceImpl.java
│  │  │  │  │  │  │  │  ├─PermissionsServiceImpl.java
│  │  │  │  │  │  │  │  ├─RolePermissionsServiceImpl.java
│  │  │  │  │  │  │  │  ├─RolesServiceImpl.java
│  │  │  │  │  │  │  │  ├─UserRolesServiceImpl.java
│  │  │  │  │  │  │  │  ├─UsersServiceImpl.java
│  │  ├─
│  │  │  resources/
│  │  │  ├─application.yml
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

======================================
项目目录结构：
./
├─HELP.md
├─pom.xml
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
│  │  │  │  │  ├─AuthorizationModuleApplication.java 应用程序的入口类，配置Mybatisplus的dao包扫描路径
│  │  │  │  │  ├─
│  │  │  │  │  │  author
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  config/
│  │  │  │  │  │  │  ├─JwtAuthenticationTokenFilter.java 定义Jwt过滤器，当携带token的用户第二次登陆时进行校验
│  │  │  │  │  │  │  ├─SecurityConfig.java springboot security的配置类
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  controller/
│  │  │  │  │  │  │  ├─AuthController.java 登录、登出接口
│  │  │  │  │  │  │  ├─TestController.java 模拟用户登录成功后的携带token的二次请求
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  授权/
│  │  │  │  │  │  │  │  ├─ApiController.java 定义不同角色访问不同端点的权限控制逻辑
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  exception/
│  │  │  │  │  │  │  ├─MyAccessDeniedHandler.java 处理访问被拒绝的情况
│  │  │  │  │  │  │  ├─MyAuthenticationEntryPoint.java 处理未经身份验证用户访问受保护资源的情况
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  module/
│  │  │  │  │  │  │  ├─CustomUserUserDetails.java 用于封装从数据库查询出的用户信息及其角色和权限。
│  │  │  │  │  │  │  ├─UserOfRequest.java 接收用户请求对象
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  service/
│  │  │  │  │  │  │  ├─AuthorizeTestService.java 接口
│  │  │  │  │  │  │  ├─LoginService.java 接口
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  impl/
│  │  │  │  │  │  │  │  ├─CustomUserDetailsService.java 用于根据用户名从数据库中查询用户的详细信息，包括用户信息、角色和权限，并将这些信息封装成一个实现了 UserDetails 接口的对象，供 Spring Security 进行后续的用户认证和授权操作。
│  │  │  │  │  │  │  │  ├─LoginServiceImpl.java 用户认证，并将认证信息存储到reids。提供注销功能，从 SecurityContextHolder 中获取当前登录用户信息，并从 Redis 中移除该用户信息。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  utils/
│  │  │  │  │  │  │  ├─JwtUtil.java Jwt工具类
│  │  │  │  │  │  │  ├─RedisServiceUtil.java 模拟Redis的工具
│  │  │  │  │  ├─
│  │  │  │  │  │  serve/ 提供用户、角色、权限的增删改查
│  │  test/
│  │  ├─
│  │  │  java/
│  │  │  ├─
│  │  │  │  com/
│  │  │  │  ├─
│  │  │  │  │  maizi/
│  │  │  │  │  ├─
│  │  │  │  │  │  authorizationmodule/
│  │  │  │  │  │  ├─AuthorizationModuleApplicationTests.java 
│  │  │  │  │  │  ├─PasswordInit.java 对明文密码进行加密，然后更新到原数据上。
│  │  │  │  │  │  ├─AllMatchExample.java 检查List中是否包含NULL元素

```