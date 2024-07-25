package com.maizi.author.controller.other;

import com.maizi.common.o.dto.UserDTO;
import com.maizi.common.o.dto.UserDetailsDTO;
import com.maizi.common.utils.R;
import com.maizi.author.entity.UsersEntity;
import com.maizi.common.utils.PageUtils;
import com.maizi.author.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
@Slf4j
@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private UsersService usersService;


    /**
     * 根据用户名查询用户信息及其角色和权限
     */
    @PostMapping("/findAuthorByUsername")
    public R findAuthorByUsername(@RequestBody UserDTO user) {
        UserDetailsDTO userDetailsDTO = usersService.findAuthorByUsername(user.getUsername());
        return R.ok().put("data", userDetailsDTO);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = usersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        UsersEntity users = usersService.getById(id);

        return R.ok().put("users", users);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("generator:users:save")
    public R save(@RequestBody UsersEntity users) {
        usersService.save(users);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("generator:users:update")
    public R update(@RequestBody UsersEntity users) {
        usersService.updateById(users);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("generator:users:delete")
    public R delete(@RequestBody Integer[] ids) {
        usersService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
