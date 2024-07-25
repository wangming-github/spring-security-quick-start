package com.maizi.author.service;

import com.maizi.common.o.dto.UserDTO;
import com.maizi.common.utils.R;

import java.util.Map;

public interface LoginService {

    R login(UserDTO userDTO);

    R logout();

    Map<String, Object> getRolesAndAuthorities();
}

