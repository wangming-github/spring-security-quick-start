
```java
2024-07-12 18:16:26.483 ERROR 15719 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'customUserDetailsService': Unsatisfied dependency expressed through field 'usersService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'usersService': Unsatisfied dependency expressed through field 'baseMapper'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'usersDao' defined in file [/Users/maizi/Documents/0.Quick-Start/AOP-Quick-Start/authorization-module/target/classes/com/maizi/service/dao/UsersDao.class]: Unsatisfied dependency expressed through bean property 'sqlSessionFactory'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'sqlSessionFactory' defined in class path resource [com/baomidou/mybatisplus/autoconfigure/MybatisPlusAutoConfiguration.class]: Unsatisfied dependency expressed through method 'sqlSessionFactory' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Generic.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.sql.DataSource]: Factory method 'dataSource' threw exception; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.util.DriverDataSource]: No default constructor found; nested exception is java.lang.NoSuchMethodException: com.zaxxer.hikari.util.DriverDataSource.<init>()
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.resolveFieldValue(AutowiredAnnotationBeanPostProcessor.java:659) ~[spring-beans-5.3.23.jar:5.3.23]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:639) ~[spring-beans-5.3.23.jar:5.3.23]
	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:119) ~[spring-beans-5.3.23.jar:5.3.23]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessProperties(AutowiredAnnotationBeanPostProcessor.java:399) ~[spring-beans-5.3.23.jar:5.3.23]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1431) ~[spring-beans-5.3.23.jar:5.3.23]
	a
```


这个错误表明了几个关键问题：

1. **依赖未满足**：`customUserDetailsService` bean 无法创建，因为它依赖于 `usersService`，而 `usersService` 依赖于 `baseMapper`，最终 `baseMapper` 依赖于 `sqlSessionFactory`。

2. **数据源配置问题**：在创建 `sqlSessionFactory` 时出现了问题。具体地，`dataSource` 的实例化失败，报告了 `com.zaxxer.hikari.util.DriverDataSource` 缺少默认构造函数的错误。

针对这些问题，可以采取以下步骤来解决：

- **检查数据源配置**：打开你的 `application.properties` 或 `application.yml` 文件，确认数据库连接信息是否正确配置。确保以下信息正确填写：
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/your_database
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  ```

- **检查依赖配置**：在你的 Maven `pom.xml` 文件中，确认是否正确添加了 MyBatis Plus 和相关数据库依赖。示例配置如下：
  ```xml
  <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-boot-starter</artifactId>
      <version>latest_version</version>
  </dependency>
  <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>latest_version</version>
  </dependency>
  ```

- **检查数据源初始化**：如果使用的是 HikariCP 数据源（Spring Boot 默认的数据源），确保在 `application.properties` 或 `application.yml` 中的数据源配置项正确，例如 `spring.datasource.*` 相关配置。

- **解决 `com.zaxxer.hikari.util.DriverDataSource` 缺少默认构造函数的问题**：通常情况下，HikariCP 不需要手动配置 `DriverDataSource`，Spring Boot 会自动配置。确保你的依赖版本和配置与 Spring Boot 版本兼容。

如果以上步骤都确认无误，重新启动应用程序看看问题是否解决。如果问题仍然存在，可以提供更多的配置信息或者详细的错误日志，以便进一步帮助你解决。