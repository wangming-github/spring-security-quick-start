package com.maizi.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maizi.common.o.constants.ModuleType;
import com.maizi.common.o.dto.UserDetailsDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 定义了一个自定义的用户详细信息类 CustomUserUserDetails，
 * 实现了 Spring Security 的 UserDetails 接口，
 * 用于封装从数据库查询出的用户信息及其角色和权限。
 *
 * @author maizi
 */
@Slf4j
@Data
@ToString
@NoArgsConstructor
// //@JsonIgnoreProperties(ignoreUnknown = true)是 Jackson 提供的一个注解，
// // 用于在反序列化 JSON 数据时忽略 JSON 中存在但 Java 对象中不存在的属性。
// // 这对于处理可能会有额外属性的 JSON 数据特别有用，
// // 以防止因 JSON 数据与 Java 对象的结构不完全匹配而导致的反序列化错误。
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityUserUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    // 数据库查询出来的用户
    private UserDetailsDTO user;

    // 存储SpringSecurity所需要的权限信息的集合
    @JsonIgnore  // 序列化时，redis存储数据时忽略该字段
    private List<GrantedAuthority> authorities;

    public SecurityUserUserDetails(UserDetailsDTO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 只需要第一次进来时将permission 转换为authoritys
        if (authorities == null) {
            log.info("【公共模块】- 权限信息集合为空通过UserDetailsDTO初始化....");
            mapRolesToAuthorities(user.getRolesNames(), user.getPermissionNames());
        }
        return authorities;
    }

    /**
     * 将角色和权限列表转换为 Spring Security 的 GrantedAuthority 对象列表
     */
    private void mapRolesToAuthorities(List<String> roles, List<String> permissions) {
        authorities = new ArrayList<>();
        // 将角色转换为 Spring Security 的 ROLE_ 角色格式并添加到权限列表中
        if (roles != null) {
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        }
        if (permissions != null) {
            permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        }
        // 将权限添加到权限列表中
        log.info("【公共模块】- authority：" + this.authorities);
    }


    @Override
    public String getPassword() {
        return user.getUsersPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsersUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
