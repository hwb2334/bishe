package com.shenmou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenmou.springboot.entity.Menu;
import com.shenmou.springboot.mapper.MenuMapper;
import com.shenmou.springboot.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus() {
        // 查询所有数据
        List<Menu> lists = list();
        // 找出pid为null的一级菜单
        List<Menu> parentMenu = lists.stream().filter(menu -> menu.getPId() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for(Menu menu : parentMenu){
            menu.setChildren(lists.stream().filter(m -> menu.getMenuId().equals(m.getPId())).collect(Collectors.toList()));
        }
        return parentMenu;
    }
}
