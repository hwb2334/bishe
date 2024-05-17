package com.shenmou.springboot.controller;
import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.service.IGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gdCate")
public class GoodsCategoryController {

    @Resource
    private IGoodsCategoryService goodsCategoryService;

    @GetMapping("/findAll")
    public Result findAll(){
        return Result.success(goodsCategoryService.list());
    }
}
