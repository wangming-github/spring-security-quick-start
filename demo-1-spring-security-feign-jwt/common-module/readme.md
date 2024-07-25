```txt
项目目录结构：

./
├─pom.xml
├─
│  src/
│  ├─
│  │  main/
│  │  ├─
│  │  │  java/
│  │  │  ├─
│  │  │  │  com/
│  │  │  │  ├─
│  │  │  │  │  maizi/
│  │  │  │  │  ├─
│  │  │  │  │  │  common/
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  exception/
│  │  │  │  │  │  │  ├─RRException.java      自定义异常类，用于封装应用程序中发生的运行时异常。
│  │  │  │  │  │  │  ├─RRExceptionHandler.java      全局异常处理器，用于处理应用程序中的各种异常。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  feign/
│  │  │  │  │  │  │  ├─FeignConfig.java      Feign客户端配置类
│  │  │  │  │  │  │  ├─FeignErrorDecoder.java      自定义 Feign 错误解码器
│  │  │  │  │  │  │  ├─FeignInterceptor.java       一个用于拦截 Feign 请求并添加当前 HTTP 请求头信息的拦截器。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  o/
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  bo/ （Business Object）：  BO是指业务对象，用于表示业务逻辑处理中的对象。
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  constants/常量
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  dto/（Data Transfer Object）：  DTO用于在不同层（例如业务层和持久层）或者不同服务之间传输数据。
│  │  │  │  │  │  │  │  ├─UserDTO.java
│  │  │  │  │  │  │  │  ├─UserDetailsDTO.java
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  po/（Persistent Object）：  PO是持久化对象，通常与数据库中的表结构映射，用于表示数据库中的数据记录。
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  pojo/（Plain Old Java Object）：  POJO是一个普通的Java对象，它不依赖于特定框架或技术，也不实现特定接口。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  redis/
│  │  │  │  │  │  │  ├─JwtUtil.java      一个用于生成和验证 JWT 的工具类。
│  │  │  │  │  │  │  ├─RedisConfig.java      该类配置了 Spring Data Redis 的 RedisTemplate，以便进行 Redis 操作。
│  │  │  │  │  │  │  ├─RedisUtil.java      该类封装了对 Redis 操作的常用方法，提供了对 Redis 中数据的设置、获取、删除和其他操作。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  security/
│  │  │  │  │  │  │  ├─AccessDeniedHandler.java       该类实现了 Spring Security 的 AccessDeniedHandler 接口，用于处理用户访问被拒绝的情况。
│  │  │  │  │  │  │  ├─AuthenticationEntryPoint.java      该类实现了 Spring Security 的 AuthenticationEntryPoint 接口，用于处理认证异常。
│  │  │  │  │  │  │  ├─JwtAuthenticationTokenFilter.java      自定义 JWT 过滤器，用于拦截并验证请求中的 JWT。
│  │  │  │  │  │  │  ├─SecurityConfig.java          配置 Spring Security 的安全设置。
│  │  │  │  │  │  │  ├─SecurityUserUserDetails.java      一个实现了 Spring Security 的 UserDetails 接口，用于封装从数据库查询出的用户信息及其角色和权限。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  utils/
│  │  │  │  │  │  │  ├─Constant.java      一些常用的常量，例如分页参数、排序字段及排序方式等，用于简化代码中的硬编码，提供统一的常量引用。
│  │  │  │  │  │  │  ├─PageUtils.java      分页工具类
│  │  │  │  │  │  │  ├─QueryUtils.java      查询参数工具类，用于构建分页和排序的 MyBatis-Plus {@link IPage} 对象。
│  │  │  │  │  │  │  ├─R.java      自定义响应对象，用于封装 API 响应的数据。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  xss/
│  │  │  │  │  │  │  ├─SQLFilter.java     SQL 过滤工具类，用于防止 SQL 注入攻击。
            
```