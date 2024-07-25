package com.maizi.author.controller.other;

import com.maizi.common.utils.R;
import com.maizi.author.entity.UserRolesEntity;
import com.maizi.author.service.UserRolesService;
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
@RequestMapping("userRoles")
public class UserRolesController {
    @Autowired
    private UserRolesService userRolesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("XXXXX:userroles:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userRolesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    // @RequiresPermissions("XXXXX:userroles:info")
    public R info(@PathVariable("userId") Integer userId) {
        UserRolesEntity userRoles = userRolesService.getById(userId);

        return R.ok().put("userRoles", userRoles);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("XXXXX:userroles:save")
    public R save(@RequestBody UserRolesEntity userRoles) {
        userRolesService.save(userRoles);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("XXXXX:userroles:update")
    public R update(@RequestBody UserRolesEntity userRoles) {
        userRolesService.updateById(userRoles);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("XXXXX:userroles:delete")
    public R delete(@RequestBody Integer[] userIds) {
        userRolesService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
