package com.shenmou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.entity.Role;
import com.shenmou.springboot.entity.RoleMenu;
import com.shenmou.springboot.service.IRoleMenuService;
import com.shenmou.springboot.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;
    @Resource
    private IRoleMenuService roleMenuService;

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<Integer> menuIds = roleMenuService.list(queryWrapper)
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return Result.success(menuIds);
    }

    @PostMapping("/setMenu/{roleId}")
    public Result setRoleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        roleMenuService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<Role> lists = roleService.list();
        return Result.success(lists);
    }
}
