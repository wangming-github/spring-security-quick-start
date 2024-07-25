package com.maizi.common.o.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;


/**
 * @author maizi
 */
@Data// 生成 getter、setter、toString、equals 和 hashCode 方法。
@ToString
@Builder// 生成构建器模式的代码。
@AllArgsConstructor// 生成一个包含所有字段的构造函数。
@NoArgsConstructor// 生成一个无参数的构造函数。
public class UserDetailsDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer usersId;
    private String usersUsername;
    // @JsonIgnore // 序列化时，redis存储数据时忽略该字段
    private String usersPassword;

    /**
     * 用户的角色名
     */
    // @JsonIgnore // 序列化时，redis存储数据时忽略该字段
    List<String> rolesNames;
    /**
     * 用户的权限名
     */
    // @JsonIgnore // 序列化时，redis存储数据时忽略该字段
    List<String> permissionNames;
    // Getters and setters for all fields
}