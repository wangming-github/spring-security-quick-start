package com.maizi.author.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizi.author.entity.UsersEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
@Mapper
public interface UsersDao extends BaseMapper<UsersEntity> {


    @Select("SELECT r.name FROM roles r " +//
            "LEFT JOIN user_roles ur ON r.id = ur.role_id " +//
            "LEFT JOIN users u ON ur.user_id = u.id " +//
            "WHERE u.username = #{username}")
    List<String> getRolesByUserName(String username);

    @Select("SELECT p.name FROM permissions p " +//
            "LEFT JOIN role_permissions rp ON p.id = rp.permission_id " +//
            "LEFT JOIN roles r ON rp.role_id = r.id " +//
            "LEFT JOIN user_roles ur ON r.id = ur.role_id " +//
            "LEFT JOIN users u ON ur.user_id = u.id " +//
            "WHERE u.username = #{username}")
    List<String> getPermissionsByUserName(String username);
}


