package com.shenmou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenmou.springboot.common.Constants;
import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.entity.Dict;
import com.shenmou.springboot.entity.Menu;
import com.shenmou.springboot.service.IDictService;
import com.shenmou.springboot.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private IDictService dictService;

    @GetMapping("/ids")
    public Result findAllIds() {
        return Result.success(menuService.list().stream().map(Menu::getMenuId));
    }

    @GetMapping("/dict")
    public Result selectDict(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictService.list(queryWrapper));
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<Menu> parentMenu = menuService.findMenus();
        return Result.success(parentMenu);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }

    @DeleteMapping("/{menuId}")
    public Result delete(@PathVariable Integer menuId) {
        return Result.success(menuService.removeById(menuId));
    }
}
