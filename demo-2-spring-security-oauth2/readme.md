

![filterchain](imgs/img.png)

### OAuth 2.0 简介

OAuth 2.0 是目前最流行的授权机制，用来授权第三方应用获取用户数据。

这个标准比较抽象，使用了很多术语，初学者不容易理解。其实说起来并不复杂，下面通过一个简单的类比，帮助大家轻松理解 OAuth 2.0 到底是什么。

#### 一、快递员问题

我住在一个大型的居民小区。小区有门禁系统，进入的时候需要输入密码。我经常网购和外卖，每天都有快递员来送货。我必须找到一个办法，让快递员通过门禁系统进入小区。

如果我把自己的密码告诉快递员，他就拥有了与我同样的权限，这样不太合适。万一我想取消他进入小区的权力，也很麻烦，我自己的密码也得跟着改，还得通知其他的快递员。

有没有一种办法，让快递员能够自由进入小区，又不必知道小区居民的密码，而且他的唯一权限就是送货，其他需要密码的场合，他都没有权限？

#### 二、授权机制的设计

于是，我设计了一套授权机制。

1. **获取授权**: 门禁系统的密码输入器下面增加一个按钮，叫做"获取授权"。快递员需要首先按这个按钮去申请授权。
2. **屋主确认**: 按下按钮后，屋主（也就是我）的手机就会跳出对话框：有人正在要求授权。系统还会显示该快递员的姓名、工号和所属的快递公司。我确认请求属实，就点击按钮，告诉门禁系统我同意给予他进入小区的授权。
3. **生成令牌**: 门禁系统得到我的确认以后，向快递员显示一个进入小区的令牌（access token）。令牌就是类似密码的一串数字，只在短期内（比如七天）有效。
4. **使用令牌**: 快递员向门禁系统输入令牌，进入小区。

有人可能会问，为什么不是远程为快递员开门，而要为他单独生成一个令牌？这是因为快递员可能每天都会来送货，第二天他还可以复用这个令牌。另外，有的小区有多重门禁，快递员可以使用同一个令牌通过它们。

#### 三、互联网场景

我们把上面的例子搬到互联网，就是 OAuth 的设计了。

1. **储存用户数据的网络服务**: 比如，微信储存了我的好友信息，获取这些信息就必须经过微信的"门禁系统"。
2. **第三方应用**: 快递员（或者说快递公司）就是第三方应用，想要穿过门禁系统进入小区。
3. **用户授权**: 我就是用户本人，同意授权第三方应用进入小区，获取我的数据。

简单说，OAuth 就是一种授权机制。数据的所有者告诉系统，同意授权第三方应用进入系统，获取这些数据。系统从而产生一个短期的进入令牌（token），用来代替密码，供第三方应用使用。

#### 四、令牌与密码

令牌（token）与密码（password）的作用是一样的，都可以进入系统，但是有三点差异。

1. **有效期**: 令牌是短期的，到期会自动失效，用户自己无法修改。密码一般长期有效，用户不修改就不会发生变化。
2. **可撤销性**: 令牌可以被数据所有者撤销，会立即失效。以上例而言，屋主可以随时取消快递员的令牌。密码一般不允许被他人撤销。
3. **权限范围**: 令牌有权限范围（scope），比如只能进小区的二号门。对于网络服务来说，只读令牌就比读写令牌更安全。密码一般是完整权限。

上面这些设计，保证了令牌既可以让第三方应用获得权限，同时又随时可控，不会危及系统安全。这就是 OAuth 2.0 的优点。

注意，只要知道了令牌，就能进入系统。系统一般不会再次确认身份，所以令牌必须保密，泄漏令牌与泄漏密码的后果是一样的。这也是为什么令牌的有效期，一般都设置得很短的原因。

OAuth 2.0 对于如何颁发令牌的细节，规定得非常详细。具体来说，一共分成四种授权类型（authorization grant），即四种颁发令牌的方式，适用于不同的互联网场景。





**OAuth 2.0 规定了四种获得令牌的流程。你可以选择最适合自己的那一种，向第三方应用颁发令牌。**下面就是这四种授权方式。

> - 授权码（authorization-code）
> - 隐藏式（implicit）
> - 密码式（password）：
> - 客户端凭证（client credentials）

注意，不管哪一种授权方式，第三方应用申请令牌之前，都必须先到系统备案，说明自己的身份，然后会拿到两个身份识别码：客户端 ID（client ID）和客户端密钥（client secret）。这是为了防止令牌被滥用，没有备案过的第三方应用，是不会拿到令牌的。



## 第一种授权方式：授权码

**授权码（authorization code）方式，指的是第三方应用先申请一个授权码，然后再用该码获取令牌。**

这种方式是最常用的流程，安全性也最高，它适用于那些有后端的 Web 应用。授权码通过前端传送，令牌则是储存在后端，而且所有与资源服务器的通信都在后端完成。这样的前后端分离，可以避免令牌泄漏。

#### 第一步：用户点击授权链接

A 网站提供一个链接，用户点击后就会跳转到 B 网站，授权用户数据给 A 网站使用。下面就是 A 网站跳转 B 网站的一个示意链接。

> ```javascript
> https://b.com/oauth/authorize?
>   response_type=code&
>   client_id=CLIENT_ID&
>   redirect_uri=CALLBACK_URL&
>   scope=read
> ```

* `response_type`参数表示要求返回授权码（`code`）。
* `client_id`参数让 B 网站知道是谁在请求。
* `redirect_uri`参数是 B 网站在接受或拒绝请求后的跳转网址。
* `scope`参数表示要求的授权范围（这里是只读）。

![img](https://cdn.beekka.com/blogimg/asset/201904/bg2019040902.jpg)

#### 第二步：用户同意授权

用户跳转后，B 网站会要求用户登录，然后询问是否同意给予 A 网站授权。用户表示同意，这时 B 网站就会跳回`redirect_uri`参数指定的网址。跳转时，会传回一个授权码，就像下面这样。

> ```javascript
> https://a.com/callback?code=AUTHORIZATION_CODE
> ```

上面 URL 中，`code`参数就是授权码。

![img](https://cdn.beekka.com/blogimg/asset/201904/bg2019040907.jpg)

#### 第三步：A 网站后端请求令牌

A 网站拿到授权码以后，就可以在后端，向 B 网站请求令牌。

> ```javascript
> https://b.com/oauth/token?
>  client_id=CLIENT_ID&
>  client_secret=CLIENT_SECRET&
>  grant_type=authorization_code&
>  code=AUTHORIZATION_CODE&
>  redirect_uri=CALLBACK_URL
> ```

上面 URL 中，

* `client_id`参数和`client_secret`参数用来让 B 确认 A 的身份（`client_secret`参数是保密的，因此只能在后端发请求）。
* `grant_type`参数的值是`authorization_code`，表示采用的授权方式是授权码。
* `code`参数是上一步拿到的授权码。
* `redirect_uri`参数是令牌颁发后的回调网址。

![img](https://cdn.beekka.com/blogimg/asset/201904/bg2019040904.jpg)

#### 第四步：B 网站颁发令牌

B 网站收到请求以后，就会颁发令牌。具体做法是向`redirect_uri`指定的网址，发送一段 JSON 数据。

> ```javascript
> {    
>   "access_token":"ACCESS_TOKEN",
>   "token_type":"bearer",
>   "expires_in":2592000,
>   "refresh_token":"REFRESH_TOKEN",
>   "scope":"read",
>   "uid":100101,
>   "info":{...}
> }
> ```

上面 JSON 数据中，
* `access_token`字段就是令牌，A 网站在后端拿到了。

![img](https://cdn.beekka.com/blogimg/asset/201904/bg2019040905.jpg)

## 第二种方式：隐藏式

有些 Web 应用是纯前端应用，没有后端。这时就不能用上面的方式了，必须将令牌储存在前端。**RFC 6749 就规定了第二种方式，允许直接向前端颁发令牌。这种方式没有授权码这个中间步骤，所以称为（授权码）"隐藏式"（implicit）。**

第一步，A 网站提供一个链接，要求用户跳转到 B 网站，授权用户数据给 A 网站使用。

> ```javascript
> https://b.com/oauth/authorize?
>   response_type=token&
>   client_id=CLIENT_ID&
>   redirect_uri=CALLBACK_URL&
>   scope=read
> ```

上面 URL 中，`response_type`参数为`token`，表示要求直接返回令牌。

第二步，用户跳转到 B 网站，登录后同意给予 A 网站授权。这时，B 网站就会跳回`redirect_uri`参数指定的跳转网址，并且把令牌作为 URL 参数，传给 A 网站。

> ```javascript
> https://a.com/callback#token=ACCESS_TOKEN
> ```

上面 URL 中，`token`参数就是令牌，A 网站因此直接在前端拿到令牌。

注意，令牌的位置是 URL 锚点（fragment），而不是查询字符串（querystring），这是因为 OAuth 2.0 允许跳转网址是 HTTP 协议，因此存在"中间人攻击"的风险，而浏览器跳转时，锚点不会发到服务器，就减少了泄漏令牌的风险。

![img](https://cdn.beekka.com/blogimg/asset/201904/bg2019040906.jpg)

这种方式把令牌直接传给前端，是很不安全的。因此，只能用于一些安全要求不高的场景，并且令牌的有效期必须非常短，通常就是会话期间（session）有效，浏览器关掉，令牌就失效了。

## 第三种方式：密码式

**如果你高度信任某个应用，RFC 6749 也允许用户把用户名和密码，直接告诉该应用。该应用就使用你的密码，申请令牌，这种方式称为"密码式"（password）。**

第一步，A 网站要求用户提供 B 网站的用户名和密码。拿到以后，A 就直接向 B 请求令牌。

> ```javascript
> https://oauth.b.com/token?
>   grant_type=password&
>   username=USERNAME&
>   password=PASSWORD&
>   client_id=CLIENT_ID
> ```

上面 URL 中，`grant_type`参数是授权方式，这里的`password`表示"密码式"，`username`和`password`是 B 的用户名和密码。

第二步，B 网站验证身份通过后，直接给出令牌。注意，这时不需要跳转，而是把令牌放在 JSON 数据里面，作为 HTTP 回应，A 因此拿到令牌。

这种方式需要用户给出自己的用户名/密码，显然风险很大，因此只适用于其他授权方式都无法采用的情况，而且必须是用户高度信任的应用。

## 第四种方式：凭证式

**最后一种方式是凭证式（client credentials），适用于没有前端的命令行应用，即在命令行下请求令牌。**

第一步，A 应用在命令行向 B 发出请求。

> ```javascript
> https://oauth.b.com/token?
>   grant_type=client_credentials&
>   client_id=CLIENT_ID&
>   client_secret=CLIENT_SECRET
> ```

上面 URL 中，`grant_type`参数等于`client_credentials`表示采用凭证式，`client_id`和`client_secret`用来让 B 确认 A 的身份。

第二步，B 网站验证通过以后，直接返回令牌。

这种方式给出的令牌，是针对第三方应用的，而不是针对用户的，即有可能多个用户共享同一个令牌。

## 令牌的使用

A 网站拿到令牌以后，就可以向 B 网站的 API 请求数据了。

此时，每个发到 API 的请求，都必须带有令牌。具体做法是在请求的头信息，加上一个`Authorization`字段，令牌就放在这个字段里面。

> ```bash
> curl -H "Authorization: Bearer ACCESS_TOKEN" \
> "https://api.b.com"
> ```

上面命令中，`ACCESS_TOKEN`就是拿到的令牌。

## 更新令牌

令牌的有效期到了，如果让用户重新走一遍上面的流程，再申请一个新的令牌，很可能体验不好，而且也没有必要。OAuth 2.0 允许用户自动更新令牌。

具体方法是，B 网站颁发令牌的时候，一次性颁发两个令牌，一个用于获取数据，另一个用于获取新的令牌（refresh token 字段）。令牌到期前，用户使用 refresh token 发一个请求，去更新令牌。

> ```javascript
> https://b.com/oauth/token?
>   grant_type=refresh_token&
>   client_id=CLIENT_ID&
>   client_secret=CLIENT_SECRET&
>   refresh_token=REFRESH_TOKEN
> ```

上面 URL 中，`grant_type`参数为`refresh_token`表示要求更新令牌，`client_id`参数和`client_secret`参数用于确认身份，`refresh_token`参数就是用于更新令牌的令牌。

B 网站验证通过以后，就会颁发新的令牌。

写到这里，颁发令牌的四种方式就介绍完了。[下一篇文章](https://www.ruanyifeng.com/blog/2019/04/github-oauth.html)会编写一个真实的 Demo，演示如何通过 OAuth 2.0 向 GitHub 的 API 申请令牌，然后再用令牌获取数据。

（正文完）







1. **`spring-boot-starter-oauth2-resource-server`**：

   角色：资源服务器（Resource Server）

   比喻：想象一个图书馆（资源服务器），它有很多书（受保护的资源）。只有持有有效借书证（访问令牌）的人才能借书（访问资源）。

   功能：保护资源，验证和授权访问令牌，确保只有授权的客户端能访问受保护的资源。

   使用场景：当你的应用程序中有需要保护的 API 或数据时，你会使用这个依赖来确保只有授权用户能访问这些资源。

2. **`spring-boot-starter-oauth2-client`**：

   角色：OAuth2 客户端（Client）

   比喻：想象一个用户（客户端），他想从图书馆（资源服务器）借书，但他需要先从图书馆管理处（授权服务器）获得借书证（访问令牌）。

   功能：与授权服务器交互，获取和管理访问令牌，用以访问受保护的资源。

   使用场景：当你的应用程序需要访问其他受保护的资源服务器时，你会使用这个依赖来获取访问令牌。

3. **`spring-boot-starter-oauth2-authorization-server`**：

   角色：授权服务器（Authorization Server）

   比喻：想象一个图书馆管理处（授权服务器），它负责发放借书证（访问令牌）。只有经过图书馆管理处验证身份（用户认证）的人，才能拿到借书证。

   功能：处理用户身份验证，生成并发放访问令牌，支持多种授权模式（如授权码、密码模式等）。

   使用场景：当你的应用程序需要作为授权服务器，负责用户认证并发放访问令牌时，你会使用这个依赖。

总结：

- **资源服务器**：保护资源，验证访问令牌，确保只有授权的客户端能访问资源。
- **OAuth2 客户端**：从授权服务器获取访问令牌，用以访问资源服务器上的受保护资源。
- **授权服务器**：进行用户认证，发放访问令牌，允许客户端获取访问资源服务器的权限。

### 可以搭建一个示例?

当然可以！我们来构建一个简单的 Spring Boot 项目，包括 OAuth2 授权服务器、资源服务器和客户端，展示它们的交互过程。

### 项目结构

1. **authorization-server**：授权服务器
2. **resource-server**：资源服务器
3. **client**：OAuth2 客户端

### 授权服务器 (authorization-server)

**依赖项 (`pom.xml`)**：

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
    </dependency>
</dependencies>
```

**配置类 (`AuthorizationServerConfig.java`)**：

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("{noop}secret")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("read")
                .redirectUris("http://localhost:8082/login/oauth2/code/client");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置令牌存储等
    }
}
```

**应用程序主类 (`AuthorizationServerApplication.java`)**：

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }
}
```

### 资源服务器 (resource-server)

**依赖项 (`pom.xml`)**：

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
</dependencies>
```

**配置类 (`ResourceServerConfig.java`)**：

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public").permitAll()
                .anyRequest().authenticated();
    }
}
```

**资源控制器 (`ResourceController.java`)**：

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }

    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "This is a protected endpoint.";
    }
}
```

**应用程序主类 (`ResourceServerApplication.java`)**：

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}
```

### 客户端 (client)

**依赖项 (`pom.xml`)**：

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
</dependencies>
```

**配置类 (`SecurityConfig.java`)**：

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login.defaultSuccessUrl("/home", true)
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository))
                );
    }
}
```

**控制器 (`ClientController.java`)**：

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@RestController
public class ClientController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public ClientController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/home")
    public String home(OidcUser oidcUser) {
        return "Welcome, " + oidcUser.getFullName();
    }
}
```

**应用程序主类 (`ClientApplication.java`)**：

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

### 运行步骤

1. 启动授权服务器 (`AuthorizationServerApplication`)。
2. 启动资源服务器 (`ResourceServerApplication`)。
3. 启动客户端 (`ClientApplication`)。
4. 在浏览器中访问 `http://localhost:8082`（客户端地址），将被重定向到授权服务器进行登录。
5. 登录成功后，将被重定向回客户端，显示欢迎信息。
6. 可以使用获取到的令牌访问资源服务器的受保护资源（例如，`http://localhost:8081/protected`）。

这样，你就可以看到 OAuth2 授权服务器、资源服务器和客户端之间的交互了。