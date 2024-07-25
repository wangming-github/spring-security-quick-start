package com.maizi.author.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserWithRolesAndPermissions {
    private String username;
    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();
}
