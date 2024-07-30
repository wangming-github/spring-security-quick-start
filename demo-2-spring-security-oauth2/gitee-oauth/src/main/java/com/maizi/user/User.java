package com.maizi.user;

import lombok.Data;

// 详细的用户信息数据，一般会有十几个字段。这里简单写几个
@Data
public class User {

    private Long userId;
    private String roleId;
    private String username;
    private String password;
    private String phone;
}
