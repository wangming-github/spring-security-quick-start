-- 删除数据库（如果存在的话）
drop database if exists spring_security_quick_start;

-- 创建一个新的数据库
create database spring_security_quick_start;

-- 切换到刚创建的数据库
use spring_security_quick_start;

-- 创建 oauth2_registered_client 表，用于存储注册的客户端信息
CREATE TABLE oauth2_registered_client (
    id varchar(100) NOT NULL,  -- 客户端唯一标识符
    client_id varchar(100) NOT NULL,  -- 客户端 ID
    client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,  -- 客户端 ID 发放时间
    client_secret varchar(200) DEFAULT NULL,  -- 客户端密钥
    client_secret_expires_at timestamp DEFAULT NULL,  -- 客户端密钥过期时间
    client_name varchar(200) NOT NULL,  -- 客户端名称
    client_authentication_methods varchar(1000) NOT NULL,  -- 客户端认证方法
    authorization_grant_types varchar(1000) NOT NULL,  -- 授权授权类型
    redirect_uris varchar(1000) DEFAULT NULL,  -- 重定向 URI
    post_logout_redirect_uris varchar(1000) DEFAULT NULL,  -- 注销后的重定向 URI
    scopes varchar(1000) NOT NULL,  -- 客户端授权范围
    client_settings varchar(2000) NOT NULL,  -- 客户端设置
    token_settings varchar(2000) NOT NULL,  -- 令牌设置
    PRIMARY KEY (id)  -- 主键
);

-- 创建 oauth2_authorization_consent 表，用于存储用户对客户端的授权同意
CREATE TABLE oauth2_authorization_consent (
    registered_client_id varchar(100) NOT NULL,  -- 注册的客户端 ID
    principal_name varchar(200) NOT NULL,  -- 用户主名称
    authorities varchar(1000) NOT NULL,  -- 用户权限
    PRIMARY KEY (registered_client_id, principal_name)  -- 主键
);

-- 
-- IMPORTANT:
-- If using PostgreSQL, update ALL columns defined with 'blob' to 'text',
-- as PostgreSQL does not support the 'blob' data type.
-- 

-- 创建 oauth2_authorization 表，用于存储授权相关的信息
CREATE TABLE oauth2_authorization (
    id varchar(100) NOT NULL,  -- 授权记录的唯一标识符
    registered_client_id varchar(100) NOT NULL,  -- 注册的客户端 ID
    principal_name varchar(200) NOT NULL,  -- 用户主名称
    authorization_grant_type varchar(100) NOT NULL,  -- 授权类型
    authorized_scopes varchar(1000) DEFAULT NULL,  -- 授权范围
    attributes blob DEFAULT NULL,  -- 授权属性
    state varchar(500) DEFAULT NULL,  -- 授权状态
    authorization_code_value blob DEFAULT NULL,  -- 授权码值
    authorization_code_issued_at timestamp DEFAULT NULL,  -- 授权码发放时间
    authorization_code_expires_at timestamp DEFAULT NULL,  -- 授权码过期时间
    authorization_code_metadata blob DEFAULT NULL,  -- 授权码元数据
    access_token_value blob DEFAULT NULL,  -- 访问令牌值
    access_token_issued_at timestamp DEFAULT NULL,  -- 访问令牌发放时间
    access_token_expires_at timestamp DEFAULT NULL,  -- 访问令牌过期时间
    access_token_metadata blob DEFAULT NULL,  -- 访问令牌元数据
    access_token_type varchar(100) DEFAULT NULL,  -- 访问令牌类型
    access_token_scopes varchar(1000) DEFAULT NULL,  -- 访问令牌范围
    oidc_id_token_value blob DEFAULT NULL,  -- OIDC ID 令牌值
    oidc_id_token_issued_at timestamp DEFAULT NULL,  -- OIDC ID 令牌发放时间
    oidc_id_token_expires_at timestamp DEFAULT NULL,  -- OIDC ID 令牌过期时间
    oidc_id_token_metadata blob DEFAULT NULL,  -- OIDC ID 令牌元数据
    refresh_token_value blob DEFAULT NULL,  -- 刷新令牌值
    refresh_token_issued_at timestamp DEFAULT NULL,  -- 刷新令牌发放时间
    refresh_token_expires_at timestamp DEFAULT NULL,  -- 刷新令牌过期时间
    refresh_token_metadata blob DEFAULT NULL,  -- 刷新令牌元数据
    user_code_value blob DEFAULT NULL,  -- 用户代码值
    user_code_issued_at timestamp DEFAULT NULL,  -- 用户代码发放时间
    user_code_expires_at timestamp DEFAULT NULL,  -- 用户代码过期时间
    user_code_metadata blob DEFAULT NULL,  -- 用户代码元数据
    device_code_value blob DEFAULT NULL,  -- 设备代码值
    device_code_issued_at timestamp DEFAULT NULL,  -- 设备代码发放时间
    device_code_expires_at timestamp DEFAULT NULL,  -- 设备代码过期时间
    device_code_metadata blob DEFAULT NULL,  -- 设备代码元数据
    PRIMARY KEY (id)  -- 主键
);
