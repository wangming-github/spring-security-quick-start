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
