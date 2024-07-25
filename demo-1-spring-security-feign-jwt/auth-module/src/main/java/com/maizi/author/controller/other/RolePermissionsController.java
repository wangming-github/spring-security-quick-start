package com.maizi.author.controller.other;


import com.maizi.common.utils.R;
import com.maizi.author.entity.RolePermissionsEntity;
import com.maizi.author.service.RolePermissionsService;
import com.maizi.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author wangming
 * @email myoneray@gmail.com
 * @date 2024-07-12 17:44:48
 */
@RestController
@RequestMapping("rolePermissions")
public class RolePermissionsController {

    @Autowired
    private RolePermissionsService rolePermissionsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = rolePermissionsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    public R info(@PathVariable("roleId") Integer roleId) {
        RolePermissionsEntity rolePermissions = rolePermissionsService.getById(roleId);

        return R.ok().put("rolePermissions", rolePermissions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RolePermissionsEntity rolePermissions) {
        rolePermissionsService.save(rolePermissions);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RolePermissionsEntity rolePermissions) {
        rolePermissionsService.updateById(rolePermissions);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] roleIds) {
        rolePermissionsService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
