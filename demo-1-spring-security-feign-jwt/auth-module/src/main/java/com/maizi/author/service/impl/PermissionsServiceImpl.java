package com.maizi.author.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.author.dao.PermissionsDao;
import com.maizi.author.entity.PermissionsEntity;
import com.maizi.author.entity.RolePermissionsEntity;
import com.maizi.author.service.PermissionsService;
import com.maizi.author.service.RolePermissionsService;
import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("permissionsService")
public class PermissionsServiceImpl extends ServiceImpl<PermissionsDao, PermissionsEntity> implements PermissionsService {

    @Autowired
    private RolePermissionsService rolePermissionsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PermissionsEntity> page = this.page(new QueryUtils<PermissionsEntity>().getPage(params), new QueryWrapper<PermissionsEntity>());

        return new PageUtils(page);
    }

    /**
     * 根据角色名查询权限名
     *
     * @param roleIds 角色名
     * @return List<String> 权限名
     */
    @Override
    public List<String> findPermissionNameById(List<Integer> roleIds) {

        // 查询出对应关系
        List<RolePermissionsEntity> rolePermissions = rolePermissionsService.findByRoleId(roleIds);
        List<Integer> permissionIds = rolePermissions.stream().map(RolePermissionsEntity::getPermissionId).collect(Collectors.toList());

        // 查询出权限对象
        List<PermissionsEntity> permissionsEntities = this.baseMapper.selectList(//
                new LambdaQueryWrapper<PermissionsEntity>()//
                        .in(PermissionsEntity::getId, permissionIds));

        return permissionsEntities.stream().map(PermissionsEntity::getName).collect(Collectors.toList());
    }


}