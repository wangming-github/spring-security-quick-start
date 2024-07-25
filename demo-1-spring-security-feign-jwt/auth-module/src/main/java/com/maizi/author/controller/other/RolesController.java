package com.maizi.author.controller.other;

import com.maizi.common.utils.R;
import com.maizi.author.entity.RolesEntity;
import com.maizi.author.service.RolesService;
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
@RequestMapping("roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("generator:roles:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = rolesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("generator:roles:info")
    public R info(@PathVariable("id") Integer id) {
        RolesEntity roles = rolesService.getById(id);

        return R.ok().put("roles", roles);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("generator:roles:save")
    public R save(@RequestBody RolesEntity roles) {
        rolesService.save(roles);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("generator:roles:update")
    public R update(@RequestBody RolesEntity roles) {
        rolesService.updateById(roles);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:roles:delete")
    public R delete(@RequestBody Integer[] ids) {
        rolesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
