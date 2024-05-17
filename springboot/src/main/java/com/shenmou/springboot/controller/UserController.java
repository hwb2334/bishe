package com.shenmou.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.entity.User;
import com.shenmou.springboot.entity.dto.UserDTO;
import com.shenmou.springboot.service.IRoleService;
import com.shenmou.springboot.service.IUserRoleService;
import com.shenmou.springboot.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleService roleService;

    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody UserDTO userDTO){
        return Result.success(userService.updateInfo(userDTO));
    }

    @PostMapping("/save")
    public Result saveUser(@RequestBody User user){
        userService.saveUser(user);
        return Result.success();
    }

    // 注册用户
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String userName = userDTO.getUserName();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        User dto = userService.register(userDTO);
        return Result.success(dto);
    }

    // 登录
    @PostMapping("/login")
    public Result findUser(@RequestBody UserDTO userDTO) {
        String userName = userDTO.getUserName();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String userName,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!userName.equals(""))
            queryWrapper.like("user_name", userName);
        if (!email.equals(""))
            queryWrapper.like("email", email);
        if (!address.equals(""))
            queryWrapper.like("address", address);
        return userService.page(page, queryWrapper);
    }
}
