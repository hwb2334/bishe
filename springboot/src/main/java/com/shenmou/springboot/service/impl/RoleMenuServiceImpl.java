package com.shenmou.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.Menu;
import com.shenmou.springboot.entity.RoleMenu;
import com.shenmou.springboot.mapper.RoleMenuMapper;
import com.shenmou.springboot.service.IMenuService;
import com.shenmou.springboot.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        // 先删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);

        // 再把前端传过来的菜单id数组绑定到当前这个角色id上去
        ArrayList<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if(menu.getPId() != null && !menuIdsCopy.contains(menu.getPId())){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPId());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPId());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }
}
