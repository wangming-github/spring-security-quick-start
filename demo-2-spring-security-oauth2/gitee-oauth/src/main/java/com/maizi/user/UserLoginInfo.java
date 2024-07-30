package com.maizi.user;

import lombok.Data;
import lombok.ToString;

/**
 * 用户信息登陆后的信息，会序列化到Jwt的payload
 */
@Data
@ToString
public class UserLoginInfo {

    private String sessionId; // 会话id，全局唯一
    private Long userId;
    private String nickname; // 昵称
    private String roleId;
    private Long expiredTime; // 过期时间


}
