package com.maizi.author.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.author.service.UsersService;
import com.maizi.common.o.dto.UserDetailsDTO;
import com.maizi.common.exception.RRException;
import com.maizi.author.dao.UsersDao;
import com.maizi.author.entity.RolesEntity;
import com.maizi.author.entity.UsersEntity;
import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.QueryUtils;
import com.maizi.author.service.PermissionsService;
import com.maizi.author.service.RolePermissionsService;
import com.maizi.author.service.RolesService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {


    @Autowired
    UsersDao usersDao;
    @Autowired
    RolesService rolesService;
    @Autowired
    RolePermissionsService rolePermissionsService;
    @Autowired
    PermissionsService permissionsService;

    @Override
    public UserDetailsDTO findAuthorByUsername(String username) throws RRException {

        // 查询当前用户信息， 如果用户未找到，则抛出异常。
        UsersEntity user = Optional.ofNullable(getUserByName(username)).orElseThrow(() -> new RRException("没有找到用户名为" + username + "的信息！ "));

        // 查询角色信息
        List<RolesEntity> roles = rolesService.findRolesByUserId(user.getId());

        List<Integer> roleIds = roles.stream().map(RolesEntity::getId).collect(Collectors.toList());
        List<String> rolesNames = roles.stream().map(RolesEntity::getName).collect(Collectors.toList());

        // 查询权限信息
        List<String> permissionNames = permissionsService.findPermissionNameById(roleIds);

        // 封装成DTO传输对象方便远程调用
        return UserDetailsDTO.builder()//
                .usersId(user.getId()).usersUsername(user.getUsername()).usersPassword(user.getPassword())//
                .rolesNames(rolesNames)//
                .permissionNames(permissionNames)//
                .build();
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersEntity> page = this.page(new QueryUtils<UsersEntity>().getPage(params), new QueryWrapper<>());
        return new PageUtils(page);
    }

    @Override
    public UsersEntity getUserByName(String username) {
        LambdaQueryWrapper<UsersEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsersEntity::getUsername, username);
        return this.getOne(wrapper);
    }


    // 示例：对已有密码进行重新编码
    // @Override
    // public void reEncodePasswords() {
    //
    //     this.list().forEach(user -> {
    //         System.out.println(user.toString());
    //         String encodedPassword = passwordEncoder.encode(user.getPassword());
    //         user.setPassword(encodedPassword);
    //         this.updateById(user);
    //         System.out.println(user.toString());
    //     });
    // }


}