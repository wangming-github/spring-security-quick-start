package com.maizi.author.config;

import com.maizi.author.service.UsersService;
import com.maizi.common.o.dto.UserDetailsDTO;
import com.maizi.common.security.SecurityUserUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


/**
 * 自定义用户详细信息服务类，用于登录时从数据库或其他来源加载用户信息
 * 以供 Spring Security 进行身份验证和授权。
 *
 * <p>
 * 该类实现了 {@link UserDetailsService} 接口，负责从用户提供的用户名中加载用户详细信息。
 * </p>
 * 不实现UserDetailsService接口，Sprint security默认从我们在SecurityConfig中配置的用户名查询用户信息校验登录信息。
 * 实现UserDetailsService接口，我们可以从第三方加载用户详细信息。
 *
 * @author maizi
 */
@Slf4j
@Component
public class LoginUserDetailsService implements UserDetailsService {


    @Autowired
    private UsersService usersService; // 注入服务类，用于从数据源中获取用户信息

    /**
     * 根根据用户名加载用户的详细信息，包括用户名、密码和角色等信息，
     * 并返回一个实现了 UserDetails 接口的对象。
     * 这个对象包含了用户的所有必要信息，供 Spring Security 后续使用。
     *
     * @param username 用户名，用于查找用户详细信息
     * @return 实现了 {@link UserDetails} 接口的对象，包含用户的详细信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 从 UsersService 中查找用户详细信息
        UserDetailsDTO userDetailsDTO = usersService.findAuthorByUsername(username);
        // 创建并返回一个 CustomUserUserDetails 实例
        // 该实例包含了从数据源中获取的用户详细信息
        return new SecurityUserUserDetails(userDetailsDTO);
    }
}
