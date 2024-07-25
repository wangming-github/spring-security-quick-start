-- 如果数据库已存在，则删除数据库 aopquickstart
DROP
DATABASE IF EXISTS aopquickstart;

-- 创建数据库 aopquickstart
CREATE
DATABASE IF NOT EXISTS aopquickstart;

-- 使用 aopquickstart 数据库
USE
aopquickstart;

-- 创建用户表
CREATE TABLE `users`
(
    `id`       INT AUTO_INCREMENT PRIMARY KEY, -- 用户ID，自动递增
    `username` VARCHAR(50)  NOT NULL,          -- 用户名，不允许为空
    `password` VARCHAR(100) NOT NULL           -- 密码，不允许为空
);

-- 创建角色表
CREATE TABLE `roles`
(
    `id`   INT AUTO_INCREMENT PRIMARY KEY, -- 角色ID，自动递增
    `name` VARCHAR(50) NOT NULL            -- 角色名称，不允许为空
);

-- 创建权限表
CREATE TABLE `permissions`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY, -- 权限ID，自动递增
    `name`        VARCHAR(50) NOT NULL,           -- 权限名称，不允许为空
    `description` VARCHAR(255)                    -- 权限描述
);

-- 创建用户角色关联表
CREATE TABLE `user_roles`
(
    `user_id` INT NOT NULL,                                              -- 用户ID
    `role_id` INT NOT NULL,                                              -- 角色ID
    PRIMARY KEY (`user_id`, `role_id`),                                  -- 复合主键
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE, -- 外键约束，删除用户时也删除关联
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE  -- 外键约束，删除角色时也删除关联
);

-- 创建角色权限关联表
CREATE TABLE `role_permissions`
(
    `role_id`       INT NOT NULL,                                                   -- 角色ID
    `permission_id` INT NOT NULL,                                                   -- 权限ID
    PRIMARY KEY (`role_id`, `permission_id`),                                       -- 复合主键
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,            -- 外键约束，删除角色时也删除关联
    FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE -- 外键约束，删除权限时也删除关联
);

-- 插入用户数据
INSERT INTO `users` (`username`, `password`)
VALUES ('alice', 'password1'),   -- 用户 alice
       ('bob', 'password2'),     -- 用户 bob
       ('charlie', 'password3'), -- 用户 charlie
       ('david', 'password4'),   -- 用户 david
       ('eve', 'password5');
-- 用户 eve

-- 插入角色数据
INSERT INTO `roles` (`name`)
VALUES ('ADMIN'),     -- 角色 ADMIN
       ('USER'),      -- 角色 USER
       ('MODERATOR'), -- 角色 MODERATOR
       ('GUEST');
-- 角色 GUEST

-- 插入权限数据
INSERT INTO `permissions` (`name`, `description`)
VALUES ('READ_PRIVILEGES', '可以读取数据'),   -- 权限 READ_PRIVILEGES
       ('WRITE_PRIVILEGES', '可以写入数据'),  -- 权限 WRITE_PRIVILEGES
       ('DELETE_PRIVILEGES', '可以删除数据'), -- 权限 DELETE_PRIVILEGES
       ('UPDATE_PRIVILEGES', '可以更新数据');
-- 权限 UPDATE_PRIVILEGES

-- 插入用户角色关联数据
INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES (1, 1), -- 用户ID 1 (alice) 关联角色ID 1 (ADMIN)
       (2, 2), -- 用户ID 2 (bob) 关联角色ID 2 (USER)
       (3, 3), -- 用户ID 3 (charlie) 关联角色ID 3 (MODERATOR)
       (4, 4), -- 用户ID 4 (david) 关联角色ID 4 (GUEST)
       (5, 2);
-- 用户ID 5 (eve) 关联角色ID 2 (USER)

-- 插入角色权限关联数据
INSERT INTO `role_permissions` (`role_id`, `permission_id`)
VALUES (1, 1), -- 角色ID 1 (ADMIN) 拥有权限ID 1 (READ_PRIVILEGES)
       (1, 2), -- 角色ID 1 (ADMIN) 拥有权限ID 2 (WRITE_PRIVILEGES)
       (1, 3), -- 角色ID 1 (ADMIN) 拥有权限ID 3 (DELETE_PRIVILEGES)
       (1, 4), -- 角色ID 1 (ADMIN) 拥有权限ID 4 (UPDATE_PRIVILEGES)
       (2, 1), -- 角色ID 2 (USER) 拥有权限ID 1 (READ_PRIVILEGES)
       (3, 1), -- 角色ID 3 (MODERATOR) 拥有权限ID 1 (READ_PRIVILEGES)
       (3, 4), -- 角色ID 3 (MODERATOR) 拥有权限ID 4 (UPDATE_PRIVILEGES)
       (4, 1); -- 角色ID 4 (GUEST) 拥有权限ID 1 (READ_PRIVILEGES)
