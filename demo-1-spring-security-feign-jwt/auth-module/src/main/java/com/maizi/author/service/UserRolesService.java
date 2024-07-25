package com.maizi.author.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.author.entity.UserRolesEntity;
import com.maizi.common.utils.PageUtils;

import java.util.Map;

/**
 * @author wangming
 * @email myoneray@gmail.com
 * @date 2024-07-12 17:44:48
 */
public interface UserRolesService extends IService<UserRolesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

