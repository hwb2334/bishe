package com.shenmou.springboot.controller;

import com.shenmou.springboot.common.Result;
import com.shenmou.springboot.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping("/lists")
    public Result findPage()
    {
        return Result.success(bannerService.list());
    }
}
