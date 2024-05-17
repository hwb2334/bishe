package com.shenmou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenmou.springboot.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus();
}
