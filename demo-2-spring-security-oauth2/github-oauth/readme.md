# Getting Started

### 官方示例

https://github.com/spring-projects/spring-security-samples/tree/6.2.x/servlet/spring-boot/java/oauth2/login#github-login



![image-20240731002854134](/Users/maizi/Documents/0.Quick-Start/spring-security-quick-start/file/imgs/image-20240731002854134.png)

## 使用 GitHub 登录

本节介绍如何使用 GitHub 作为身份验证提供程序配置示例应用程序，并涵盖以下主题：

- [注册 OAuth 应用程序](https://github.com/spring-projects/spring-security-samples/tree/6.2.x/servlet/spring-boot/java/oauth2/login#github-register-application)
- [配置application.yml](https://github.com/spring-projects/spring-security-samples/tree/6.2.x/servlet/spring-boot/java/oauth2/login#github-application-config)
- [启动应用程序](https://github.com/spring-projects/spring-security-samples/tree/6.2.x/servlet/spring-boot/java/oauth2/login#github-boot-application)

### 注册 OAuth 应用程序

要使用 GitHub 的 OAuth 2.0 身份验证系统进行登录，必须[注册一个新的 OAuth 应用程序](https://github.com/settings/applications/new)。

注册 OAuth 应用程序时，请确保**授权回调 URL**设置为`http://127.0.0.1:8080/login/oauth2/code/github`。

*授权回调 URL（重定向 URI）是最终用户通过 GitHub 进行身份验证并在授权应用程序*页面上授予对 OAuth 应用程序的访问权限后，其用户代理被重定向回的应用程序路径。

默认的重定向 URI 模板是`{baseUrl}/login/oauth2/code/{registrationId}`。registrationId**是**的唯一标识符`ClientRegistration`。

如果应用程序在代理服务器后面运行，建议检查[代理服务器配置](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#appendix-proxy-server)以确保应用程序配置正确。另请参阅支持的模板[`URI`变量](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2Client-auth-code-redirect-uri)`redirect-uri`。

### 配置application.yml



现在您有了一个新的 GitHub OAuth 应用程序，您需要配置该应用程序以使用 OAuth 应用程序进行*身份验证流程*。具体操作如下：

1. 转到`application.yml`并设置以下配置：

   ```
   spring:
     security:
       oauth2:
         client:
           registration: 
             github: 
               client-id: github-client-id
               client-secret: github-client-secret
   ```

   

   示例 3.OAuth 客户端属性

   1. `spring.security.oauth2.client.registration`是 OAuth 客户端属性的基本属性前缀。
   2. 基本属性前缀后面是的 ID `ClientRegistration`，例如 github。

2. `client-id`将和属性中的值替换`client-secret`为您之前创建的 OAuth 2.0 凭据。

### 启动应用程序



启动 Spring Boot 2.0 示例并转到`http://127.0.0.1:8080`。然后，您将被重定向到默认的*自动生成的*登录页面，其中显示 GitHub 的链接。

单击 GitHub 链接，您将被重定向到 GitHub 进行身份验证。

使用您的 GitHub 凭据进行身份验证后，呈现给您的下一个页面是“授权应用程序”。此页面将要求您**授权**在上一步中创建的应用程序。单击*授权应用程序*以允许 OAuth 应用程序访问您的个人用户数据信息。

此时，OAuth 客户端从 UserInfo Endpoint 检索您的个人用户信息并建立经过身份验证的会话。

[有关 UserInfo Endpoint 返回的详细信息，请参阅“获取经过身份验证的用户”](https://developer.github.com/v3/users/#get-the-authenticated-user) 的 API 文档。

http://127.0.0.1:8080/

登录成功将在页面返回用户信息

```tex
OAuth 2.0 Login with Spring Security
You are successfully logged in 64132018 via the OAuth 2.0 Client GitHub
 
User Attributes:
login: wangming-github
id: 64132018
node_id: MDQ6VXNlcjY0MTMyMDE4
avatar_url: https://avatars.githubusercontent.com/u/64132018?v=4
gravatar_id:
url: https://api.github.com/users/wangming-github
html_url: https://github.com/wangming-github
followers_url: https://api.github.com/users/wangming-github/followers
following_url: https://api.github.com/users/wangming-github/following{/other_user}
gists_url: https://api.github.com/users/wangming-github/gists{/gist_id}
starred_url: https://api.github.com/users/wangming-github/starred{/owner}{/repo}
subscriptions_url: https://api.github.com/users/wangming-github/subscriptions
organizations_url: https://api.github.com/users/wangming-github/orgs
repos_url: https://api.github.com/users/wangming-github/repos
events_url: https://api.github.com/users/wangming-github/events{/privacy}
received_events_url: https://api.github.com/users/wangming-github/received_events
type: User
site_admin: false
name: Nines
company:
blog:
location
```

#### 核心类

* CommonOAuth2Provider帮我们实现了github  FACEBOOK GOOGLE 等平台的登录