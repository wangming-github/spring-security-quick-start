package com.maizi.config;

import com.maizi.user.UserLoginInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;


public class GiteeAuthentication extends AbstractAuthenticationToken {

    private String code;
    private UserLoginInfo currentUser;

    public GiteeAuthentication() {
        super(null); // 权限，用不上，直接null
    }

    @Override
    public Object getCredentials() {
        return isAuthenticated() ? null : code;
    }

    @Override
    public Object getPrincipal() {
        return isAuthenticated() ? currentUser : null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCurrentUser(UserLoginInfo currentUser) {
        this.currentUser = currentUser;
    }
}