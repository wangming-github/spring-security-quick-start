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
│  │  │  │  │  │  │  ├─RRException.java
│  │  │  │  │  │  │  ├─RRExceptionHandler.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  feign/
│  │  │  │  │  │  │  ├─FeignConfig.java
│  │  │  │  │  │  │  ├─FeignErrorDecoder.java
│  │  │  │  │  │  │  ├─FeignInterceptor.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  o/
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  bo/
│  │  │  │  │  │  │  │  ├─（Business Object）：  BO是指业务对象，用于表示业务逻辑处理中的对象。
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  constants/
│  │  │  │  │  │  │  │  ├─ModuleType.java
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  dto/
│  │  │  │  │  │  │  │  ├─UserDTO.java
│  │  │  │  │  │  │  │  ├─UserDetailsDTO.java
│  │  │  │  │  │  │  │  ├─（Data Transfer Object）：  DTO用于在不同层（例如业务层和持久层）或者不同服务之间传输数据。
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  po/
│  │  │  │  │  │  │  │  ├─（Persistent Object）：  PO是持久化对象，通常与数据库中的表结构映射，用于表示数据库中的数据记录。
│  │  │  │  │  │  │  ├─
│  │  │  │  │  │  │  │  pojo/
│  │  │  │  │  │  │  │  ├─（Plain Old Java Object）：  POJO是一个普通的Java对象，它不依赖于特定框架或技术，也不实现特定接口。
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  redis/
│  │  │  │  │  │  │  ├─JwtUtil.java
│  │  │  │  │  │  │  ├─RedisConfig.java
│  │  │  │  │  │  │  ├─RedisUtil.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  security/
│  │  │  │  │  │  │  ├─AccessDeniedHandler.java
│  │  │  │  │  │  │  ├─AuthenticationEntryPoint.java
│  │  │  │  │  │  │  ├─JwtAuthenticationTokenFilter.java
│  │  │  │  │  │  │  ├─SecurityConfig.java
│  │  │  │  │  │  │  ├─SecurityUserUserDetails.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  utils/
│  │  │  │  │  │  │  ├─Constant.java
│  │  │  │  │  │  │  ├─PageUtils.java
│  │  │  │  │  │  │  ├─QueryUtils.java
│  │  │  │  │  │  │  ├─R.java
│  │  │  │  │  │  ├─
│  │  │  │  │  │  │  xss/
│  │  │  │  │  │  │  ├─SQLFilter.java
│  ├─
│  │  test/
│  │  ├─
│  │  │  java/
```