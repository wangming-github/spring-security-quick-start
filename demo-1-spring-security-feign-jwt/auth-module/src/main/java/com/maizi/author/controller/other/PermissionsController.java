package com.maizi.author.controller.other;

import com.maizi.common.utils.R;
import com.maizi.author.entity.PermissionsEntity;
import com.maizi.author.service.PermissionsService;
import com.maizi.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
@RestController
@RequestMapping("permissions")
public class PermissionsController {

    @Autowired
    private PermissionsService permissionsService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:permissions:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = permissionsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:permissions:info")
    public R info(@PathVariable("id") Integer id) {
        PermissionsEntity permissions = permissionsService.getById(id);

        return R.ok().put("permissions", permissions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("generator:permissions:save")
    public R save(@RequestBody PermissionsEntity permissions) {
        permissionsService.save(permissions);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("generator:permissions:update")
    public R update(@RequestBody PermissionsEntity permissions) {
        permissionsService.updateById(permissions);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:permissions:delete")
    public R delete(@RequestBody Integer[] ids) {
        permissionsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
