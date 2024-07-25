spring-security-quick-start项目包含三个模块

* auth-module  登录及其用户管理，角色管理，权限管理。
* common-module
    * 公共的抽取的模块 共用对象，
    * 及其最重要的权限，其他模块只需要添加此模块并且<u>修改[SecurityConfig.java](https://github.com/wangming-github/spring-security-quick-start/blob/4e26d93453ed89c09d9cd92cda2182b0f0cd7dbe/demo-1-spring-security-feign-jwt/common-module/src/main/java/com/maizi/common/security/SecurityConfig.java)中的配置即可实现权限管理。</u>
    * 更详细的解释参看common-module 内部readme
* service-module
    * 提供给auth-module模块用来模拟<u>**feign附带token的远程调用**</u>。


