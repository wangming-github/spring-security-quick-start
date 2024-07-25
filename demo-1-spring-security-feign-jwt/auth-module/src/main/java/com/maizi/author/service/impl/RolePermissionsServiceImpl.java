package com.maizi.author.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.author.dao.RolePermissionsDao;
import com.maizi.author.entity.RolePermissionsEntity;
import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.QueryUtils;
import com.maizi.author.service.RolePermissionsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("rolePermissionsService")
public class RolePermissionsServiceImpl extends ServiceImpl<RolePermissionsDao, RolePermissionsEntity> implements RolePermissionsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermissionsEntity> page = this.page(new QueryUtils<RolePermissionsEntity>().getPage(params), new QueryWrapper<RolePermissionsEntity>());

        return new PageUtils(page);
    }


    /**
     * 根据角色Id查询角色权限信息
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<RolePermissionsEntity> findByRoleId(List<Integer> roleIds) {

        LambdaQueryWrapper<RolePermissionsEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePermissionsEntity::getRoleId, roleIds);
        return this.baseMapper.selectList(wrapper);
    }

}