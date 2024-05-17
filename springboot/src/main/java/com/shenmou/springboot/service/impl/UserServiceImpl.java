package com.shenmou.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.entity.Menu;
import com.shenmou.springboot.entity.User;
import com.shenmou.springboot.entity.dto.UserDTO;
import com.shenmou.springboot.exception.ServiceException;
import com.shenmou.springboot.mapper.RoleMapper;
import com.shenmou.springboot.mapper.RoleMenuMapper;
import com.shenmou.springboot.mapper.UserMapper;
import com.shenmou.springboot.service.IMenuService;
import com.shenmou.springboot.service.IUserService;
import com.shenmou.springboot.utils.TokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userDTO.getUserName());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = userDTO.getPassword();
        if (one != null && encoder.matches(password, one.getPassword())) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.getToken(one.getUserName(), one.getPassword());
            userDTO.setToken(token);
            Integer roleId = one.getRoleId(); // ADMIN
            List<Menu> roleMenu = getRoleMenus(roleId);
            userDTO.setMenus(roleMenu);
            // 前端不展示密码
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userDTO.getUserName());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            Integer roleId = one.getRoleId();
            String flag = roleMapper.selectById(roleId);
            String password = one.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePwd = encoder.encode(password);
            one.setPassword(encodePwd);
            one.setRoleFlag(flag);
            // 设置默认头像
            one.setAvatar(Constants.AVATAR_DEFAULT);
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名已存在");
        }
        return one;
    }

    @Override
    public void saveUser(User user) {
        saveOrUpdate(user);
    }

    @Override
    public UserDTO updateInfo(UserDTO userDTO) {
        User user = new User();
        BeanUtil.copyProperties(userDTO, user, true);
        saveOrUpdate(user);
        user = getById(user.getUserId());
        BeanUtil.copyProperties(user, userDTO, true);
        return userDTO;
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleId
     * @return
     */
    private List<Menu> getRoleMenus(Integer roleId){
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        List<Menu> menus = menuService.findMenus();
        List<Menu> roleMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if(menuIds.contains(menu.getMenuId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf() 移除children 里面不在 menuIds 集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getMenuId()));
        }
        return roleMenus;
    }

}
