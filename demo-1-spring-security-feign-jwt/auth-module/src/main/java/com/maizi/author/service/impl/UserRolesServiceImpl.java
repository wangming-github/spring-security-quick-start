package com.maizi.author.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.author.dao.UserRolesDao;
import com.maizi.author.entity.UserRolesEntity;
import com.maizi.author.service.UserRolesService;
import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.QueryUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userRolesService")
public class UserRolesServiceImpl extends ServiceImpl<UserRolesDao, UserRolesEntity> implements UserRolesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserRolesEntity> page = this.page(new QueryUtils<UserRolesEntity>().getPage(params), new QueryWrapper<UserRolesEntity>());

        return new PageUtils(page);
    }

}