package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenmou.springboot.entity.Role;
import com.shenmou.springboot.entity.RoleMenu;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IRoleMenuService extends IService<RoleMenu> {
    void setRoleMenu(Integer roleId, List<Integer> menuIds);
}
